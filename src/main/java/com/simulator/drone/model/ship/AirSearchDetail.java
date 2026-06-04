package com.simulator.drone.model.ship;

public class AirSearchDetail {

    /** 0–6 RPM */
    private double antennaRotationRpm;
    /** 0–300 kW */
    private double transmitPowerKw;
    /** 0–450 km */
    private double rangeKm;
    /** 0 = no fault */
    private int    faultCodes;

    public AirSearchDetail() {}

    public AirSearchDetail(double antennaRotationRpm, double transmitPowerKw,
                           double rangeKm, int faultCodes) {
        this.antennaRotationRpm = antennaRotationRpm;
        this.transmitPowerKw    = transmitPowerKw;
        this.rangeKm            = rangeKm;
        this.faultCodes         = faultCodes;
    }

    public double getAntennaRotationRpm() { return antennaRotationRpm; }
    public void setAntennaRotationRpm(double antennaRotationRpm) { this.antennaRotationRpm = antennaRotationRpm; }

    public double getTransmitPowerKw() { return transmitPowerKw; }
    public void setTransmitPowerKw(double transmitPowerKw) { this.transmitPowerKw = transmitPowerKw; }

    public double getRangeKm() { return rangeKm; }
    public void setRangeKm(double rangeKm) { this.rangeKm = rangeKm; }

    public int getFaultCodes() { return faultCodes; }
    public void setFaultCodes(int faultCodes) { this.faultCodes = faultCodes; }

    @Override
    public String toString() {
        return "{" +
                "\"antennaRotationRpm\":" + antennaRotationRpm + "," +
                "\"transmitPowerKw\":"    + transmitPowerKw    + "," +
                "\"rangeKm\":"            + rangeKm            + "," +
                "\"faultCodes\":"         + faultCodes         +
                "}";
    }
}
