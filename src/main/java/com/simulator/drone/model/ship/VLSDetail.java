package com.simulator.drone.model.ship;

public class VLSDetail {

    /** 0–8 */
    private int    cellsOccupied;
    /** running count */
    private int    cellsFired;
    /** 15–35°C */
    private double coolantTempCelsius;
    private boolean coolantAlarm;
    /** "SAFE", "ARMED", "FIRING" */
    private String launchStatus;

    public VLSDetail() {}

    public VLSDetail(int cellsOccupied, int cellsFired, double coolantTempCelsius,
                     boolean coolantAlarm, String launchStatus) {
        this.cellsOccupied      = cellsOccupied;
        this.cellsFired         = cellsFired;
        this.coolantTempCelsius = coolantTempCelsius;
        this.coolantAlarm       = coolantAlarm;
        this.launchStatus       = launchStatus;
    }

    public int getCellsOccupied() { return cellsOccupied; }
    public void setCellsOccupied(int cellsOccupied) { this.cellsOccupied = cellsOccupied; }

    public int getCellsFired() { return cellsFired; }
    public void setCellsFired(int cellsFired) { this.cellsFired = cellsFired; }

    public double getCoolantTempCelsius() { return coolantTempCelsius; }
    public void setCoolantTempCelsius(double coolantTempCelsius) { this.coolantTempCelsius = coolantTempCelsius; }

    public boolean isCoolantAlarm() { return coolantAlarm; }
    public void setCoolantAlarm(boolean coolantAlarm) { this.coolantAlarm = coolantAlarm; }

    public String getLaunchStatus() { return launchStatus; }
    public void setLaunchStatus(String launchStatus) { this.launchStatus = launchStatus; }

    @Override
    public String toString() {
        return "{" +
                "\"cellsOccupied\":"      + cellsOccupied      + "," +
                "\"cellsFired\":"         + cellsFired         + "," +
                "\"coolantTempCelsius\":" + coolantTempCelsius + "," +
                "\"coolantAlarm\":"       + coolantAlarm       + "," +
                "\"launchStatus\":"       + (launchStatus != null ? "\"" + launchStatus + "\"" : "null") +
                "}";
    }
}
