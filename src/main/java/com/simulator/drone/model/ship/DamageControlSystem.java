package com.simulator.drone.model.ship;

public class DamageControlSystem {

    /** 0-100% */
    private double hullIntegrityPercent;
    /** -25 to 25 degrees */
    private double rollDegrees;
    /** -10 to 10 degrees */
    private double pitchDegrees;
    /** -5 to 5 degrees */
    private double heelDegrees;
    /** 0-5 */
    private int fireZonesActive;
    /** 0-10 */
    private int floodZonesActive;
    /** "NONE", "ALPHA", "BRAVO", "CHARLIE" */
    private String nbcAlertLevel;
    /** 0-100 L/min */
    private double waterIngressLpm;

    // Component detail objects
    private HullZoneDetail       hullZoneDetail;
    private FireDetectionDetail  fireDetectionDetail;
    private FloodDetectionDetail floodDetectionDetail;
    private StabilityDetail      stabilityDetail;

    public DamageControlSystem() {}

    public DamageControlSystem(double hullIntegrityPercent, double rollDegrees,
                                double pitchDegrees, double heelDegrees,
                                int fireZonesActive, int floodZonesActive,
                                String nbcAlertLevel, double waterIngressLpm) {
        this.hullIntegrityPercent = hullIntegrityPercent;
        this.rollDegrees          = rollDegrees;
        this.pitchDegrees         = pitchDegrees;
        this.heelDegrees          = heelDegrees;
        this.fireZonesActive      = fireZonesActive;
        this.floodZonesActive     = floodZonesActive;
        this.nbcAlertLevel        = nbcAlertLevel;
        this.waterIngressLpm      = waterIngressLpm;
    }

    public double getHullIntegrityPercent() { return hullIntegrityPercent; }
    public void setHullIntegrityPercent(double hullIntegrityPercent) { this.hullIntegrityPercent = hullIntegrityPercent; }

    public double getRollDegrees() { return rollDegrees; }
    public void setRollDegrees(double rollDegrees) { this.rollDegrees = rollDegrees; }

    public double getPitchDegrees() { return pitchDegrees; }
    public void setPitchDegrees(double pitchDegrees) { this.pitchDegrees = pitchDegrees; }

    public double getHeelDegrees() { return heelDegrees; }
    public void setHeelDegrees(double heelDegrees) { this.heelDegrees = heelDegrees; }

    public int getFireZonesActive() { return fireZonesActive; }
    public void setFireZonesActive(int fireZonesActive) { this.fireZonesActive = fireZonesActive; }

    public int getFloodZonesActive() { return floodZonesActive; }
    public void setFloodZonesActive(int floodZonesActive) { this.floodZonesActive = floodZonesActive; }

    public String getNbcAlertLevel() { return nbcAlertLevel; }
    public void setNbcAlertLevel(String nbcAlertLevel) { this.nbcAlertLevel = nbcAlertLevel; }

    public double getWaterIngressLpm() { return waterIngressLpm; }
    public void setWaterIngressLpm(double waterIngressLpm) { this.waterIngressLpm = waterIngressLpm; }

    public HullZoneDetail getHullZoneDetail() { return hullZoneDetail; }
    public void setHullZoneDetail(HullZoneDetail hullZoneDetail) { this.hullZoneDetail = hullZoneDetail; }

    public FireDetectionDetail getFireDetectionDetail() { return fireDetectionDetail; }
    public void setFireDetectionDetail(FireDetectionDetail fireDetectionDetail) { this.fireDetectionDetail = fireDetectionDetail; }

    public FloodDetectionDetail getFloodDetectionDetail() { return floodDetectionDetail; }
    public void setFloodDetectionDetail(FloodDetectionDetail floodDetectionDetail) { this.floodDetectionDetail = floodDetectionDetail; }

    public StabilityDetail getStabilityDetail() { return stabilityDetail; }
    public void setStabilityDetail(StabilityDetail stabilityDetail) { this.stabilityDetail = stabilityDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"hullIntegrityPercent\":" + hullIntegrityPercent + "," +
                "\"rollDegrees\":"          + rollDegrees          + "," +
                "\"pitchDegrees\":"         + pitchDegrees         + "," +
                "\"heelDegrees\":"          + heelDegrees          + "," +
                "\"fireZonesActive\":"      + fireZonesActive      + "," +
                "\"floodZonesActive\":"     + floodZonesActive     + "," +
                "\"nbcAlertLevel\":"        + (nbcAlertLevel != null ? "\"" + nbcAlertLevel + "\"" : "null") + "," +
                "\"waterIngressLpm\":"      + waterIngressLpm      + "," +
                "\"hullZoneDetail\":"       + (hullZoneDetail       != null ? hullZoneDetail.toString()       : "null") + "," +
                "\"fireDetectionDetail\":"  + (fireDetectionDetail  != null ? fireDetectionDetail.toString()  : "null") + "," +
                "\"floodDetectionDetail\":" + (floodDetectionDetail != null ? floodDetectionDetail.toString() : "null") + "," +
                "\"stabilityDetail\":"      + (stabilityDetail      != null ? stabilityDetail.toString()      : "null") +
                "}";
    }
}
