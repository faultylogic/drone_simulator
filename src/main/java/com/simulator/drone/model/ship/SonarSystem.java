package com.simulator.drone.model.ship;

public class SonarSystem {

    /** AN/SQS-510 */
    private boolean hullSonarOperational;
    /** AN/SQR-501 CANTASS */
    private boolean cantassTowedArrayDeployed;
    /** 0-600 m */
    private double towedArrayCableOutMeters;
    /** 0-20 */
    private int activeContactsTracked;
    /** "ACTIVE", "PASSIVE", or "SILENT" */
    private String operatingMode;
    /** 5-50 km */
    private double detectionRangeKm;
    /** 30-80 dB */
    private double ambientNoiseDb;

    // Component detail objects
    private HullSonarDetail  hullSonarDetail;
    private TowedArrayDetail towedArrayDetail;

    public SonarSystem() {}

    public SonarSystem(boolean hullSonarOperational, boolean cantassTowedArrayDeployed,
                       double towedArrayCableOutMeters, int activeContactsTracked,
                       String operatingMode, double detectionRangeKm, double ambientNoiseDb) {
        this.hullSonarOperational       = hullSonarOperational;
        this.cantassTowedArrayDeployed  = cantassTowedArrayDeployed;
        this.towedArrayCableOutMeters   = towedArrayCableOutMeters;
        this.activeContactsTracked      = activeContactsTracked;
        this.operatingMode              = operatingMode;
        this.detectionRangeKm           = detectionRangeKm;
        this.ambientNoiseDb             = ambientNoiseDb;
    }

    public boolean isHullSonarOperational() { return hullSonarOperational; }
    public void setHullSonarOperational(boolean hullSonarOperational) { this.hullSonarOperational = hullSonarOperational; }

    public boolean isCantassTowedArrayDeployed() { return cantassTowedArrayDeployed; }
    public void setCantassTowedArrayDeployed(boolean cantassTowedArrayDeployed) { this.cantassTowedArrayDeployed = cantassTowedArrayDeployed; }

    public double getTowedArrayCableOutMeters() { return towedArrayCableOutMeters; }
    public void setTowedArrayCableOutMeters(double towedArrayCableOutMeters) { this.towedArrayCableOutMeters = towedArrayCableOutMeters; }

    public int getActiveContactsTracked() { return activeContactsTracked; }
    public void setActiveContactsTracked(int activeContactsTracked) { this.activeContactsTracked = activeContactsTracked; }

    public String getOperatingMode() { return operatingMode; }
    public void setOperatingMode(String operatingMode) { this.operatingMode = operatingMode; }

    public double getDetectionRangeKm() { return detectionRangeKm; }
    public void setDetectionRangeKm(double detectionRangeKm) { this.detectionRangeKm = detectionRangeKm; }

    public double getAmbientNoiseDb() { return ambientNoiseDb; }
    public void setAmbientNoiseDb(double ambientNoiseDb) { this.ambientNoiseDb = ambientNoiseDb; }

    public HullSonarDetail getHullSonarDetail() { return hullSonarDetail; }
    public void setHullSonarDetail(HullSonarDetail hullSonarDetail) { this.hullSonarDetail = hullSonarDetail; }

    public TowedArrayDetail getTowedArrayDetail() { return towedArrayDetail; }
    public void setTowedArrayDetail(TowedArrayDetail towedArrayDetail) { this.towedArrayDetail = towedArrayDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"hullSonarOperational\":"      + hullSonarOperational      + "," +
                "\"cantassTowedArrayDeployed\":" + cantassTowedArrayDeployed + "," +
                "\"towedArrayCableOutMeters\":"  + towedArrayCableOutMeters  + "," +
                "\"activeContactsTracked\":"     + activeContactsTracked     + "," +
                "\"operatingMode\":"             + (operatingMode != null ? "\"" + operatingMode + "\"" : "null") + "," +
                "\"detectionRangeKm\":"          + detectionRangeKm          + "," +
                "\"ambientNoiseDb\":"            + ambientNoiseDb            + "," +
                "\"hullSonarDetail\":"           + (hullSonarDetail  != null ? hullSonarDetail.toString()  : "null") + "," +
                "\"towedArrayDetail\":"          + (towedArrayDetail != null ? towedArrayDetail.toString() : "null") +
                "}";
    }
}
