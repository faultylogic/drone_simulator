package com.simulator.drone.model.ship;

public class SurfaceSearchDetail {

    /** 0–24 RPM */
    private double antennaRotationRpm;
    /** 0–50 kW */
    private double transmitPowerKw;
    /** 0–100 km */
    private double rangeKm;
    private boolean rainfallCompensation;

    public SurfaceSearchDetail() {}

    public SurfaceSearchDetail(double antennaRotationRpm, double transmitPowerKw,
                               double rangeKm, boolean rainfallCompensation) {
        this.antennaRotationRpm   = antennaRotationRpm;
        this.transmitPowerKw      = transmitPowerKw;
        this.rangeKm              = rangeKm;
        this.rainfallCompensation = rainfallCompensation;
    }

    public double getAntennaRotationRpm() { return antennaRotationRpm; }
    public void setAntennaRotationRpm(double antennaRotationRpm) { this.antennaRotationRpm = antennaRotationRpm; }

    public double getTransmitPowerKw() { return transmitPowerKw; }
    public void setTransmitPowerKw(double transmitPowerKw) { this.transmitPowerKw = transmitPowerKw; }

    public double getRangeKm() { return rangeKm; }
    public void setRangeKm(double rangeKm) { this.rangeKm = rangeKm; }

    public boolean isRainfallCompensation() { return rainfallCompensation; }
    public void setRainfallCompensation(boolean rainfallCompensation) { this.rainfallCompensation = rainfallCompensation; }

    @Override
    public String toString() {
        return "{" +
                "\"antennaRotationRpm\":"  + antennaRotationRpm  + "," +
                "\"transmitPowerKw\":"     + transmitPowerKw     + "," +
                "\"rangeKm\":"             + rangeKm             + "," +
                "\"rainfallCompensation\":" + rainfallCompensation +
                "}";
    }
}
