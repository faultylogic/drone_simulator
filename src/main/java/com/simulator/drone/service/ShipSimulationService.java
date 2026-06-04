package com.simulator.drone.service;

import com.simulator.drone.model.ShipAlertLevel;
import com.simulator.drone.model.ShipPayload;
import com.simulator.drone.model.ship.AirSearchDetail;
import com.simulator.drone.model.ship.BilgeDetail;
import com.simulator.drone.model.ship.BoforsGunDetail;
import com.simulator.drone.model.ship.CIWSDetail;
import com.simulator.drone.model.ship.DamageControlSystem;
import com.simulator.drone.model.ship.DieselEngineDetail;
import com.simulator.drone.model.ship.ESMDetail;
import com.simulator.drone.model.ship.ElectricalBusDetail;
import com.simulator.drone.model.ship.FireControlDetail;
import com.simulator.drone.model.ship.FireDetectionDetail;
import com.simulator.drone.model.ship.FireFightingDetail;
import com.simulator.drone.model.ship.FloodDetectionDetail;
import com.simulator.drone.model.ship.GasTurbineDetail;
import com.simulator.drone.model.ship.GeneratorDetail;
import com.simulator.drone.model.ship.HarpoonLauncherDetail;
import com.simulator.drone.model.ship.HullSonarDetail;
import com.simulator.drone.model.ship.HullZoneDetail;
import com.simulator.drone.model.ship.PowerSystem;
import com.simulator.drone.model.ship.PropulsionSystem;
import com.simulator.drone.model.ship.RadarSystem;
import com.simulator.drone.model.ship.SeawaterCoolingDetail;
import com.simulator.drone.model.ship.ShaftDetail;
import com.simulator.drone.model.ship.SonarSystem;
import com.simulator.drone.model.ship.StabilityDetail;
import com.simulator.drone.model.ship.SurfaceSearchDetail;
import com.simulator.drone.model.ship.TorpedoDetail;
import com.simulator.drone.model.ship.TowedArrayDetail;
import com.simulator.drone.model.ship.VLSDetail;
import com.simulator.drone.model.ship.WaterSystem;
import com.simulator.drone.model.ship.WeaponSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShipSimulationService {

    private static final Logger log = LoggerFactory.getLogger(ShipSimulationService.class);

    // Spawn centre — open Atlantic south of Halifax, well clear of the NS coastline
    private static final double BASE_LAT              = 43.80;
    private static final double BASE_LON              = -63.00;
    private static final double METERS_PER_DEGREE_LAT = 111_320.0;
    private static final double SPAWN_RADIUS_METERS   = 60_000.0;  // 60 km scatter radius
    private static final double MAX_MOVE_METERS       = 50.0;
    private static final int    TICK_MS               = 3000;
    private static final int    BASE_CREW             = 1_000;

    // Navigable water boundary — open Atlantic south of Nova Scotia, no land overlap
    // NS south coast sits at ~44.0-44.5°N in this longitude range
    private static final double NAV_NORTH       = 44.10;   // stays south of NS coast
    private static final double NAV_SOUTH       = 42.50;   // open Atlantic
    private static final double NAV_EAST        = -59.50;  // well into Sable Island Bank
    private static final double NAV_WEST        = -66.50;  // south of Yarmouth / Gulf of Maine
    private static final double SHORE_BUFFER_KM = 1.0;     // 1 km exclusion zone from each boundary

    @Value("${simulator.ship-count:2}")
    private volatile int shipCount;

    @Value("${simulator.drone-count:5}")
    private int droneCount;

    private final SimpMessagingTemplate messagingTemplate;
    private final ShipMetricsCollector  metricsCollector;
    private final Map<String, ShipPayload> fleet = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public ShipSimulationService(SimpMessagingTemplate messagingTemplate,
                                 ShipMetricsCollector metricsCollector) {
        this.messagingTemplate = messagingTemplate;
        this.metricsCollector  = metricsCollector;
    }

    @PostConstruct
    public void init() {
        int dronesPerShip = (shipCount > 0) ? (int) Math.ceil((double) droneCount / shipCount) : 0;
        for (int i = 1; i <= shipCount; i++) {
            spawnShip(i, dronesPerShip);
        }
        log.info("Initialized {} ships with ~{} drones each", shipCount, dronesPerShip);
    }

    private ShipPayload spawnShip(int index, int dronesPerShip) {
        double metersPerDegreeLon = METERS_PER_DEGREE_LAT * Math.cos(Math.toRadians(BASE_LAT));
        String id    = String.format("SHIP-%03d", index);
        double angle = random.nextDouble() * 2 * Math.PI;
        double dist  = Math.sqrt(random.nextDouble()) * SPAWN_RADIUS_METERS;
        double lat   = BASE_LAT + (dist * Math.cos(angle)) / METERS_PER_DEGREE_LAT;
        double lon   = BASE_LON + (dist * Math.sin(angle)) / metersPerDegreeLon;

        ShipPayload ship = new ShipPayload(
                id, lat, lon,
                random.nextDouble() * 360,        // heading
                10 + random.nextDouble() * 20,    // speedKnots 10-30
                BASE_CREW + random.nextInt(500),  // crewActive 1000-1500
                dronesPerShip,
                ShipAlertLevel.NORMAL
        );

        // PropulsionSystem: CODOG
        PropulsionSystem propulsion = new PropulsionSystem(
                60 + random.nextDouble() * 20,    // GT1 60-80%
                60 + random.nextDouble() * 20,    // GT2 60-80%
                40 + random.nextDouble() * 20,    // diesel 40-60%
                200 + random.nextDouble() * 80,   // port shaft 200-280 RPM
                200 + random.nextDouble() * 80,   // stbd shaft 200-280 RPM
                80 + random.nextDouble() * 15,    // gearbox temp 80-95°C
                2000 + random.nextDouble() * 2000, // fuel 2000-4000 L/hr
                false
        );
        ship.setPropulsion(propulsion);

        // PowerSystem: 4x Allison 501-K34
        double gen1 = 400 + random.nextDouble() * 200;
        double gen2 = 400 + random.nextDouble() * 200;
        double gen3 = 400 + random.nextDouble() * 200;
        double gen4 = 400 + random.nextDouble() * 200;
        PowerSystem power = new PowerSystem(
                gen1, gen2, gen3, gen4,
                1800 + random.nextDouble() * 600, // total load 1800-2400 kW
                440.0,
                60.0
        );
        ship.setPower(power);

        // WeaponSystem
        WeaponSystem weapons = new WeaponSystem(
                true,
                850 + random.nextInt(150),
                22.0,
                true,
                1200 + random.nextInt(350),
                8,
                8,
                6,
                "SAFE"
        );
        ship.setWeapons(weapons);

        // SonarSystem
        SonarSystem sonar = new SonarSystem(
                true,
                true,
                300 + random.nextDouble() * 200,  // cable 300-500 m
                random.nextInt(5),                // contacts 0-4
                "PASSIVE",
                20 + random.nextDouble() * 15,    // range 20-35 km
                45 + random.nextDouble() * 15     // noise 45-60 dB
        );
        ship.setSonar(sonar);

        // RadarSystem
        RadarSystem radar = new RadarSystem(
                true,
                true,
                true,
                random.nextInt(8),
                random.nextInt(5),
                false,
                ""
        );
        ship.setRadar(radar);

        // WaterSystem
        WaterSystem water = new WaterSystem(
                80 + random.nextDouble() * 15,    // fresh water 80-95%
                22 + random.nextDouble() * 8,     // cooling temp 22-30°C
                2 + random.nextDouble() * 3,      // bilge 2-5 cm
                9 + random.nextDouble() * 1.5,    // fire pressure 9-10.5 bar
                800 + random.nextDouble() * 400,  // desalinator 800-1200 L/hr
                20 + random.nextDouble() * 10     // sewage 20-30%
        );
        ship.setWaterSystems(water);

        // DamageControlSystem
        DamageControlSystem dc = new DamageControlSystem(
                97 + random.nextDouble() * 3,     // hull integrity 97-100%
                random.nextDouble() * 4 - 2,      // roll -2 to 2°
                random.nextDouble() * 3 - 1.5,    // pitch -1.5 to 1.5°
                random.nextDouble() * 2 - 1,      // heel -1 to 1°
                0,
                0,
                "NONE",
                0.1 + random.nextDouble() * 0.5   // water ingress 0.1-0.6 L/min
        );
        ship.setDamageControl(dc);

        // --- Propulsion component details ---
        GasTurbineDetail gt1Detail = new GasTurbineDetail(
                20 + random.nextDouble() * 10,      // inletTemp 20-30
                450 + random.nextDouble() * 70,     // exhaustTemp 450-520
                7000 + random.nextDouble() * 1500,  // compressorRpm 7000-8500
                4 + random.nextDouble() * 2,        // oilPressure 4-6
                70 + random.nextDouble() * 15,      // oilTemp 70-85
                0.8 + random.nextDouble() * 1.7     // vibration 0.8-2.5
        );
        propulsion.setGasTurbine1Detail(gt1Detail);

        GasTurbineDetail gt2Detail = new GasTurbineDetail(
                20 + random.nextDouble() * 10,
                450 + random.nextDouble() * 70,
                7000 + random.nextDouble() * 1500,
                4 + random.nextDouble() * 2,
                70 + random.nextDouble() * 15,
                0.8 + random.nextDouble() * 1.7
        );
        propulsion.setGasTurbine2Detail(gt2Detail);

        DieselEngineDetail dieselDetail = new DieselEngineDetail(
                78 + random.nextDouble() * 10,      // coolant 78-88
                3.5 + random.nextDouble() * 1.5,    // oilPressure 3.5-5
                70 + random.nextDouble() * 15,      // oilTemp 70-85
                300 + random.nextDouble() * 80,     // exhaust 300-380
                2.0 + random.nextDouble() * 1.0,    // turboBoost 2-3
                400 + random.nextDouble() * 120     // rpm 400-520
        );
        propulsion.setDieselDetail(dieselDetail);

        ShaftDetail portShaft = new ShaftDetail(
                200 + random.nextDouble() * 150,    // torque 200-350 kN·m
                50 + random.nextDouble() * 20,      // bearing 50-70
                0.3 + random.nextDouble() * 1.2,    // vibration 0.3-1.5
                0.1 + random.nextDouble() * 0.4,    // sealLeak 0.1-0.5
                70 + random.nextDouble() * 20       // pitch 70-90
        );
        propulsion.setPortShaftDetail(portShaft);

        ShaftDetail stbdShaft = new ShaftDetail(
                200 + random.nextDouble() * 150,
                50 + random.nextDouble() * 20,
                0.3 + random.nextDouble() * 1.2,
                0.1 + random.nextDouble() * 0.4,
                70 + random.nextDouble() * 20
        );
        propulsion.setStbdShaftDetail(stbdShaft);

        // --- Power component details ---
        double busV = power.getBusVoltageVolts();
        double lineToNeutral = busV / Math.sqrt(3.0);
        GeneratorDetail gen1d = new GeneratorDetail(
                438 + random.nextDouble() * 4,      // voltage 438-442
                (power.getGen1Kw() / 1000.0) * 100,
                75 + random.nextDouble() * 25,      // windingTemp 75-100
                45 + random.nextDouble() * 20,      // bearingTemp 45-65
                0.2 + random.nextDouble() * 1.0,    // vibration 0.2-1.2
                "ONLINE"
        );
        power.setGen1Detail(gen1d);

        GeneratorDetail gen2d = new GeneratorDetail(
                438 + random.nextDouble() * 4,
                (power.getGen2Kw() / 1000.0) * 100,
                75 + random.nextDouble() * 25,
                45 + random.nextDouble() * 20,
                0.2 + random.nextDouble() * 1.0,
                "ONLINE"
        );
        power.setGen2Detail(gen2d);

        GeneratorDetail gen3d = new GeneratorDetail(
                438 + random.nextDouble() * 4,
                (power.getGen3Kw() / 1000.0) * 100,
                75 + random.nextDouble() * 25,
                45 + random.nextDouble() * 20,
                0.2 + random.nextDouble() * 1.0,
                "ONLINE"
        );
        power.setGen3Detail(gen3d);

        GeneratorDetail gen4d = new GeneratorDetail(
                438 + random.nextDouble() * 4,
                (power.getGen4Kw() / 1000.0) * 100,
                75 + random.nextDouble() * 25,
                45 + random.nextDouble() * 20,
                0.2 + random.nextDouble() * 1.0,
                "ONLINE"
        );
        power.setGen4Detail(gen4d);

        ElectricalBusDetail busDetail = new ElectricalBusDetail(
                lineToNeutral + (random.nextDouble() - 0.5) * 0.4,
                lineToNeutral + (random.nextDouble() - 0.5) * 0.4,
                lineToNeutral + (random.nextDouble() - 0.5) * 0.4,
                90 + random.nextDouble() * 7,       // powerFactor 90-97
                0.5 + random.nextDouble() * 1.5,    // harmonicDistortion 0.5-2
                0
        );
        power.setBusDetail(busDetail);

        // --- Weapon component details ---
        BoforsGunDetail boforsDetail = new BoforsGunDetail(
                0.0, 0.0,
                2 + random.nextDouble() * 6,        // barrelWear 2-8%
                22 + random.nextDouble() * 6,       // coolingFluid 22-28
                "READY"
        );
        weapons.setBofors57mmDetail(boforsDetail);

        CIWSDetail ciswsDetail = new CIWSDetail(
                0.0, 15.0,
                2500 + random.nextDouble() * 500,   // radarRange 2500-3000
                "STANDBY",
                false
        );
        weapons.setCiswsDetail(ciswsDetail);

        HarpoonLauncherDetail harpoonDetail = new HarpoonLauncherDetail(
                20 + random.nextDouble() * 8,
                20 + random.nextDouble() * 8,
                20 + random.nextDouble() * 8,
                20 + random.nextDouble() * 8,
                false, 0
        );
        weapons.setHarpoonDetail(harpoonDetail);

        VLSDetail vlsDetail = new VLSDetail(
                8, 0,
                18 + random.nextDouble() * 4,       // coolantTemp 18-22
                false, "SAFE"
        );
        weapons.setVlsDetail(vlsDetail);

        TorpedoDetail torpedoDetail = new TorpedoDetail(
                0,
                98 + random.nextDouble() * 2,       // guidance 98-100
                true, 0
        );
        weapons.setTorpedoDetail(torpedoDetail);

        // --- Sonar component details ---
        HullSonarDetail hullSonarDetail = new HullSonarDetail(
                44 + random.nextInt(5),             // activeElements 44-48
                0.0,                                // transmitPower 0 (passive)
                35 + random.nextDouble() * 10,      // selfNoise 35-45
                8 + random.nextDouble() * 6,        // waterTemp 8-14
                32 + random.nextDouble() * 2,       // salinity 32-34
                "PASSIVE"
        );
        sonar.setHullSonarDetail(hullSonarDetail);

        TowedArrayDetail towedArrayDetail = new TowedArrayDetail(
                80 + random.nextDouble() * 40,      // depth 80-120
                2000 + random.nextDouble() * 1500,  // tension 2000-3500
                88 + random.nextInt(9),             // activeElements 88-96
                4 + random.nextDouble() * 4,        // temp 4-8
                450 + random.nextDouble() * 100,    // layback 450-550
                "DEPLOYED"
        );
        sonar.setTowedArrayDetail(towedArrayDetail);

        // --- Radar component details ---
        AirSearchDetail airSearchDetail = new AirSearchDetail(
                6.0,
                280 + random.nextDouble() * 20,     // transmitPower 280-300
                420 + random.nextDouble() * 30,     // range 420-450
                0
        );
        radar.setAirSearchDetail(airSearchDetail);

        SurfaceSearchDetail surfaceSearchDetail = new SurfaceSearchDetail(
                20 + random.nextDouble() * 4,       // antennaRpm 20-24
                40 + random.nextDouble() * 10,      // transmitPower 40-50
                80 + random.nextDouble() * 20,      // range 80-100
                true
        );
        radar.setSurfaceSearchDetail(surfaceSearchDetail);

        FireControlDetail fireControlDetail = new FireControlDetail(
                0.0, 15.0,
                25 + random.nextDouble() * 10,      // snr 25-35
                false, 0
        );
        radar.setFireControlDetail(fireControlDetail);

        ESMDetail esmDetail = new ESMDetail(
                "I/J",
                -85 + random.nextDouble() * 25,     // signalStrength -85 to -60
                random.nextInt(4),                  // emittersDetected 0-3
                false
        );
        radar.setEsmDetail(esmDetail);

        // --- Water component details ---
        SeawaterCoolingDetail coolingDetail = new SeawaterCoolingDetail(
                true, true,
                3000 + random.nextDouble() * 1000,  // flowRate 3000-4000
                12 + random.nextDouble() * 6,       // inletTemp 12-18
                28 + random.nextDouble() * 7,       // outletTemp 28-35
                0.2 + random.nextDouble() * 0.4     // filterDp 0.2-0.6
        );
        water.setCoolingDetail(coolingDetail);

        FireFightingDetail firefightingDetail = new FireFightingDetail(
                true, false,
                85 + random.nextDouble() * 10,      // foamAgent 85-95
                9 + random.nextDouble() * 1.5,      // pressure 9-10.5
                0, false
        );
        water.setFirefightingDetail(firefightingDetail);

        BilgeDetail bilgeDetail = new BilgeDetail(
                false, false,
                0.5 + random.nextDouble() * 1.5,    // forward 0.5-2
                0.5 + random.nextDouble() * 1.5,    // midships 0.5-2
                0.5 + random.nextDouble() * 1.5,    // aft 0.5-2
                1.0 + random.nextDouble() * 2.0     // engineRoom 1-3
        );
        water.setBilgeDetail(bilgeDetail);

        // --- Damage control component details ---
        HullZoneDetail hullZoneDetail = new HullZoneDetail(
                97 + random.nextDouble() * 3,
                97 + random.nextDouble() * 3,
                97 + random.nextDouble() * 3,
                97 + random.nextDouble() * 3,
                97 + random.nextDouble() * 3,
                true
        );
        dc.setHullZoneDetail(hullZoneDetail);

        FireDetectionDetail fireDetectionDetail = new FireDetectionDetail(
                45 + random.nextInt(6),             // smokeDetectors 45-50
                45 + random.nextInt(6),             // heatDetectors 45-50
                0, false,
                180 + random.nextDouble() * 20      // co2Pressure 180-200
        );
        dc.setFireDetectionDetail(fireDetectionDetail);

        FloodDetectionDetail floodDetectionDetail = new FloodDetectionDetail(
                18 + random.nextInt(3),             // highWaterSensors 18-20
                0,
                0.1 + random.nextDouble() * 0.4,    // totalIngress 0.1-0.5
                false
        );
        dc.setFloodDetectionDetail(floodDetectionDetail);

        StabilityDetail stabilityDetail = new StabilityDetail(
                1.2 + random.nextDouble() * 0.6,    // gm 1.2-1.8
                14 + random.nextDouble() * 4,       // rollPeriod 14-18
                4750 + random.nextDouble() * 50,    // displacement 4750-4800
                (random.nextDouble() - 0.5) * 0.2, // trim -0.1 to 0.1
                4.0 + random.nextDouble() * 0.4,    // draftFwd 4.0-4.4
                4.1 + random.nextDouble() * 0.4     // draftAft 4.1-4.5
        );
        dc.setStabilityDetail(stabilityDetail);

        fleet.put(id, ship);
        return ship;
    }

    /**
     * Resize the fleet to {@code targetCount} ships. Ships are added or removed
     * at the high-index end. This method is synchronized to prevent races with the
     * {@code @Scheduled} tick.
     */
    public synchronized void resize(int targetCount) {
        int current = shipCount;
        if (targetCount > current) {
            // Compute dronesPerShip from total drones already assigned
            int dronesPerShip = (targetCount > 0) ? (int) Math.ceil((double) droneCount / targetCount) : 0;
            for (int i = current + 1; i <= targetCount; i++) {
                spawnShip(i, dronesPerShip);
                log.info("Spawned ship SHIP-{}", String.format("%03d", i));
            }
        } else if (targetCount < current) {
            for (int i = current; i > targetCount; i--) {
                String id = String.format("SHIP-%03d", i);
                fleet.remove(id);
                log.info("Removed ship {}", id);
            }
        }
        shipCount = targetCount;
        log.info("Fleet resized to {} ships", shipCount);
    }

    public int getCurrentShipCount() {
        return shipCount;
    }

    @Scheduled(fixedRate = TICK_MS)
    public void tick() {
        fleet.values().forEach(this::update);
        messagingTemplate.convertAndSend("/topic/ships", new ArrayList<>(fleet.values()));
    }

    private void update(ShipPayload ship) {
        // Ships turn slowly — nudge heading ±5° occasionally
        if (random.nextInt(5) == 0) {
            ship.setHeadingDegrees((ship.getHeadingDegrees() + (random.nextDouble() - 0.5) * 10 + 360) % 360);
        }

        // Move along current heading
        double headingRad     = Math.toRadians(ship.getHeadingDegrees());
        double distanceMeters = random.nextDouble() * MAX_MOVE_METERS;
        double metersPerDegreeLon = METERS_PER_DEGREE_LAT * Math.cos(Math.toRadians(ship.getLatitude()));
        ship.setLatitude (ship.getLatitude()  + (distanceMeters * Math.cos(headingRad)) / METERS_PER_DEGREE_LAT);
        ship.setLongitude(ship.getLongitude() + (distanceMeters * Math.sin(headingRad)) / metersPerDegreeLon);

        // Enforce 1 km shoreline exclusion zone
        applyShorelineGuardrail(ship);

        // Speed in knots (1 knot = 0.514 m/s)
        ship.setSpeedKnots(distanceMeters / (TICK_MS / 1000.0) / 0.514);

        // Update sub-systems
        updateSystems(ship);

        // Alert level based on hull integrity (from DamageControlSystem) and average GT output
        double hullIntegrity = ship.getDamageControl() != null ? ship.getDamageControl().getHullIntegrityPercent() : 100.0;
        double avgGt = ship.getPropulsion() != null
                ? (ship.getPropulsion().getGasTurbine1Percent() + ship.getPropulsion().getGasTurbine2Percent()) / 2.0
                : 70.0;

        if      (hullIntegrity < 30 || avgGt < 20) ship.setAlertLevel(ShipAlertLevel.CONDITION_1);
        else if (hullIntegrity < 60 || avgGt < 40) ship.setAlertLevel(ShipAlertLevel.CONDITION_2);
        else if (hullIntegrity < 80 || avgGt < 60) ship.setAlertLevel(ShipAlertLevel.CONDITION_3);
        else                                        ship.setAlertLevel(ShipAlertLevel.NORMAL);

        ship.setTimestamp(Instant.now());
        log.info("ship updated: {}", ship);
        metricsCollector.record(ship);
    }

    private void updateSystems(ShipPayload ship) {
        double speedKnots = ship.getSpeedKnots();

        // --- PropulsionSystem ---
        PropulsionSystem prop = ship.getPropulsion();
        if (prop != null) {
            double targetRpm = speedKnots * 12.0;
            double portRpm   = clamp(prop.getPortShaftRpm() + (targetRpm - prop.getPortShaftRpm()) * 0.1 + drift(2), 0, 350);
            double stbdRpm   = clamp(prop.getStbdShaftRpm() + (targetRpm - prop.getStbdShaftRpm()) * 0.1 + drift(2), 0, 350);
            prop.setPortShaftRpm(portRpm);
            prop.setStbdShaftRpm(stbdRpm);

            // GT ramps up when speed > 15kn
            double gt1Target = speedKnots > 15 ? 75 + random.nextDouble() * 10 : 40 + random.nextDouble() * 10;
            double gt2Target = speedKnots > 15 ? 75 + random.nextDouble() * 10 : 40 + random.nextDouble() * 10;
            prop.setGasTurbine1Percent(clamp(prop.getGasTurbine1Percent() + (gt1Target - prop.getGasTurbine1Percent()) * 0.05 + drift(1), 0, 100));
            prop.setGasTurbine2Percent(clamp(prop.getGasTurbine2Percent() + (gt2Target - prop.getGasTurbine2Percent()) * 0.05 + drift(1), 0, 100));

            // Diesel active when speed <= 18kn
            double dieselTarget = speedKnots <= 18 ? 50 + random.nextDouble() * 15 : 10 + random.nextDouble() * 10;
            prop.setDieselOutputPercent(clamp(prop.getDieselOutputPercent() + (dieselTarget - prop.getDieselOutputPercent()) * 0.05 + drift(1), 0, 100));

            // Gearbox temp correlates with shaft RPM
            double avgRpm     = (portRpm + stbdRpm) / 2.0;
            double tempTarget = 60 + avgRpm * (50.0 / 350.0);
            prop.setGearboxTempCelsius(clamp(prop.getGearboxTempCelsius() + (tempTarget - prop.getGearboxTempCelsius()) * 0.05 + drift(0.5), 60, 110));

            // Fuel consumption
            double fuelCalc = 500
                    + prop.getDieselOutputPercent() * 20
                    + (prop.getGasTurbine1Percent() + prop.getGasTurbine2Percent()) * 35
                    + drift(50);
            prop.setFuelConsumptionLph(Math.max(200, fuelCalc));

            // GT1 detail
            GasTurbineDetail gt1d = prop.getGasTurbine1Detail();
            if (gt1d != null) {
                double gt1Out = prop.getGasTurbine1Percent();
                gt1d.setExhaustTempCelsius(clamp(400 + gt1Out * 1.5 + drift(2), 400, 560));
                gt1d.setCompressorSpeedRpm(clamp(3000 + gt1Out * 65 + drift(50), 3000, 9500));
                gt1d.setOilPressureBar(clamp(gt1d.getOilPressureBar() + drift(0.05), 3, 7));
                gt1d.setOilTempCelsius(clamp(gt1d.getOilTempCelsius() + drift(0.3), 60, 95));
                gt1d.setVibrationMms(clamp(gt1d.getVibrationMms() + drift(0.05), 0.5, 5));
                gt1d.setInletTempCelsius(clamp(gt1d.getInletTempCelsius() + drift(0.1), 15, 40));
            }

            // GT2 detail
            GasTurbineDetail gt2d = prop.getGasTurbine2Detail();
            if (gt2d != null) {
                double gt2Out = prop.getGasTurbine2Percent();
                gt2d.setExhaustTempCelsius(clamp(400 + gt2Out * 1.5 + drift(2), 400, 560));
                gt2d.setCompressorSpeedRpm(clamp(3000 + gt2Out * 65 + drift(50), 3000, 9500));
                gt2d.setOilPressureBar(clamp(gt2d.getOilPressureBar() + drift(0.05), 3, 7));
                gt2d.setOilTempCelsius(clamp(gt2d.getOilTempCelsius() + drift(0.3), 60, 95));
                gt2d.setVibrationMms(clamp(gt2d.getVibrationMms() + drift(0.05), 0.5, 5));
                gt2d.setInletTempCelsius(clamp(gt2d.getInletTempCelsius() + drift(0.1), 15, 40));
            }

            // Diesel detail
            DieselEngineDetail dsd = prop.getDieselDetail();
            if (dsd != null) {
                double dOut = prop.getDieselOutputPercent();
                dsd.setCoolantTempCelsius(clamp(70 + dOut * 0.25 + drift(0.5), 70, 95));
                dsd.setOilPressureBar(clamp(dsd.getOilPressureBar() + drift(0.03), 3, 6));
                dsd.setOilTempCelsius(clamp(dsd.getOilTempCelsius() + drift(0.3), 65, 90));
                dsd.setExhaustTempCelsius(clamp(250 + dOut * 2.0 + drift(3), 250, 450));
                dsd.setTurbochargerBoostBar(clamp(1.5 + dOut * 0.02 + drift(0.05), 1.5, 3.5));
                dsd.setRpmActual(clamp(dOut * 6.0 + drift(5), 0, 600));
            }

            // Port shaft detail
            ShaftDetail psd = prop.getPortShaftDetail();
            if (psd != null) {
                psd.setTorqueKnm(clamp(prop.getPortShaftRpm() * 1.2 + drift(5), 0, 500));
                psd.setBearingTempCelsius(clamp(psd.getBearingTempCelsius() + drift(0.2), 40, 80));
                psd.setVibrationMms(clamp(psd.getVibrationMms() + drift(0.02), 0.1, 3));
                psd.setSealLeakRateLpm(clamp(psd.getSealLeakRateLpm() + drift(0.01), 0, 2));
            }

            // Stbd shaft detail
            ShaftDetail ssd = prop.getStbdShaftDetail();
            if (ssd != null) {
                ssd.setTorqueKnm(clamp(prop.getStbdShaftRpm() * 1.2 + drift(5), 0, 500));
                ssd.setBearingTempCelsius(clamp(ssd.getBearingTempCelsius() + drift(0.2), 40, 80));
                ssd.setVibrationMms(clamp(ssd.getVibrationMms() + drift(0.02), 0.1, 3));
                ssd.setSealLeakRateLpm(clamp(ssd.getSealLeakRateLpm() + drift(0.01), 0, 2));
            }
        }

        // --- PowerSystem ---
        PowerSystem pwr = ship.getPower();
        if (pwr != null) {
            double loadTarget = clamp(1000 + speedKnots * 40, 500, 3800);
            double totalLoad  = clamp(pwr.getTotalLoadKw() + (loadTarget - pwr.getTotalLoadKw()) * 0.05 + drift(10), 500, 3800);
            pwr.setTotalLoadKw(totalLoad);

            double perGen = totalLoad / 4.0;
            pwr.setGen1Kw(clamp(perGen + drift(20), 0, 1000));
            pwr.setGen2Kw(clamp(perGen + drift(20), 0, 1000));
            pwr.setGen3Kw(clamp(perGen + drift(20), 0, 1000));
            pwr.setGen4Kw(clamp(perGen + drift(20), 0, 1000));

            pwr.setBusVoltageVolts(clamp(pwr.getBusVoltageVolts() + drift(0.5), 437, 443));
            pwr.setFrequencyHz(clamp(pwr.getFrequencyHz() + drift(0.05), 59.7, 60.3));

            // Generator details
            GeneratorDetail g1d = pwr.getGen1Detail();
            if (g1d != null) {
                g1d.setOutputVoltageVolts(clamp(g1d.getOutputVoltageVolts() + drift(0.3), 435, 445));
                g1d.setLoadPercent(clamp((pwr.getGen1Kw() / 1000.0) * 100, 0, 100));
                g1d.setWindingTempCelsius(clamp(g1d.getWindingTempCelsius() + drift(0.3), 60, 120));
                g1d.setBearingTempCelsius(clamp(g1d.getBearingTempCelsius() + drift(0.2), 40, 80));
                g1d.setVibrationMms(clamp(g1d.getVibrationMms() + drift(0.02), 0.1, 3));
            }
            GeneratorDetail g2d = pwr.getGen2Detail();
            if (g2d != null) {
                g2d.setOutputVoltageVolts(clamp(g2d.getOutputVoltageVolts() + drift(0.3), 435, 445));
                g2d.setLoadPercent(clamp((pwr.getGen2Kw() / 1000.0) * 100, 0, 100));
                g2d.setWindingTempCelsius(clamp(g2d.getWindingTempCelsius() + drift(0.3), 60, 120));
                g2d.setBearingTempCelsius(clamp(g2d.getBearingTempCelsius() + drift(0.2), 40, 80));
                g2d.setVibrationMms(clamp(g2d.getVibrationMms() + drift(0.02), 0.1, 3));
            }
            GeneratorDetail g3d = pwr.getGen3Detail();
            if (g3d != null) {
                g3d.setOutputVoltageVolts(clamp(g3d.getOutputVoltageVolts() + drift(0.3), 435, 445));
                g3d.setLoadPercent(clamp((pwr.getGen3Kw() / 1000.0) * 100, 0, 100));
                g3d.setWindingTempCelsius(clamp(g3d.getWindingTempCelsius() + drift(0.3), 60, 120));
                g3d.setBearingTempCelsius(clamp(g3d.getBearingTempCelsius() + drift(0.2), 40, 80));
                g3d.setVibrationMms(clamp(g3d.getVibrationMms() + drift(0.02), 0.1, 3));
            }
            GeneratorDetail g4d = pwr.getGen4Detail();
            if (g4d != null) {
                g4d.setOutputVoltageVolts(clamp(g4d.getOutputVoltageVolts() + drift(0.3), 435, 445));
                g4d.setLoadPercent(clamp((pwr.getGen4Kw() / 1000.0) * 100, 0, 100));
                g4d.setWindingTempCelsius(clamp(g4d.getWindingTempCelsius() + drift(0.3), 60, 120));
                g4d.setBearingTempCelsius(clamp(g4d.getBearingTempCelsius() + drift(0.2), 40, 80));
                g4d.setVibrationMms(clamp(g4d.getVibrationMms() + drift(0.02), 0.1, 3));
            }

            // Bus detail
            ElectricalBusDetail bd = pwr.getBusDetail();
            if (bd != null) {
                double ltn = pwr.getBusVoltageVolts() / Math.sqrt(3.0);
                bd.setPhase1VoltageVolts(clamp(ltn + drift(0.2), 248, 260));
                bd.setPhase2VoltageVolts(clamp(ltn + drift(0.2), 248, 260));
                bd.setPhase3VoltageVolts(clamp(ltn + drift(0.2), 248, 260));
                bd.setPowerFactorPercent(clamp(bd.getPowerFactorPercent() + drift(0.1), 85, 100));
                bd.setHarmonicDistortionPercent(clamp(bd.getHarmonicDistortionPercent() + drift(0.05), 0, 5));
            }
        }

        // --- WeaponSystem ---
        WeaponSystem wpn = ship.getWeapons();
        if (wpn != null) {
            // Barrel cools 0.2°C/tick
            wpn.setBofors57mmBarrelTempCelsius(Math.max(20.0, wpn.getBofors57mmBarrelTempCelsius() - 0.2));
            // 1% chance drill consumes 5 rounds
            if (random.nextInt(100) == 0 && wpn.getBofors57mmAmmoRounds() >= 5) {
                wpn.setBofors57mmAmmoRounds(wpn.getBofors57mmAmmoRounds() - 5);
            }

            // Bofors detail
            BoforsGunDetail bgd = wpn.getBofors57mmDetail();
            if (bgd != null) {
                bgd.setElevationDegrees(clamp(bgd.getElevationDegrees() + drift(0.5), -10, 85));
                bgd.setAzimuthDegrees((bgd.getAzimuthDegrees() + drift(0.5) + 360) % 360);
                if (random.nextInt(200) == 0) bgd.setBarrelWearPercent(clamp(bgd.getBarrelWearPercent() + 0.01, 0, 100));
                bgd.setCoolingFluidTempCelsius(clamp(bgd.getCoolingFluidTempCelsius() + drift(0.1), 20, 80));
            }

            // CIWS detail
            CIWSDetail ciwsd = wpn.getCiswsDetail();
            if (ciwsd != null) {
                ciwsd.setMountAzimuthDegrees((ciwsd.getMountAzimuthDegrees() + drift(0.3) + 360) % 360);
                ciwsd.setMountElevationDegrees(clamp(ciwsd.getMountElevationDegrees() + drift(0.3), -20, 80));
                ciwsd.setRadarRangeMeters(clamp(ciwsd.getRadarRangeMeters() + drift(5), 0, 3000));
            }

            // Harpoon detail
            HarpoonLauncherDetail hpd = wpn.getHarpoonDetail();
            if (hpd != null) {
                hpd.setCanister1TempCelsius(clamp(hpd.getCanister1TempCelsius() + drift(0.05), 20, 35));
                hpd.setCanister2TempCelsius(clamp(hpd.getCanister2TempCelsius() + drift(0.05), 20, 35));
                hpd.setCanister3TempCelsius(clamp(hpd.getCanister3TempCelsius() + drift(0.05), 20, 35));
                hpd.setCanister4TempCelsius(clamp(hpd.getCanister4TempCelsius() + drift(0.05), 20, 35));
            }

            // VLS detail
            VLSDetail vlsd = wpn.getVlsDetail();
            if (vlsd != null) {
                vlsd.setCoolantTempCelsius(clamp(vlsd.getCoolantTempCelsius() + drift(0.1), 15, 35));
            }

            // Torpedo detail
            TorpedoDetail tpd = wpn.getTorpedoDetail();
            if (tpd != null) {
                tpd.setGuidanceSystemPercent(clamp(tpd.getGuidanceSystemPercent() + drift(0.05), 95, 100));
            }
        }

        // --- SonarSystem ---
        SonarSystem sonar = ship.getSonar();
        if (sonar != null) {
            int contactDelta = random.nextInt(3) - 1; // -1, 0, or +1
            sonar.setActiveContactsTracked(clampInt(sonar.getActiveContactsTracked() + contactDelta, 0, 20));

            if (sonar.isCantassTowedArrayDeployed()) {
                double cable = clamp(sonar.getTowedArrayCableOutMeters() + drift(2), 100, 600);
                sonar.setTowedArrayCableOutMeters(cable);
            }

            sonar.setDetectionRangeKm(clamp(sonar.getDetectionRangeKm() + drift(0.3), 5, 50));
            sonar.setAmbientNoiseDb(clamp(sonar.getAmbientNoiseDb() + drift(0.5), 30, 80));

            // Hull sonar detail
            HullSonarDetail hsd = sonar.getHullSonarDetail();
            if (hsd != null) {
                if (random.nextInt(100) != 0) {
                    // elements stay at max 99% of the time — no change
                } else {
                    hsd.setActiveElements(clampInt(hsd.getActiveElements() + (random.nextInt(3) - 1), 0, 48));
                }
                hsd.setSelfNoiseDb(clamp(hsd.getSelfNoiseDb() + drift(0.3), 20, 60));
                hsd.setWaterTempCelsius(clamp(hsd.getWaterTempCelsius() + drift(0.05), 0, 20));
                hsd.setSalinityPsu(clamp(hsd.getSalinityPsu() + drift(0.02), 30, 36));
                if ("PASSIVE".equals(hsd.getPingMode()) || "SILENT".equals(hsd.getPingMode())) {
                    hsd.setTransmitPowerKw(0);
                } else {
                    hsd.setTransmitPowerKw(clamp(250 + random.nextDouble() * 50, 0, 300));
                }
            }

            // Towed array detail
            TowedArrayDetail tad = sonar.getTowedArrayDetail();
            if (tad != null) {
                tad.setDepthMeters(clamp(tad.getDepthMeters() + drift(0.5), 0, 200));
                tad.setTensionNewtons(clamp(tad.getTensionNewtons() + drift(20), 500, 5000));
                tad.setLaybackMeters(clamp(tad.getLaybackMeters() + drift(1), 0, 800));
                tad.setArrayTempCelsius(clamp(tad.getArrayTempCelsius() + drift(0.1), 2, 15));
            }
        }

        // --- RadarSystem ---
        RadarSystem radar = ship.getRadar();
        if (radar != null) {
            int airDelta     = random.nextInt(3) - 1;
            int surfDelta    = random.nextInt(3) - 1;
            radar.setAirTracksDetected(clampInt(radar.getAirTracksDetected() + airDelta, 0, 30));
            radar.setSurfaceTracksDetected(clampInt(radar.getSurfaceTracksDetected() + surfDelta, 0, 15));

            // 1% chance ESM alert toggles
            if (random.nextInt(100) == 0) {
                radar.setEsmAlertActive(!radar.isEsmAlertActive());
                radar.setEsmBandDetected(radar.isEsmAlertActive() ? "I/J" : "");
            }

            // Air search detail
            AirSearchDetail asd = radar.getAirSearchDetail();
            if (asd != null) {
                asd.setAntennaRotationRpm(clamp(6.0 + drift(0.02), 0, 6));
                asd.setTransmitPowerKw(clamp(asd.getTransmitPowerKw() + drift(1), 0, 300));
                asd.setRangeKm(clamp(asd.getRangeKm() + drift(0.5), 0, 450));
            }

            // Surface search detail
            SurfaceSearchDetail ssrd = radar.getSurfaceSearchDetail();
            if (ssrd != null) {
                ssrd.setAntennaRotationRpm(clamp(22.0 + drift(0.1), 0, 24));
                ssrd.setTransmitPowerKw(clamp(ssrd.getTransmitPowerKw() + drift(0.3), 0, 50));
                ssrd.setRangeKm(clamp(ssrd.getRangeKm() + drift(0.2), 0, 100));
            }

            // Fire control detail
            FireControlDetail fcd = radar.getFireControlDetail();
            if (fcd != null) {
                fcd.setTrackingAzimuthDegrees((fcd.getTrackingAzimuthDegrees() + drift(0.2) + 360) % 360);
                fcd.setTrackingElevationDegrees(clamp(fcd.getTrackingElevationDegrees() + drift(0.2), -5, 90));
                fcd.setSignalToNoiseDb(clamp(fcd.getSignalToNoiseDb() + drift(0.3), 0, 40));
            }

            // ESM detail
            ESMDetail esmd = radar.getEsmDetail();
            if (esmd != null) {
                esmd.setSignalStrengthDbm(clamp(esmd.getSignalStrengthDbm() + drift(0.5), -100, -20));
                if (random.nextInt(20) == 0) {
                    esmd.setEmittersDetected(clampInt(esmd.getEmittersDetected() + (random.nextInt(3) - 1), 0, 10));
                }
            }
        }

        // --- WaterSystem ---
        WaterSystem water = ship.getWaterSystems();
        if (water != null) {
            water.setPotableFreshWaterPercent(clamp(water.getPotableFreshWaterPercent() - 0.0003, 0, 100));

            // Cooling temp correlates with GT output
            double gtAvg = ship.getPropulsion() != null
                    ? (ship.getPropulsion().getGasTurbine1Percent() + ship.getPropulsion().getGasTurbine2Percent()) / 2.0
                    : 60.0;
            double coolingDrift = (gtAvg - 60.0) * 0.01 + drift(0.1);
            water.setSeawaterCoolingTempCelsius(clamp(water.getSeawaterCoolingTempCelsius() + coolingDrift, 15, 45));

            water.setBilgeWaterLevelCm(clamp(water.getBilgeWaterLevelCm() + drift(0.02), 0, 25));
            water.setFireMainPressureBar(clamp(water.getFireMainPressureBar() + drift(0.03), 7.5, 11));
            water.setDesalinatorOutputLph(clamp(water.getDesalinatorOutputLph() + drift(5), 500, 2000));
            water.setSewageTankPercent(clamp(water.getSewageTankPercent() + 0.0002, 0, 100));

            // Seawater cooling detail
            SeawaterCoolingDetail scd = water.getCoolingDetail();
            if (scd != null) {
                double gtAvgW = ship.getPropulsion() != null
                        ? (ship.getPropulsion().getGasTurbine1Percent() + ship.getPropulsion().getGasTurbine2Percent()) / 2.0
                        : 60.0;
                scd.setFlowRateLpm(clamp(scd.getFlowRateLpm() + drift(10), 0, 5000));
                scd.setInletTempCelsius(clamp(scd.getInletTempCelsius() + drift(0.05), 10, 25));
                scd.setOutletTempCelsius(clamp(scd.getOutletTempCelsius() + (gtAvgW - 60.0) * 0.005 + drift(0.1), 25, 45));
                scd.setFilterDpBar(clamp(scd.getFilterDpBar() + drift(0.01), 0, 2));
            }

            // Firefighting detail
            FireFightingDetail ffd = water.getFirefightingDetail();
            if (ffd != null) {
                ffd.setFoamAgentPercent(clamp(ffd.getFoamAgentPercent() - 0.0001, 0, 100));
                ffd.setSystemPressureBar(clamp(ffd.getSystemPressureBar() + drift(0.02), 7.5, 11));
            }

            // Bilge detail
            BilgeDetail bld = water.getBilgeDetail();
            if (bld != null) {
                bld.setForwardBilgeCm(clamp(bld.getForwardBilgeCm() + drift(0.01), 0, 30));
                bld.setMidshipsBilgeCm(clamp(bld.getMidshipsBilgeCm() + drift(0.01), 0, 30));
                bld.setAftBilgeCm(clamp(bld.getAftBilgeCm() + drift(0.01), 0, 30));
                bld.setEngineRoomBilgeCm(clamp(bld.getEngineRoomBilgeCm() + drift(0.015), 0, 30));
            }
        }

        // --- DamageControlSystem ---
        DamageControlSystem dc = ship.getDamageControl();
        if (dc != null) {
            dc.setHullIntegrityPercent(clamp(dc.getHullIntegrityPercent() + drift(0.02), 0, 100));
            dc.setRollDegrees(clamp(dc.getRollDegrees() + drift(0.3), -25, 25));
            dc.setPitchDegrees(clamp(dc.getPitchDegrees() + drift(0.3), -10, 10));
            dc.setHeelDegrees(clamp(dc.getHeelDegrees() + drift(0.3), -5, 5));
            dc.setWaterIngressLpm(clamp(dc.getWaterIngressLpm() + drift(0.02), 0, 10));

            // Hull zone detail
            HullZoneDetail hzd = dc.getHullZoneDetail();
            if (hzd != null) {
                hzd.setZone1IntegrityPercent(clamp(hzd.getZone1IntegrityPercent() + drift(0.01), 0, 100));
                hzd.setZone2IntegrityPercent(clamp(hzd.getZone2IntegrityPercent() + drift(0.01), 0, 100));
                hzd.setZone3IntegrityPercent(clamp(hzd.getZone3IntegrityPercent() + drift(0.01), 0, 100));
                hzd.setZone4IntegrityPercent(clamp(hzd.getZone4IntegrityPercent() + drift(0.015), 0, 100));
                hzd.setZone5IntegrityPercent(clamp(hzd.getZone5IntegrityPercent() + drift(0.01), 0, 100));
            }

            // Fire detection detail
            FireDetectionDetail fdd = dc.getFireDetectionDetail();
            if (fdd != null) {
                fdd.setCo2SystemPressureBar(clamp(fdd.getCo2SystemPressureBar() + drift(0.05), 0, 200));
            }

            // Flood detection detail
            FloodDetectionDetail fldd = dc.getFloodDetectionDetail();
            if (fldd != null) {
                fldd.setTotalWaterIngressLpm(clamp(fldd.getTotalWaterIngressLpm() + drift(0.02), 0, 100));
            }

            // Stability detail
            StabilityDetail std = dc.getStabilityDetail();
            if (std != null) {
                std.setMetacentricHeightMeters(clamp(std.getMetacentricHeightMeters() + drift(0.002), 0.5, 2.5));
                std.setRollPeriodSeconds(clamp(std.getRollPeriodSeconds() + drift(0.05), 10, 25));
                std.setDisplacementTonnes(clamp(std.getDisplacementTonnes() + drift(0.5), 4600, 5000));
                std.setTrimMeters(clamp(std.getTrimMeters() + drift(0.001), -1, 1));
                double dispDelta = (std.getDisplacementTonnes() - 4770.0) * 0.0001;
                std.setDraftForwardMeters(clamp(std.getDraftForwardMeters() + dispDelta + drift(0.001), 3, 5));
                std.setDraftAftMeters(clamp(std.getDraftAftMeters() + dispDelta + drift(0.001), 3, 5));
            }
        }
    }

    /**
     * Keeps ships at least SHORE_BUFFER_KM (1 km) inside the navigable water boundary.
     * If a ship enters the exclusion zone the move is reverted and the heading is
     * reflected so the ship steers back into open water on the next tick.
     */
    private void applyShorelineGuardrail(ShipPayload ship) {
        double lat = ship.getLatitude();
        double lon = ship.getLongitude();

        // Convert 1 km buffer to degrees (latitude is constant; longitude varies with lat)
        double bufLat = (SHORE_BUFFER_KM * 1_000.0) / METERS_PER_DEGREE_LAT;
        double bufLon = (SHORE_BUFFER_KM * 1_000.0) / (METERS_PER_DEGREE_LAT * Math.cos(Math.toRadians(lat)));

        double minLat = NAV_SOUTH + bufLat;
        double maxLat = NAV_NORTH - bufLat;
        double minLon = NAV_WEST  + bufLon;
        double maxLon = NAV_EAST  - bufLon;

        double heading = ship.getHeadingDegrees();
        boolean violated = false;

        // North boundary
        if (lat > maxLat) {
            ship.setLatitude(maxLat);
            heading = reflectHeadingNS(heading);
            violated = true;
        // South boundary
        } else if (lat < minLat) {
            ship.setLatitude(minLat);
            heading = reflectHeadingNS(heading);
            violated = true;
        }

        // East boundary
        if (lon > maxLon) {
            ship.setLongitude(maxLon);
            heading = reflectHeadingEW(heading);
            violated = true;
        // West boundary
        } else if (lon < minLon) {
            ship.setLongitude(minLon);
            heading = reflectHeadingEW(heading);
            violated = true;
        }

        if (violated) {
            // Add a small random perturbation so ships don't get stuck pinging the same wall
            heading = (heading + (random.nextDouble() - 0.5) * 30 + 360) % 360;
            ship.setHeadingDegrees(heading);
            log.debug("Ship {} hit shoreline exclusion zone — heading corrected to {:.1f}°",
                    ship.getShipId(), heading);
        }
    }

    /** Reflect the north/south component of a heading (bounce off N or S boundary). */
    private double reflectHeadingNS(double heading) {
        double rad = Math.toRadians(heading);
        double dx  =  Math.sin(rad);   // east component — unchanged
        double dy  = -Math.cos(rad);   // north component — flipped
        return (Math.toDegrees(Math.atan2(dx, -dy)) + 360) % 360;
    }

    /** Reflect the east/west component of a heading (bounce off E or W boundary). */
    private double reflectHeadingEW(double heading) {
        double rad = Math.toRadians(heading);
        double dx  = -Math.sin(rad);   // east component — flipped
        double dy  =  Math.cos(rad);   // north component — unchanged
        return (Math.toDegrees(Math.atan2(dx, dy)) + 360) % 360;
    }

    /** Small random drift centred on zero */
    private double drift(double magnitude) {
        return (random.nextDouble() - 0.5) * 2 * magnitude;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public List<String> getShipIds() {
        return new ArrayList<>(fleet.keySet());
    }

    public Collection<ShipPayload> getAll() {
        return Collections.unmodifiableCollection(fleet.values());
    }

    public Optional<ShipPayload> getById(String id) {
        return Optional.ofNullable(fleet.get(id));
    }
}
