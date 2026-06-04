package com.simulator.drone.model.ship;

public class ShaftDetail {

    /** 0–500 kN·m */
    private double torqueKnm;
    /** 40–80°C */
    private double bearingTempCelsius;
    /** 0.1–3 mm/s */
    private double vibrationMms;
    /** 0–2 L/min */
    private double sealLeakRateLpm;
    /** −100–100% */
    private double propellerPitchPercent;

    public ShaftDetail() {}

    public ShaftDetail(double torqueKnm, double bearingTempCelsius,
                       double vibrationMms, double sealLeakRateLpm,
                       double propellerPitchPercent) {
        this.torqueKnm            = torqueKnm;
        this.bearingTempCelsius   = bearingTempCelsius;
        this.vibrationMms         = vibrationMms;
        this.sealLeakRateLpm      = sealLeakRateLpm;
        this.propellerPitchPercent = propellerPitchPercent;
    }

    public double getTorqueKnm() { return torqueKnm; }
    public void setTorqueKnm(double torqueKnm) { this.torqueKnm = torqueKnm; }

    public double getBearingTempCelsius() { return bearingTempCelsius; }
    public void setBearingTempCelsius(double bearingTempCelsius) { this.bearingTempCelsius = bearingTempCelsius; }

    public double getVibrationMms() { return vibrationMms; }
    public void setVibrationMms(double vibrationMms) { this.vibrationMms = vibrationMms; }

    public double getSealLeakRateLpm() { return sealLeakRateLpm; }
    public void setSealLeakRateLpm(double sealLeakRateLpm) { this.sealLeakRateLpm = sealLeakRateLpm; }

    public double getPropellerPitchPercent() { return propellerPitchPercent; }
    public void setPropellerPitchPercent(double propellerPitchPercent) { this.propellerPitchPercent = propellerPitchPercent; }

    @Override
    public String toString() {
        return "{" +
                "\"torqueKnm\":"             + torqueKnm             + "," +
                "\"bearingTempCelsius\":"    + bearingTempCelsius    + "," +
                "\"vibrationMms\":"          + vibrationMms          + "," +
                "\"sealLeakRateLpm\":"       + sealLeakRateLpm       + "," +
                "\"propellerPitchPercent\":" + propellerPitchPercent +
                "}";
    }
}
