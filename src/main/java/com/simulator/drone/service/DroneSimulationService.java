package com.simulator.drone.service;

import com.simulator.drone.model.DronePayload;
import com.simulator.drone.model.DroneStatus;
import com.simulator.drone.model.ShipPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@DependsOn("shipSimulationService")
public class DroneSimulationService {

    private static final Logger log = LoggerFactory.getLogger(DroneSimulationService.class);

    // Open Atlantic south of Halifax — well clear of the NS coastline
    private static final double BASE_LAT = 43.80;
    private static final double BASE_LON = -63.00;
    private static final double METERS_PER_DEGREE_LAT = 111_320.0;
    private static final double SPAWN_RADIUS_METERS = 15_000.0;
    private static final double MAX_MOVE_METERS   = 30.0;
    private static final double MAX_LEASH_METERS  = 105_000.0;  // 105 km leash radius (15 km × 7)
    private static final double SPAWN_NEAR_SHIP_M =  21_000.0;  // spawn within 21 km of assigned ship (3 km × 7)
    private static final int    TICK_MS            = 3000;

    @Value("${simulator.drone-count:5}")
    private int droneCount;

    private volatile int dronesPerShip;

    private final SimpMessagingTemplate messagingTemplate;
    private final DroneMetricsCollector  metricsCollector;
    private final ShipSimulationService  shipSimulationService;
    private final Map<String, DronePayload> fleet = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public DroneSimulationService(SimpMessagingTemplate messagingTemplate,
                                  DroneMetricsCollector metricsCollector,
                                  ShipSimulationService shipSimulationService) {
        this.messagingTemplate    = messagingTemplate;
        this.metricsCollector     = metricsCollector;
        this.shipSimulationService = shipSimulationService;
    }

    @PostConstruct
    public void init() {
        List<String> shipIds = shipSimulationService.getShipIds();
        int shipCount = shipIds.size();
        dronesPerShip = (shipCount > 0) ? (int) Math.ceil((double) droneCount / shipCount) : droneCount;
        for (int i = 1; i <= droneCount; i++) {
            spawnDrone(i, shipIds);
        }
        log.info("Initialized {} drones assigned to ships: {}", droneCount, shipIds);
    }

    private DronePayload spawnDrone(int index, List<String> shipIds) {
        String id     = String.format("DRONE-%05d", index);
        String shipId = shipIds.isEmpty() ? null : shipIds.get((index - 1) % shipIds.size());

        // Spawn near the assigned ship if available; otherwise fall back to base coords
        double spawnLat = BASE_LAT;
        double spawnLon = BASE_LON;
        if (shipId != null) {
            shipSimulationService.getById(shipId).ifPresent(ship -> {
                // will be overwritten below — just a hint for the compiler
            });
            ShipPayload ship = shipSimulationService.getById(shipId).orElse(null);
            if (ship != null) {
                spawnLat = ship.getLatitude();
                spawnLon = ship.getLongitude();
            }
        }

        double metersPerDegreeLon = METERS_PER_DEGREE_LAT * Math.cos(Math.toRadians(spawnLat));
        double angle = random.nextDouble() * 2 * Math.PI;
        double dist  = random.nextDouble() * SPAWN_NEAR_SHIP_M;
        double lat   = spawnLat + (dist * Math.cos(angle)) / METERS_PER_DEGREE_LAT;
        double lon   = spawnLon + (dist * Math.sin(angle)) / metersPerDegreeLon;
        DronePayload drone = new DronePayload(
                id, shipId, lat, lon,
                10 + random.nextDouble() * 90,   // altitudeMeters
                0,                               // speedMps
                random.nextDouble() * 360,       // headingDegrees
                70 + random.nextInt(30),         // batteryPercent
                0.5 + random.nextDouble() * 4.5, // payloadKg 0.5–5.0
                DroneStatus.FLYING
        );
        fleet.put(id, drone);
        return drone;
    }

    /**
     * Resize the drone fleet. Total drones = targetShipCount * dronesPerShip.
     * Drones are added or removed at the high-index end. This method is
     * synchronized to prevent races with the {@code @Scheduled} tick.
     */
    public synchronized void resize(int targetShipCount, int newDronesPerShip) {
        int targetTotal = targetShipCount * newDronesPerShip;
        int current     = fleet.size();
        List<String> shipIds = shipSimulationService.getShipIds();

        if (targetTotal > current) {
            for (int i = current + 1; i <= targetTotal; i++) {
                spawnDrone(i, shipIds);
                log.info("Spawned drone DRONE-{}", String.format("%05d", i));
            }
        } else if (targetTotal < current) {
            for (int i = current; i > targetTotal; i--) {
                String id = String.format("DRONE-%05d", i);
                fleet.remove(id);
                log.info("Removed drone {}", id);
            }
        }
        dronesPerShip = newDronesPerShip;
        log.info("Drone fleet resized to {} drones ({} ships x {} per ship)",
                targetTotal, targetShipCount, newDronesPerShip);
    }

    public int getCurrentDroneCount() {
        return fleet.size();
    }

    public int getCurrentDronesPerShip() {
        return dronesPerShip;
    }

    @Scheduled(fixedRate = TICK_MS)
    public void tick() {
        fleet.values().forEach(this::update);
        messagingTemplate.convertAndSend("/topic/drones", new ArrayList<>(fleet.values()));
    }

    private void update(DronePayload drone) {
        // Determine movement — stay within 2 km of assigned ship
        double bearing;
        double distanceMeters;

        ShipPayload ship = drone.getShipId() != null
                ? shipSimulationService.getById(drone.getShipId()).orElse(null)
                : null;

        if (ship != null) {
            double distToShip = haversineMeters(
                    drone.getLatitude(), drone.getLongitude(),
                    ship.getLatitude(), ship.getLongitude());

            if (distToShip > MAX_LEASH_METERS) {
                // Outside 2 km — fly directly back toward the ship (±15° wobble)
                bearing = bearingTo(drone.getLatitude(), drone.getLongitude(),
                                    ship.getLatitude(), ship.getLongitude())
                          + (random.nextDouble() - 0.5) * Math.toRadians(30);
                distanceMeters = MAX_MOVE_METERS;
            } else {
                // Inside 2 km — patrol randomly with a gentle pull toward the ship
                // Pull strength increases linearly from 0 (at ship) to 40 % (at 2 km boundary)
                double pull = (distToShip / MAX_LEASH_METERS) * 0.4;
                double randomBearing = random.nextDouble() * 2 * Math.PI;
                double shipBearing   = bearingTo(drone.getLatitude(), drone.getLongitude(),
                                                  ship.getLatitude(), ship.getLongitude());
                // Blend bearings using vector components to avoid angle-wrap issues
                double rx = Math.cos(randomBearing), ry = Math.sin(randomBearing);
                double sx = Math.cos(shipBearing),   sy = Math.sin(shipBearing);
                bearing = Math.atan2(ry * (1 - pull) + sy * pull,
                                     rx * (1 - pull) + sx * pull);
                distanceMeters = random.nextDouble() * MAX_MOVE_METERS;
            }
        } else {
            // No ship assigned — pure random movement
            bearing       = random.nextDouble() * 2 * Math.PI;
            distanceMeters = random.nextDouble() * MAX_MOVE_METERS;
        }

        double metersPerDegreeLon = METERS_PER_DEGREE_LAT * Math.cos(Math.toRadians(drone.getLatitude()));
        drone.setLatitude(drone.getLatitude()  + (distanceMeters * Math.cos(bearing)) / METERS_PER_DEGREE_LAT);
        drone.setLongitude(drone.getLongitude() + (distanceMeters * Math.sin(bearing)) / metersPerDegreeLon);
        drone.setHeadingDegrees((Math.toDegrees(bearing) + 360) % 360);
        drone.setSpeedMps(distanceMeters / (TICK_MS / 1000.0));

        // Altitude drift
        drone.setAltitudeMeters(Math.max(5, Math.min(150, drone.getAltitudeMeters() + (random.nextDouble() - 0.5) * 2)));

        // Battery drain: ~1% per minute
        if (random.nextInt(60) == 0 && drone.getBatteryPercent() > 0) {
            drone.setBatteryPercent(drone.getBatteryPercent() - 1);
        }

        // Payload drift: small vibration ±0.05 kg per tick (sensors, equipment shifting)
        drone.setPayloadKg(Math.max(0, drone.getPayloadKg() + (random.nextDouble() - 0.5) * 0.1));

        // Status based on battery
        if (drone.getBatteryPercent() < 20 && drone.getStatus() == DroneStatus.FLYING) {
            drone.setStatus(DroneStatus.RETURNING);
        } else if (drone.getBatteryPercent() < 5) {
            drone.setStatus(DroneStatus.LANDING);
        }

        drone.setTimestamp(Instant.now());
        log.info("drone updated: {}", drone);
        metricsCollector.record(drone);
    }

    /** Flat-earth distance in metres (accurate to <0.1 % for distances under 50 km). */
    private double haversineMeters(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1) * METERS_PER_DEGREE_LAT;
        double dLon = (lon2 - lon1) * METERS_PER_DEGREE_LAT
                      * Math.cos(Math.toRadians((lat1 + lat2) / 2.0));
        return Math.sqrt(dLat * dLat + dLon * dLon);
    }

    /** Bearing (radians) from point 1 toward point 2. */
    private double bearingTo(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1) * METERS_PER_DEGREE_LAT;
        double dLon = (lon2 - lon1) * METERS_PER_DEGREE_LAT
                      * Math.cos(Math.toRadians((lat1 + lat2) / 2.0));
        return Math.atan2(dLon, dLat);
    }

    public Collection<DronePayload> getAll() {
        return Collections.unmodifiableCollection(fleet.values());
    }

    public Optional<DronePayload> getById(String id) {
        return Optional.ofNullable(fleet.get(id));
    }
}
