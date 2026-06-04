package com.simulator.drone.service;

import com.simulator.drone.model.DronePayload;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DroneMetricsCollector {

    private final MeterRegistry meterRegistry;
    private final Map<String, AtomicReference<DronePayload>> snapshots = new ConcurrentHashMap<>();

    public DroneMetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void record(DronePayload drone) {
        // Register gauges once per drone; update snapshot on every call
        final String shipTag = drone.getShipId() != null ? drone.getShipId() : "unassigned";

        snapshots.computeIfAbsent(drone.getDroneId(), id -> {
            AtomicReference<DronePayload> ref = new AtomicReference<>(drone);
            Gauge.builder("drone.battery.percent", ref, r -> r.get().getBatteryPercent())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.altitude.meters", ref, r -> r.get().getAltitudeMeters())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.speed.mps", ref, r -> r.get().getSpeedMps())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.heading.degrees", ref, r -> r.get().getHeadingDegrees())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.latitude", ref, r -> r.get().getLatitude())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.longitude", ref, r -> r.get().getLongitude())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            Gauge.builder("drone.payload.kg", ref, r -> r.get().getPayloadKg())
                    .tag("drone_id", id).tag("ship_id", shipTag).register(meterRegistry);
            return ref;
        }).set(drone);

        meterRegistry.counter("drone.updates.total",
                "drone_id", drone.getDroneId(),
                "ship_id",  shipTag).increment();
    }
}
