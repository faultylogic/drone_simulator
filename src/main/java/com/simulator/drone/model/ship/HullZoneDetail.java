package com.simulator.drone.model.ship;

public class HullZoneDetail {

    /** bow 0–100% */
    private double zone1IntegrityPercent;
    /** forward */
    private double zone2IntegrityPercent;
    /** midships */
    private double zone3IntegrityPercent;
    /** engine room */
    private double zone4IntegrityPercent;
    /** aft */
    private double zone5IntegrityPercent;
    private boolean pressureTestPassed;

    public HullZoneDetail() {}

    public HullZoneDetail(double zone1IntegrityPercent, double zone2IntegrityPercent,
                          double zone3IntegrityPercent, double zone4IntegrityPercent,
                          double zone5IntegrityPercent, boolean pressureTestPassed) {
        this.zone1IntegrityPercent = zone1IntegrityPercent;
        this.zone2IntegrityPercent = zone2IntegrityPercent;
        this.zone3IntegrityPercent = zone3IntegrityPercent;
        this.zone4IntegrityPercent = zone4IntegrityPercent;
        this.zone5IntegrityPercent = zone5IntegrityPercent;
        this.pressureTestPassed    = pressureTestPassed;
    }

    public double getZone1IntegrityPercent() { return zone1IntegrityPercent; }
    public void setZone1IntegrityPercent(double zone1IntegrityPercent) { this.zone1IntegrityPercent = zone1IntegrityPercent; }

    public double getZone2IntegrityPercent() { return zone2IntegrityPercent; }
    public void setZone2IntegrityPercent(double zone2IntegrityPercent) { this.zone2IntegrityPercent = zone2IntegrityPercent; }

    public double getZone3IntegrityPercent() { return zone3IntegrityPercent; }
    public void setZone3IntegrityPercent(double zone3IntegrityPercent) { this.zone3IntegrityPercent = zone3IntegrityPercent; }

    public double getZone4IntegrityPercent() { return zone4IntegrityPercent; }
    public void setZone4IntegrityPercent(double zone4IntegrityPercent) { this.zone4IntegrityPercent = zone4IntegrityPercent; }

    public double getZone5IntegrityPercent() { return zone5IntegrityPercent; }
    public void setZone5IntegrityPercent(double zone5IntegrityPercent) { this.zone5IntegrityPercent = zone5IntegrityPercent; }

    public boolean isPressureTestPassed() { return pressureTestPassed; }
    public void setPressureTestPassed(boolean pressureTestPassed) { this.pressureTestPassed = pressureTestPassed; }

    @Override
    public String toString() {
        return "{" +
                "\"zone1IntegrityPercent\":" + zone1IntegrityPercent + "," +
                "\"zone2IntegrityPercent\":" + zone2IntegrityPercent + "," +
                "\"zone3IntegrityPercent\":" + zone3IntegrityPercent + "," +
                "\"zone4IntegrityPercent\":" + zone4IntegrityPercent + "," +
                "\"zone5IntegrityPercent\":" + zone5IntegrityPercent + "," +
                "\"pressureTestPassed\":"    + pressureTestPassed    +
                "}";
    }
}
