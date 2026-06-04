package com.simulator.drone.model.ship;

public class CIWSDetail {

    /** 0–360° */
    private double mountAzimuthDegrees;
    /** −20–80° */
    private double mountElevationDegrees;
    /** 0–3 000 m */
    private double radarRangeMeters;
    /** "STANDBY", "SEARCHING", "TRACKING" */
    private String trackingState;
    private boolean coolantAlarm;

    public CIWSDetail() {}

    public CIWSDetail(double mountAzimuthDegrees, double mountElevationDegrees,
                      double radarRangeMeters, String trackingState,
                      boolean coolantAlarm) {
        this.mountAzimuthDegrees   = mountAzimuthDegrees;
        this.mountElevationDegrees = mountElevationDegrees;
        this.radarRangeMeters      = radarRangeMeters;
        this.trackingState         = trackingState;
        this.coolantAlarm          = coolantAlarm;
    }

    public double getMountAzimuthDegrees() { return mountAzimuthDegrees; }
    public void setMountAzimuthDegrees(double mountAzimuthDegrees) { this.mountAzimuthDegrees = mountAzimuthDegrees; }

    public double getMountElevationDegrees() { return mountElevationDegrees; }
    public void setMountElevationDegrees(double mountElevationDegrees) { this.mountElevationDegrees = mountElevationDegrees; }

    public double getRadarRangeMeters() { return radarRangeMeters; }
    public void setRadarRangeMeters(double radarRangeMeters) { this.radarRangeMeters = radarRangeMeters; }

    public String getTrackingState() { return trackingState; }
    public void setTrackingState(String trackingState) { this.trackingState = trackingState; }

    public boolean isCoolantAlarm() { return coolantAlarm; }
    public void setCoolantAlarm(boolean coolantAlarm) { this.coolantAlarm = coolantAlarm; }

    @Override
    public String toString() {
        return "{" +
                "\"mountAzimuthDegrees\":"   + mountAzimuthDegrees   + "," +
                "\"mountElevationDegrees\":" + mountElevationDegrees + "," +
                "\"radarRangeMeters\":"      + radarRangeMeters      + "," +
                "\"trackingState\":"         + (trackingState != null ? "\"" + trackingState + "\"" : "null") + "," +
                "\"coolantAlarm\":"          + coolantAlarm          +
                "}";
    }
}
