package com.simulator.drone.model.ship;

public class FireFightingDetail {

    private boolean pump1Active;
    private boolean pump2Active;
    /** 0–100% */
    private double  foamAgentPercent;
    /** 7.5–11 bar */
    private double  systemPressureBar;
    /** 0–10 */
    private int     zonesIsolated;
    private boolean fixedSystemArmed;

    public FireFightingDetail() {}

    public FireFightingDetail(boolean pump1Active, boolean pump2Active,
                              double foamAgentPercent, double systemPressureBar,
                              int zonesIsolated, boolean fixedSystemArmed) {
        this.pump1Active       = pump1Active;
        this.pump2Active       = pump2Active;
        this.foamAgentPercent  = foamAgentPercent;
        this.systemPressureBar = systemPressureBar;
        this.zonesIsolated     = zonesIsolated;
        this.fixedSystemArmed  = fixedSystemArmed;
    }

    public boolean isPump1Active() { return pump1Active; }
    public void setPump1Active(boolean pump1Active) { this.pump1Active = pump1Active; }

    public boolean isPump2Active() { return pump2Active; }
    public void setPump2Active(boolean pump2Active) { this.pump2Active = pump2Active; }

    public double getFoamAgentPercent() { return foamAgentPercent; }
    public void setFoamAgentPercent(double foamAgentPercent) { this.foamAgentPercent = foamAgentPercent; }

    public double getSystemPressureBar() { return systemPressureBar; }
    public void setSystemPressureBar(double systemPressureBar) { this.systemPressureBar = systemPressureBar; }

    public int getZonesIsolated() { return zonesIsolated; }
    public void setZonesIsolated(int zonesIsolated) { this.zonesIsolated = zonesIsolated; }

    public boolean isFixedSystemArmed() { return fixedSystemArmed; }
    public void setFixedSystemArmed(boolean fixedSystemArmed) { this.fixedSystemArmed = fixedSystemArmed; }

    @Override
    public String toString() {
        return "{" +
                "\"pump1Active\":"       + pump1Active       + "," +
                "\"pump2Active\":"       + pump2Active       + "," +
                "\"foamAgentPercent\":"  + foamAgentPercent  + "," +
                "\"systemPressureBar\":" + systemPressureBar + "," +
                "\"zonesIsolated\":"     + zonesIsolated     + "," +
                "\"fixedSystemArmed\":"  + fixedSystemArmed  +
                "}";
    }
}
