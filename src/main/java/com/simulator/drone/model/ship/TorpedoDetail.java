package com.simulator.drone.model.ship;

public class TorpedoDetail {

    /** 0–6 */
    private int    tubesArmed;
    /** 0–100% */
    private double guidanceSystemPercent;
    private boolean safetyInterlockActive;
    /** running count */
    private int    torpedoesFired;

    public TorpedoDetail() {}

    public TorpedoDetail(int tubesArmed, double guidanceSystemPercent,
                         boolean safetyInterlockActive, int torpedoesFired) {
        this.tubesArmed             = tubesArmed;
        this.guidanceSystemPercent  = guidanceSystemPercent;
        this.safetyInterlockActive  = safetyInterlockActive;
        this.torpedoesFired         = torpedoesFired;
    }

    public int getTubesArmed() { return tubesArmed; }
    public void setTubesArmed(int tubesArmed) { this.tubesArmed = tubesArmed; }

    public double getGuidanceSystemPercent() { return guidanceSystemPercent; }
    public void setGuidanceSystemPercent(double guidanceSystemPercent) { this.guidanceSystemPercent = guidanceSystemPercent; }

    public boolean isSafetyInterlockActive() { return safetyInterlockActive; }
    public void setSafetyInterlockActive(boolean safetyInterlockActive) { this.safetyInterlockActive = safetyInterlockActive; }

    public int getTorpedoesFired() { return torpedoesFired; }
    public void setTorpedoesFired(int torpedoesFired) { this.torpedoesFired = torpedoesFired; }

    @Override
    public String toString() {
        return "{" +
                "\"tubesArmed\":"            + tubesArmed            + "," +
                "\"guidanceSystemPercent\":" + guidanceSystemPercent + "," +
                "\"safetyInterlockActive\":" + safetyInterlockActive + "," +
                "\"torpedoesFired\":"        + torpedoesFired        +
                "}";
    }
}
