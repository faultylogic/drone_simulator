package com.simulator.drone.service;

import com.simulator.drone.model.ShipPayload;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ShipMetricsCollector {

    private final MeterRegistry meterRegistry;
    private final Map<String, AtomicReference<ShipPayload>> snapshots = new ConcurrentHashMap<>();

    public ShipMetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void record(ShipPayload ship) {
        snapshots.computeIfAbsent(ship.getShipId(), id -> {
            AtomicReference<ShipPayload> ref = new AtomicReference<>(ship);

            // Identity metric — always 1.0, exists purely to advertise the ship_id dimension
            Gauge.builder("ship.info", ref, r -> 1.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Core navigation / crew gauges (kept)
            Gauge.builder("ship.speed.knots",     ref, r -> r.get().getSpeedKnots())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.heading.degrees", ref, r -> r.get().getHeadingDegrees())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.latitude",        ref, r -> r.get().getLatitude())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.longitude",       ref, r -> r.get().getLongitude())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.crew.active",     ref, r -> (double) r.get().getCrewActive())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.drone.count",     ref, r -> (double) r.get().getDroneCount())
                    .tag("ship_id", id).register(meterRegistry);

            // Propulsion
            Gauge.builder("ship.propulsion.gt1.percent",          ref, r -> r.get().getPropulsion().getGasTurbine1Percent())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.percent",          ref, r -> r.get().getPropulsion().getGasTurbine2Percent())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.percent",       ref, r -> r.get().getPropulsion().getDieselOutputPercent())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.port.shaft.rpm",       ref, r -> r.get().getPropulsion().getPortShaftRpm())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.stbd.shaft.rpm",       ref, r -> r.get().getPropulsion().getStbdShaftRpm())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gearbox.temp.celsius", ref, r -> r.get().getPropulsion().getGearboxTempCelsius())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.fuel.consumption.lph", ref, r -> r.get().getPropulsion().getFuelConsumptionLph())
                    .tag("ship_id", id).register(meterRegistry);

            // Power
            Gauge.builder("ship.power.gen1.kw",          ref, r -> r.get().getPower().getGen1Kw())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen2.kw",          ref, r -> r.get().getPower().getGen2Kw())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen3.kw",          ref, r -> r.get().getPower().getGen3Kw())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen4.kw",          ref, r -> r.get().getPower().getGen4Kw())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.total.load.kw",    ref, r -> r.get().getPower().getTotalLoadKw())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.voltage.volts", ref, r -> r.get().getPower().getBusVoltageVolts())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.frequency.hz",     ref, r -> r.get().getPower().getFrequencyHz())
                    .tag("ship_id", id).register(meterRegistry);

            // Weapons
            Gauge.builder("ship.weapons.bofors.operational",      ref, r -> r.get().getWeapons().isBofors57mmOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.bofors.ammo.rounds",      ref, r -> (double) r.get().getWeapons().getBofors57mmAmmoRounds())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.bofors.barrel.temp.celsius", ref, r -> r.get().getWeapons().getBofors57mmBarrelTempCelsius())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.cisws.operational",       ref, r -> r.get().getWeapons().isCiswsOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.cisws.ammo.rounds",       ref, r -> (double) r.get().getWeapons().getCiswsAmmoRounds())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.harpoon.ready",           ref, r -> (double) r.get().getWeapons().getHarpoonMissilesReady())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.vls.cells.ready",         ref, r -> (double) r.get().getWeapons().getVlsEssmCellsReady())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.torpedoes.ready",         ref, r -> (double) r.get().getWeapons().getMk46TorpedoesReady())
                    .tag("ship_id", id).register(meterRegistry);

            // Sonar
            Gauge.builder("ship.sonar.hull.operational",  ref, r -> r.get().getSonar().isHullSonarOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.deployed",    ref, r -> r.get().getSonar().isCantassTowedArrayDeployed() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.cable.meters", ref, r -> r.get().getSonar().getTowedArrayCableOutMeters())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.contacts.tracked",  ref, r -> (double) r.get().getSonar().getActiveContactsTracked())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.detection.range.km", ref, r -> r.get().getSonar().getDetectionRangeKm())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.ambient.noise.db",  ref, r -> r.get().getSonar().getAmbientNoiseDb())
                    .tag("ship_id", id).register(meterRegistry);

            // Radar
            Gauge.builder("ship.radar.air.search.operational",     ref, r -> r.get().getRadar().isAirSearchOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.surface.search.operational", ref, r -> r.get().getRadar().isSurfaceSearchOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.fire.control.operational",   ref, r -> r.get().getRadar().isFireControlOperational() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.air.tracks",                 ref, r -> (double) r.get().getRadar().getAirTracksDetected())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.surface.tracks",             ref, r -> (double) r.get().getRadar().getSurfaceTracksDetected())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.esm.alert",                  ref, r -> r.get().getRadar().isEsmAlertActive() ? 1.0 : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Water
            Gauge.builder("ship.water.fresh.percent",          ref, r -> r.get().getWaterSystems().getPotableFreshWaterPercent())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.cooling.temp.celsius",   ref, r -> r.get().getWaterSystems().getSeawaterCoolingTempCelsius())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.bilge.level.cm",         ref, r -> r.get().getWaterSystems().getBilgeWaterLevelCm())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.fire.main.pressure.bar", ref, r -> r.get().getWaterSystems().getFireMainPressureBar())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.desalinator.output.lph", ref, r -> r.get().getWaterSystems().getDesalinatorOutputLph())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.sewage.percent",         ref, r -> r.get().getWaterSystems().getSewageTankPercent())
                    .tag("ship_id", id).register(meterRegistry);

            // Damage Control
            Gauge.builder("ship.dc.hull.integrity.percent", ref, r -> r.get().getDamageControl().getHullIntegrityPercent())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.roll.degrees",           ref, r -> r.get().getDamageControl().getRollDegrees())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.pitch.degrees",          ref, r -> r.get().getDamageControl().getPitchDegrees())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.heel.degrees",           ref, r -> r.get().getDamageControl().getHeelDegrees())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.fire.zones",             ref, r -> (double) r.get().getDamageControl().getFireZonesActive())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.flood.zones",            ref, r -> (double) r.get().getDamageControl().getFloodZonesActive())
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.water.ingress.lpm",      ref, r -> r.get().getDamageControl().getWaterIngressLpm())
                    .tag("ship_id", id).register(meterRegistry);

            // Propulsion component details
            Gauge.builder("ship.propulsion.gt1.inlet.temp.celsius",   ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getInletTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt1.exhaust.temp.celsius", ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getExhaustTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt1.compressor.rpm",       ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getCompressorSpeedRpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt1.oil.pressure.bar",     ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getOilPressureBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt1.oil.temp.celsius",     ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getOilTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt1.vibration.mms",        ref, r -> r.get().getPropulsion().getGasTurbine1Detail() != null ? r.get().getPropulsion().getGasTurbine1Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.propulsion.gt2.inlet.temp.celsius",   ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getInletTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.exhaust.temp.celsius", ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getExhaustTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.compressor.rpm",       ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getCompressorSpeedRpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.oil.pressure.bar",     ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getOilPressureBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.oil.temp.celsius",     ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getOilTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.gt2.vibration.mms",        ref, r -> r.get().getPropulsion().getGasTurbine2Detail() != null ? r.get().getPropulsion().getGasTurbine2Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.propulsion.diesel.coolant.temp.celsius", ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getCoolantTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.oil.pressure.bar",     ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getOilPressureBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.oil.temp.celsius",     ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getOilTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.exhaust.temp.celsius", ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getExhaustTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.turbo.boost.bar",      ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getTurbochargerBoostBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.diesel.rpm",                  ref, r -> r.get().getPropulsion().getDieselDetail() != null ? r.get().getPropulsion().getDieselDetail().getRpmActual() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.propulsion.port.shaft.torque.knm",       ref, r -> r.get().getPropulsion().getPortShaftDetail() != null ? r.get().getPropulsion().getPortShaftDetail().getTorqueKnm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.port.shaft.bearing.temp.celsius", ref, r -> r.get().getPropulsion().getPortShaftDetail() != null ? r.get().getPropulsion().getPortShaftDetail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.port.shaft.vibration.mms",    ref, r -> r.get().getPropulsion().getPortShaftDetail() != null ? r.get().getPropulsion().getPortShaftDetail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.port.shaft.seal.leak.lpm",    ref, r -> r.get().getPropulsion().getPortShaftDetail() != null ? r.get().getPropulsion().getPortShaftDetail().getSealLeakRateLpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.port.shaft.pitch.percent",    ref, r -> r.get().getPropulsion().getPortShaftDetail() != null ? r.get().getPropulsion().getPortShaftDetail().getPropellerPitchPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.propulsion.stbd.shaft.torque.knm",       ref, r -> r.get().getPropulsion().getStbdShaftDetail() != null ? r.get().getPropulsion().getStbdShaftDetail().getTorqueKnm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.stbd.shaft.bearing.temp.celsius", ref, r -> r.get().getPropulsion().getStbdShaftDetail() != null ? r.get().getPropulsion().getStbdShaftDetail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.stbd.shaft.vibration.mms",    ref, r -> r.get().getPropulsion().getStbdShaftDetail() != null ? r.get().getPropulsion().getStbdShaftDetail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.stbd.shaft.seal.leak.lpm",    ref, r -> r.get().getPropulsion().getStbdShaftDetail() != null ? r.get().getPropulsion().getStbdShaftDetail().getSealLeakRateLpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.propulsion.stbd.shaft.pitch.percent",    ref, r -> r.get().getPropulsion().getStbdShaftDetail() != null ? r.get().getPropulsion().getStbdShaftDetail().getPropellerPitchPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Power component details
            Gauge.builder("ship.power.gen1.voltage.volts",       ref, r -> r.get().getPower().getGen1Detail() != null ? r.get().getPower().getGen1Detail().getOutputVoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen1.load.percent",        ref, r -> r.get().getPower().getGen1Detail() != null ? r.get().getPower().getGen1Detail().getLoadPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen1.winding.temp.celsius", ref, r -> r.get().getPower().getGen1Detail() != null ? r.get().getPower().getGen1Detail().getWindingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen1.bearing.temp.celsius", ref, r -> r.get().getPower().getGen1Detail() != null ? r.get().getPower().getGen1Detail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen1.vibration.mms",       ref, r -> r.get().getPower().getGen1Detail() != null ? r.get().getPower().getGen1Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.power.gen2.voltage.volts",       ref, r -> r.get().getPower().getGen2Detail() != null ? r.get().getPower().getGen2Detail().getOutputVoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen2.load.percent",        ref, r -> r.get().getPower().getGen2Detail() != null ? r.get().getPower().getGen2Detail().getLoadPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen2.winding.temp.celsius", ref, r -> r.get().getPower().getGen2Detail() != null ? r.get().getPower().getGen2Detail().getWindingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen2.bearing.temp.celsius", ref, r -> r.get().getPower().getGen2Detail() != null ? r.get().getPower().getGen2Detail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen2.vibration.mms",       ref, r -> r.get().getPower().getGen2Detail() != null ? r.get().getPower().getGen2Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.power.gen3.voltage.volts",       ref, r -> r.get().getPower().getGen3Detail() != null ? r.get().getPower().getGen3Detail().getOutputVoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen3.load.percent",        ref, r -> r.get().getPower().getGen3Detail() != null ? r.get().getPower().getGen3Detail().getLoadPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen3.winding.temp.celsius", ref, r -> r.get().getPower().getGen3Detail() != null ? r.get().getPower().getGen3Detail().getWindingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen3.bearing.temp.celsius", ref, r -> r.get().getPower().getGen3Detail() != null ? r.get().getPower().getGen3Detail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen3.vibration.mms",       ref, r -> r.get().getPower().getGen3Detail() != null ? r.get().getPower().getGen3Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.power.gen4.voltage.volts",       ref, r -> r.get().getPower().getGen4Detail() != null ? r.get().getPower().getGen4Detail().getOutputVoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen4.load.percent",        ref, r -> r.get().getPower().getGen4Detail() != null ? r.get().getPower().getGen4Detail().getLoadPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen4.winding.temp.celsius", ref, r -> r.get().getPower().getGen4Detail() != null ? r.get().getPower().getGen4Detail().getWindingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen4.bearing.temp.celsius", ref, r -> r.get().getPower().getGen4Detail() != null ? r.get().getPower().getGen4Detail().getBearingTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.gen4.vibration.mms",       ref, r -> r.get().getPower().getGen4Detail() != null ? r.get().getPower().getGen4Detail().getVibrationMms() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.power.bus.phase1.voltage.volts",        ref, r -> r.get().getPower().getBusDetail() != null ? r.get().getPower().getBusDetail().getPhase1VoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.phase2.voltage.volts",        ref, r -> r.get().getPower().getBusDetail() != null ? r.get().getPower().getBusDetail().getPhase2VoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.phase3.voltage.volts",        ref, r -> r.get().getPower().getBusDetail() != null ? r.get().getPower().getBusDetail().getPhase3VoltageVolts() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.power.factor.percent",        ref, r -> r.get().getPower().getBusDetail() != null ? r.get().getPower().getBusDetail().getPowerFactorPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.harmonic.distortion.percent", ref, r -> r.get().getPower().getBusDetail() != null ? r.get().getPower().getBusDetail().getHarmonicDistortionPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.power.bus.breaker.trips",               ref, r -> r.get().getPower().getBusDetail() != null ? (double) r.get().getPower().getBusDetail().getActiveBreakerTrips() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Weapon component details
            Gauge.builder("ship.weapons.bofors.elevation.degrees",   ref, r -> r.get().getWeapons().getBofors57mmDetail() != null ? r.get().getWeapons().getBofors57mmDetail().getElevationDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.bofors.azimuth.degrees",     ref, r -> r.get().getWeapons().getBofors57mmDetail() != null ? r.get().getWeapons().getBofors57mmDetail().getAzimuthDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.bofors.barrel.wear.percent", ref, r -> r.get().getWeapons().getBofors57mmDetail() != null ? r.get().getWeapons().getBofors57mmDetail().getBarrelWearPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.bofors.cooling.temp.celsius", ref, r -> r.get().getWeapons().getBofors57mmDetail() != null ? r.get().getWeapons().getBofors57mmDetail().getCoolingFluidTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.weapons.cisws.azimuth.degrees",   ref, r -> r.get().getWeapons().getCiswsDetail() != null ? r.get().getWeapons().getCiswsDetail().getMountAzimuthDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.cisws.elevation.degrees", ref, r -> r.get().getWeapons().getCiswsDetail() != null ? r.get().getWeapons().getCiswsDetail().getMountElevationDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.cisws.radar.range.meters", ref, r -> r.get().getWeapons().getCiswsDetail() != null ? r.get().getWeapons().getCiswsDetail().getRadarRangeMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.weapons.harpoon.canister1.temp.celsius", ref, r -> r.get().getWeapons().getHarpoonDetail() != null ? r.get().getWeapons().getHarpoonDetail().getCanister1TempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.harpoon.canister2.temp.celsius", ref, r -> r.get().getWeapons().getHarpoonDetail() != null ? r.get().getWeapons().getHarpoonDetail().getCanister2TempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.harpoon.canister3.temp.celsius", ref, r -> r.get().getWeapons().getHarpoonDetail() != null ? r.get().getWeapons().getHarpoonDetail().getCanister3TempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.harpoon.canister4.temp.celsius", ref, r -> r.get().getWeapons().getHarpoonDetail() != null ? r.get().getWeapons().getHarpoonDetail().getCanister4TempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.harpoon.missiles.fired",         ref, r -> r.get().getWeapons().getHarpoonDetail() != null ? (double) r.get().getWeapons().getHarpoonDetail().getMissilesFired() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.weapons.vls.cells.occupied",   ref, r -> r.get().getWeapons().getVlsDetail() != null ? (double) r.get().getWeapons().getVlsDetail().getCellsOccupied() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.vls.coolant.temp.celsius", ref, r -> r.get().getWeapons().getVlsDetail() != null ? r.get().getWeapons().getVlsDetail().getCoolantTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.vls.cells.fired",      ref, r -> r.get().getWeapons().getVlsDetail() != null ? (double) r.get().getWeapons().getVlsDetail().getCellsFired() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.weapons.torpedo.tubes.armed",     ref, r -> r.get().getWeapons().getTorpedoDetail() != null ? (double) r.get().getWeapons().getTorpedoDetail().getTubesArmed() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.torpedo.guidance.percent", ref, r -> r.get().getWeapons().getTorpedoDetail() != null ? r.get().getWeapons().getTorpedoDetail().getGuidanceSystemPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.weapons.torpedo.fired",            ref, r -> r.get().getWeapons().getTorpedoDetail() != null ? (double) r.get().getWeapons().getTorpedoDetail().getTorpedoesFired() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Sonar component details
            Gauge.builder("ship.sonar.hull.active.elements",   ref, r -> r.get().getSonar().getHullSonarDetail() != null ? (double) r.get().getSonar().getHullSonarDetail().getActiveElements() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.hull.transmit.power.kw", ref, r -> r.get().getSonar().getHullSonarDetail() != null ? r.get().getSonar().getHullSonarDetail().getTransmitPowerKw() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.hull.self.noise.db",     ref, r -> r.get().getSonar().getHullSonarDetail() != null ? r.get().getSonar().getHullSonarDetail().getSelfNoiseDb() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.hull.water.temp.celsius", ref, r -> r.get().getSonar().getHullSonarDetail() != null ? r.get().getSonar().getHullSonarDetail().getWaterTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.hull.salinity.psu",      ref, r -> r.get().getSonar().getHullSonarDetail() != null ? r.get().getSonar().getHullSonarDetail().getSalinityPsu() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.sonar.towed.depth.meters",      ref, r -> r.get().getSonar().getTowedArrayDetail() != null ? r.get().getSonar().getTowedArrayDetail().getDepthMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.tension.newtons",   ref, r -> r.get().getSonar().getTowedArrayDetail() != null ? r.get().getSonar().getTowedArrayDetail().getTensionNewtons() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.active.elements",   ref, r -> r.get().getSonar().getTowedArrayDetail() != null ? (double) r.get().getSonar().getTowedArrayDetail().getActiveElements() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.array.temp.celsius", ref, r -> r.get().getSonar().getTowedArrayDetail() != null ? r.get().getSonar().getTowedArrayDetail().getArrayTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.sonar.towed.layback.meters",    ref, r -> r.get().getSonar().getTowedArrayDetail() != null ? r.get().getSonar().getTowedArrayDetail().getLaybackMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Radar component details
            Gauge.builder("ship.radar.air.antenna.rpm",       ref, r -> r.get().getRadar().getAirSearchDetail() != null ? r.get().getRadar().getAirSearchDetail().getAntennaRotationRpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.air.transmit.power.kw", ref, r -> r.get().getRadar().getAirSearchDetail() != null ? r.get().getRadar().getAirSearchDetail().getTransmitPowerKw() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.air.range.km",          ref, r -> r.get().getRadar().getAirSearchDetail() != null ? r.get().getRadar().getAirSearchDetail().getRangeKm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.air.fault.codes",       ref, r -> r.get().getRadar().getAirSearchDetail() != null ? (double) r.get().getRadar().getAirSearchDetail().getFaultCodes() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.radar.surface.antenna.rpm",       ref, r -> r.get().getRadar().getSurfaceSearchDetail() != null ? r.get().getRadar().getSurfaceSearchDetail().getAntennaRotationRpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.surface.transmit.power.kw", ref, r -> r.get().getRadar().getSurfaceSearchDetail() != null ? r.get().getRadar().getSurfaceSearchDetail().getTransmitPowerKw() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.surface.range.km",          ref, r -> r.get().getRadar().getSurfaceSearchDetail() != null ? r.get().getRadar().getSurfaceSearchDetail().getRangeKm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.radar.fc.azimuth.degrees",   ref, r -> r.get().getRadar().getFireControlDetail() != null ? r.get().getRadar().getFireControlDetail().getTrackingAzimuthDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.fc.elevation.degrees", ref, r -> r.get().getRadar().getFireControlDetail() != null ? r.get().getRadar().getFireControlDetail().getTrackingElevationDegrees() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.fc.snr.db",            ref, r -> r.get().getRadar().getFireControlDetail() != null ? r.get().getRadar().getFireControlDetail().getSignalToNoiseDb() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.fc.targets.tracked",   ref, r -> r.get().getRadar().getFireControlDetail() != null ? (double) r.get().getRadar().getFireControlDetail().getTargetsTracked() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.radar.esm.signal.strength.dbm", ref, r -> r.get().getRadar().getEsmDetail() != null ? r.get().getRadar().getEsmDetail().getSignalStrengthDbm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.radar.esm.emitters.detected",   ref, r -> r.get().getRadar().getEsmDetail() != null ? (double) r.get().getRadar().getEsmDetail().getEmittersDetected() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Water component details
            Gauge.builder("ship.water.cooling.flow.lpm",          ref, r -> r.get().getWaterSystems().getCoolingDetail() != null ? r.get().getWaterSystems().getCoolingDetail().getFlowRateLpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.cooling.inlet.temp.celsius", ref, r -> r.get().getWaterSystems().getCoolingDetail() != null ? r.get().getWaterSystems().getCoolingDetail().getInletTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.cooling.outlet.temp.celsius", ref, r -> r.get().getWaterSystems().getCoolingDetail() != null ? r.get().getWaterSystems().getCoolingDetail().getOutletTempCelsius() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.cooling.filter.dp.bar",     ref, r -> r.get().getWaterSystems().getCoolingDetail() != null ? r.get().getWaterSystems().getCoolingDetail().getFilterDpBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.water.firefighting.foam.percent",    ref, r -> r.get().getWaterSystems().getFirefightingDetail() != null ? r.get().getWaterSystems().getFirefightingDetail().getFoamAgentPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.firefighting.pressure.bar",    ref, r -> r.get().getWaterSystems().getFirefightingDetail() != null ? r.get().getWaterSystems().getFirefightingDetail().getSystemPressureBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.firefighting.zones.isolated",  ref, r -> r.get().getWaterSystems().getFirefightingDetail() != null ? (double) r.get().getWaterSystems().getFirefightingDetail().getZonesIsolated() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.water.bilge.forward.cm",     ref, r -> r.get().getWaterSystems().getBilgeDetail() != null ? r.get().getWaterSystems().getBilgeDetail().getForwardBilgeCm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.bilge.midships.cm",    ref, r -> r.get().getWaterSystems().getBilgeDetail() != null ? r.get().getWaterSystems().getBilgeDetail().getMidshipsBilgeCm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.bilge.aft.cm",         ref, r -> r.get().getWaterSystems().getBilgeDetail() != null ? r.get().getWaterSystems().getBilgeDetail().getAftBilgeCm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.water.bilge.engine.room.cm", ref, r -> r.get().getWaterSystems().getBilgeDetail() != null ? r.get().getWaterSystems().getBilgeDetail().getEngineRoomBilgeCm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            // Damage control component details
            Gauge.builder("ship.dc.hull.zone1.percent", ref, r -> r.get().getDamageControl().getHullZoneDetail() != null ? r.get().getDamageControl().getHullZoneDetail().getZone1IntegrityPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.hull.zone2.percent", ref, r -> r.get().getDamageControl().getHullZoneDetail() != null ? r.get().getDamageControl().getHullZoneDetail().getZone2IntegrityPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.hull.zone3.percent", ref, r -> r.get().getDamageControl().getHullZoneDetail() != null ? r.get().getDamageControl().getHullZoneDetail().getZone3IntegrityPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.hull.zone4.percent", ref, r -> r.get().getDamageControl().getHullZoneDetail() != null ? r.get().getDamageControl().getHullZoneDetail().getZone4IntegrityPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.hull.zone5.percent", ref, r -> r.get().getDamageControl().getHullZoneDetail() != null ? r.get().getDamageControl().getHullZoneDetail().getZone5IntegrityPercent() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.dc.fire.smoke.detectors", ref, r -> r.get().getDamageControl().getFireDetectionDetail() != null ? (double) r.get().getDamageControl().getFireDetectionDetail().getSmokeDetectorsActive() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.fire.heat.detectors",  ref, r -> r.get().getDamageControl().getFireDetectionDetail() != null ? (double) r.get().getDamageControl().getFireDetectionDetail().getHeatDetectorsActive() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.fire.zones.alarm",     ref, r -> r.get().getDamageControl().getFireDetectionDetail() != null ? (double) r.get().getDamageControl().getFireDetectionDetail().getZonesInAlarm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.fire.co2.pressure.bar", ref, r -> r.get().getDamageControl().getFireDetectionDetail() != null ? r.get().getDamageControl().getFireDetectionDetail().getCo2SystemPressureBar() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.dc.flood.high.water.sensors", ref, r -> r.get().getDamageControl().getFloodDetectionDetail() != null ? (double) r.get().getDamageControl().getFloodDetectionDetail().getHighWaterSensorsActive() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.flood.sensors.tripped",    ref, r -> r.get().getDamageControl().getFloodDetectionDetail() != null ? (double) r.get().getDamageControl().getFloodDetectionDetail().getFloodSensorsTripped() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.flood.total.ingress.lpm",  ref, r -> r.get().getDamageControl().getFloodDetectionDetail() != null ? r.get().getDamageControl().getFloodDetectionDetail().getTotalWaterIngressLpm() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            Gauge.builder("ship.dc.stability.gm.meters",          ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getMetacentricHeightMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.stability.roll.period.seconds", ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getRollPeriodSeconds() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.stability.displacement.tonnes", ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getDisplacementTonnes() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.stability.trim.meters",         ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getTrimMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.stability.draft.forward.meters", ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getDraftForwardMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);
            Gauge.builder("ship.dc.stability.draft.aft.meters",    ref, r -> r.get().getDamageControl().getStabilityDetail() != null ? r.get().getDamageControl().getStabilityDetail().getDraftAftMeters() : 0.0)
                    .tag("ship_id", id).register(meterRegistry);

            return ref;
        }).set(ship);

        meterRegistry.counter("ship.updates.total", "ship_id", ship.getShipId()).increment();
    }
}
