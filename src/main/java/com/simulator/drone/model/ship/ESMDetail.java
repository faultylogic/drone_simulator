package com.simulator.drone.model.ship;

public class ESMDetail {

    /** e.g. "I/J/K" */
    private String frequencyBandCoverage;
    /** −100 to −20 dBm */
    private double signalStrengthDbm;
    /** 0–10 */
    private int    emittersDetected;
    private boolean jammerActive;

    public ESMDetail() {}

    public ESMDetail(String frequencyBandCoverage, double signalStrengthDbm,
                     int emittersDetected, boolean jammerActive) {
        this.frequencyBandCoverage = frequencyBandCoverage;
        this.signalStrengthDbm     = signalStrengthDbm;
        this.emittersDetected      = emittersDetected;
        this.jammerActive          = jammerActive;
    }

    public String getFrequencyBandCoverage() { return frequencyBandCoverage; }
    public void setFrequencyBandCoverage(String frequencyBandCoverage) { this.frequencyBandCoverage = frequencyBandCoverage; }

    public double getSignalStrengthDbm() { return signalStrengthDbm; }
    public void setSignalStrengthDbm(double signalStrengthDbm) { this.signalStrengthDbm = signalStrengthDbm; }

    public int getEmittersDetected() { return emittersDetected; }
    public void setEmittersDetected(int emittersDetected) { this.emittersDetected = emittersDetected; }

    public boolean isJammerActive() { return jammerActive; }
    public void setJammerActive(boolean jammerActive) { this.jammerActive = jammerActive; }

    @Override
    public String toString() {
        return "{" +
                "\"frequencyBandCoverage\":" + (frequencyBandCoverage != null ? "\"" + frequencyBandCoverage + "\"" : "null") + "," +
                "\"signalStrengthDbm\":"     + signalStrengthDbm     + "," +
                "\"emittersDetected\":"      + emittersDetected      + "," +
                "\"jammerActive\":"          + jammerActive          +
                "}";
    }
}
