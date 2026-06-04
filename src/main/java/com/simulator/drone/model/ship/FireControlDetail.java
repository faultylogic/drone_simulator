package com.simulator.drone.model.ship;

public class FireControlDetail {

    /** 0–360° */
    private double trackingAzimuthDegrees;
    /** −5–90° */
    private double trackingElevationDegrees;
    /** 0–40 dB */
    private double signalToNoiseDb;
    private boolean targetIlluminating;
    /** 0–2 */
    private int    targetsTracked;

    public FireControlDetail() {}

    public FireControlDetail(double trackingAzimuthDegrees, double trackingElevationDegrees,
                             double signalToNoiseDb, boolean targetIlluminating,
                             int targetsTracked) {
        this.trackingAzimuthDegrees   = trackingAzimuthDegrees;
        this.trackingElevationDegrees = trackingElevationDegrees;
        this.signalToNoiseDb          = signalToNoiseDb;
        this.targetIlluminating       = targetIlluminating;
        this.targetsTracked           = targetsTracked;
    }

    public double getTrackingAzimuthDegrees() { return trackingAzimuthDegrees; }
    public void setTrackingAzimuthDegrees(double trackingAzimuthDegrees) { this.trackingAzimuthDegrees = trackingAzimuthDegrees; }

    public double getTrackingElevationDegrees() { return trackingElevationDegrees; }
    public void setTrackingElevationDegrees(double trackingElevationDegrees) { this.trackingElevationDegrees = trackingElevationDegrees; }

    public double getSignalToNoiseDb() { return signalToNoiseDb; }
    public void setSignalToNoiseDb(double signalToNoiseDb) { this.signalToNoiseDb = signalToNoiseDb; }

    public boolean isTargetIlluminating() { return targetIlluminating; }
    public void setTargetIlluminating(boolean targetIlluminating) { this.targetIlluminating = targetIlluminating; }

    public int getTargetsTracked() { return targetsTracked; }
    public void setTargetsTracked(int targetsTracked) { this.targetsTracked = targetsTracked; }

    @Override
    public String toString() {
        return "{" +
                "\"trackingAzimuthDegrees\":"   + trackingAzimuthDegrees   + "," +
                "\"trackingElevationDegrees\":" + trackingElevationDegrees + "," +
                "\"signalToNoiseDb\":"          + signalToNoiseDb          + "," +
                "\"targetIlluminating\":"       + targetIlluminating       + "," +
                "\"targetsTracked\":"           + targetsTracked           +
                "}";
    }
}
