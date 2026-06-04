package com.simulator.drone.model.ship;

public class FloodDetectionDetail {

    /** 0–20 */
    private int    highWaterSensorsActive;
    /** 0–10 */
    private int    floodSensorsTripped;
    /** 0–100 L/min */
    private double totalWaterIngressLpm;
    private boolean emergencyBilgePumpActive;

    public FloodDetectionDetail() {}

    public FloodDetectionDetail(int highWaterSensorsActive, int floodSensorsTripped,
                                double totalWaterIngressLpm, boolean emergencyBilgePumpActive) {
        this.highWaterSensorsActive   = highWaterSensorsActive;
        this.floodSensorsTripped      = floodSensorsTripped;
        this.totalWaterIngressLpm     = totalWaterIngressLpm;
        this.emergencyBilgePumpActive = emergencyBilgePumpActive;
    }

    public int getHighWaterSensorsActive() { return highWaterSensorsActive; }
    public void setHighWaterSensorsActive(int highWaterSensorsActive) { this.highWaterSensorsActive = highWaterSensorsActive; }

    public int getFloodSensorsTripped() { return floodSensorsTripped; }
    public void setFloodSensorsTripped(int floodSensorsTripped) { this.floodSensorsTripped = floodSensorsTripped; }

    public double getTotalWaterIngressLpm() { return totalWaterIngressLpm; }
    public void setTotalWaterIngressLpm(double totalWaterIngressLpm) { this.totalWaterIngressLpm = totalWaterIngressLpm; }

    public boolean isEmergencyBilgePumpActive() { return emergencyBilgePumpActive; }
    public void setEmergencyBilgePumpActive(boolean emergencyBilgePumpActive) { this.emergencyBilgePumpActive = emergencyBilgePumpActive; }

    @Override
    public String toString() {
        return "{" +
                "\"highWaterSensorsActive\":"   + highWaterSensorsActive   + "," +
                "\"floodSensorsTripped\":"      + floodSensorsTripped      + "," +
                "\"totalWaterIngressLpm\":"     + totalWaterIngressLpm     + "," +
                "\"emergencyBilgePumpActive\":" + emergencyBilgePumpActive +
                "}";
    }
}
