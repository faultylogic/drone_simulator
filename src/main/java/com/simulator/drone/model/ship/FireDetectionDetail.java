package com.simulator.drone.model.ship;

public class FireDetectionDetail {

    /** 0–50 */
    private int    smokeDetectorsActive;
    /** 0–50 */
    private int    heatDetectorsActive;
    /** 0–10 */
    private int    zonesInAlarm;
    /** engine room suppression */
    private boolean halon1301Armed;
    /** 0–200 bar */
    private double  co2SystemPressureBar;

    public FireDetectionDetail() {}

    public FireDetectionDetail(int smokeDetectorsActive, int heatDetectorsActive,
                               int zonesInAlarm, boolean halon1301Armed,
                               double co2SystemPressureBar) {
        this.smokeDetectorsActive = smokeDetectorsActive;
        this.heatDetectorsActive  = heatDetectorsActive;
        this.zonesInAlarm         = zonesInAlarm;
        this.halon1301Armed       = halon1301Armed;
        this.co2SystemPressureBar = co2SystemPressureBar;
    }

    public int getSmokeDetectorsActive() { return smokeDetectorsActive; }
    public void setSmokeDetectorsActive(int smokeDetectorsActive) { this.smokeDetectorsActive = smokeDetectorsActive; }

    public int getHeatDetectorsActive() { return heatDetectorsActive; }
    public void setHeatDetectorsActive(int heatDetectorsActive) { this.heatDetectorsActive = heatDetectorsActive; }

    public int getZonesInAlarm() { return zonesInAlarm; }
    public void setZonesInAlarm(int zonesInAlarm) { this.zonesInAlarm = zonesInAlarm; }

    public boolean isHalon1301Armed() { return halon1301Armed; }
    public void setHalon1301Armed(boolean halon1301Armed) { this.halon1301Armed = halon1301Armed; }

    public double getCo2SystemPressureBar() { return co2SystemPressureBar; }
    public void setCo2SystemPressureBar(double co2SystemPressureBar) { this.co2SystemPressureBar = co2SystemPressureBar; }

    @Override
    public String toString() {
        return "{" +
                "\"smokeDetectorsActive\":" + smokeDetectorsActive + "," +
                "\"heatDetectorsActive\":"  + heatDetectorsActive  + "," +
                "\"zonesInAlarm\":"         + zonesInAlarm         + "," +
                "\"halon1301Armed\":"       + halon1301Armed       + "," +
                "\"co2SystemPressureBar\":" + co2SystemPressureBar +
                "}";
    }
}
