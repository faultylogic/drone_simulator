package com.simulator.drone.model.ship;

public class BoforsGunDetail {

    /** −10–85° */
    private double elevationDegrees;
    /** 0–360° */
    private double azimuthDegrees;
    /** 0–100% */
    private double barrelWearPercent;
    /** 20–80°C */
    private double coolingFluidTempCelsius;
    /** "READY", "LOADING", "JAMMED", "MAINTENANCE" */
    private String mountStatus;

    public BoforsGunDetail() {}

    public BoforsGunDetail(double elevationDegrees, double azimuthDegrees,
                           double barrelWearPercent, double coolingFluidTempCelsius,
                           String mountStatus) {
        this.elevationDegrees       = elevationDegrees;
        this.azimuthDegrees         = azimuthDegrees;
        this.barrelWearPercent      = barrelWearPercent;
        this.coolingFluidTempCelsius = coolingFluidTempCelsius;
        this.mountStatus            = mountStatus;
    }

    public double getElevationDegrees() { return elevationDegrees; }
    public void setElevationDegrees(double elevationDegrees) { this.elevationDegrees = elevationDegrees; }

    public double getAzimuthDegrees() { return azimuthDegrees; }
    public void setAzimuthDegrees(double azimuthDegrees) { this.azimuthDegrees = azimuthDegrees; }

    public double getBarrelWearPercent() { return barrelWearPercent; }
    public void setBarrelWearPercent(double barrelWearPercent) { this.barrelWearPercent = barrelWearPercent; }

    public double getCoolingFluidTempCelsius() { return coolingFluidTempCelsius; }
    public void setCoolingFluidTempCelsius(double coolingFluidTempCelsius) { this.coolingFluidTempCelsius = coolingFluidTempCelsius; }

    public String getMountStatus() { return mountStatus; }
    public void setMountStatus(String mountStatus) { this.mountStatus = mountStatus; }

    @Override
    public String toString() {
        return "{" +
                "\"elevationDegrees\":"       + elevationDegrees       + "," +
                "\"azimuthDegrees\":"         + azimuthDegrees         + "," +
                "\"barrelWearPercent\":"      + barrelWearPercent      + "," +
                "\"coolingFluidTempCelsius\":" + coolingFluidTempCelsius + "," +
                "\"mountStatus\":"            + (mountStatus != null ? "\"" + mountStatus + "\"" : "null") +
                "}";
    }
}
