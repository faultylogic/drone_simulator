package com.simulator.drone.model.ship;

public class RadarSystem {

    /** AN/SPS-49(V)5 */
    private boolean airSearchOperational;
    /** AN/SPS-503 Sea Giraffe */
    private boolean surfaceSearchOperational;
    /** AN/SPG-503 STIR */
    private boolean fireControlOperational;
    /** 0-50 */
    private int airTracksDetected;
    /** 0-20 */
    private int surfaceTracksDetected;
    private boolean esmAlertActive;
    /** empty or e.g. "I/J" */
    private String esmBandDetected;

    // Component detail objects
    private AirSearchDetail     airSearchDetail;
    private SurfaceSearchDetail surfaceSearchDetail;
    private FireControlDetail   fireControlDetail;
    private ESMDetail           esmDetail;

    public RadarSystem() {}

    public RadarSystem(boolean airSearchOperational, boolean surfaceSearchOperational,
                       boolean fireControlOperational, int airTracksDetected,
                       int surfaceTracksDetected, boolean esmAlertActive,
                       String esmBandDetected) {
        this.airSearchOperational     = airSearchOperational;
        this.surfaceSearchOperational = surfaceSearchOperational;
        this.fireControlOperational   = fireControlOperational;
        this.airTracksDetected        = airTracksDetected;
        this.surfaceTracksDetected    = surfaceTracksDetected;
        this.esmAlertActive           = esmAlertActive;
        this.esmBandDetected          = esmBandDetected;
    }

    public boolean isAirSearchOperational() { return airSearchOperational; }
    public void setAirSearchOperational(boolean airSearchOperational) { this.airSearchOperational = airSearchOperational; }

    public boolean isSurfaceSearchOperational() { return surfaceSearchOperational; }
    public void setSurfaceSearchOperational(boolean surfaceSearchOperational) { this.surfaceSearchOperational = surfaceSearchOperational; }

    public boolean isFireControlOperational() { return fireControlOperational; }
    public void setFireControlOperational(boolean fireControlOperational) { this.fireControlOperational = fireControlOperational; }

    public int getAirTracksDetected() { return airTracksDetected; }
    public void setAirTracksDetected(int airTracksDetected) { this.airTracksDetected = airTracksDetected; }

    public int getSurfaceTracksDetected() { return surfaceTracksDetected; }
    public void setSurfaceTracksDetected(int surfaceTracksDetected) { this.surfaceTracksDetected = surfaceTracksDetected; }

    public boolean isEsmAlertActive() { return esmAlertActive; }
    public void setEsmAlertActive(boolean esmAlertActive) { this.esmAlertActive = esmAlertActive; }

    public String getEsmBandDetected() { return esmBandDetected; }
    public void setEsmBandDetected(String esmBandDetected) { this.esmBandDetected = esmBandDetected; }

    public AirSearchDetail getAirSearchDetail() { return airSearchDetail; }
    public void setAirSearchDetail(AirSearchDetail airSearchDetail) { this.airSearchDetail = airSearchDetail; }

    public SurfaceSearchDetail getSurfaceSearchDetail() { return surfaceSearchDetail; }
    public void setSurfaceSearchDetail(SurfaceSearchDetail surfaceSearchDetail) { this.surfaceSearchDetail = surfaceSearchDetail; }

    public FireControlDetail getFireControlDetail() { return fireControlDetail; }
    public void setFireControlDetail(FireControlDetail fireControlDetail) { this.fireControlDetail = fireControlDetail; }

    public ESMDetail getEsmDetail() { return esmDetail; }
    public void setEsmDetail(ESMDetail esmDetail) { this.esmDetail = esmDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"airSearchOperational\":"     + airSearchOperational     + "," +
                "\"surfaceSearchOperational\":" + surfaceSearchOperational + "," +
                "\"fireControlOperational\":"   + fireControlOperational   + "," +
                "\"airTracksDetected\":"        + airTracksDetected        + "," +
                "\"surfaceTracksDetected\":"    + surfaceTracksDetected    + "," +
                "\"esmAlertActive\":"           + esmAlertActive           + "," +
                "\"esmBandDetected\":"          + (esmBandDetected != null ? "\"" + esmBandDetected + "\"" : "null") + "," +
                "\"airSearchDetail\":"          + (airSearchDetail     != null ? airSearchDetail.toString()     : "null") + "," +
                "\"surfaceSearchDetail\":"      + (surfaceSearchDetail != null ? surfaceSearchDetail.toString() : "null") + "," +
                "\"fireControlDetail\":"        + (fireControlDetail   != null ? fireControlDetail.toString()   : "null") + "," +
                "\"esmDetail\":"                + (esmDetail           != null ? esmDetail.toString()           : "null") +
                "}";
    }
}
