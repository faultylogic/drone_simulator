package com.simulator.drone.model.ship;

public class BilgeDetail {

    private boolean pump1Active;
    private boolean pump2Active;
    /** 0–30 cm */
    private double  forwardBilgeCm;
    private double  midshipsBilgeCm;
    private double  aftBilgeCm;
    private double  engineRoomBilgeCm;

    public BilgeDetail() {}

    public BilgeDetail(boolean pump1Active, boolean pump2Active,
                       double forwardBilgeCm, double midshipsBilgeCm,
                       double aftBilgeCm, double engineRoomBilgeCm) {
        this.pump1Active       = pump1Active;
        this.pump2Active       = pump2Active;
        this.forwardBilgeCm    = forwardBilgeCm;
        this.midshipsBilgeCm   = midshipsBilgeCm;
        this.aftBilgeCm        = aftBilgeCm;
        this.engineRoomBilgeCm = engineRoomBilgeCm;
    }

    public boolean isPump1Active() { return pump1Active; }
    public void setPump1Active(boolean pump1Active) { this.pump1Active = pump1Active; }

    public boolean isPump2Active() { return pump2Active; }
    public void setPump2Active(boolean pump2Active) { this.pump2Active = pump2Active; }

    public double getForwardBilgeCm() { return forwardBilgeCm; }
    public void setForwardBilgeCm(double forwardBilgeCm) { this.forwardBilgeCm = forwardBilgeCm; }

    public double getMidshipsBilgeCm() { return midshipsBilgeCm; }
    public void setMidshipsBilgeCm(double midshipsBilgeCm) { this.midshipsBilgeCm = midshipsBilgeCm; }

    public double getAftBilgeCm() { return aftBilgeCm; }
    public void setAftBilgeCm(double aftBilgeCm) { this.aftBilgeCm = aftBilgeCm; }

    public double getEngineRoomBilgeCm() { return engineRoomBilgeCm; }
    public void setEngineRoomBilgeCm(double engineRoomBilgeCm) { this.engineRoomBilgeCm = engineRoomBilgeCm; }

    @Override
    public String toString() {
        return "{" +
                "\"pump1Active\":"       + pump1Active       + "," +
                "\"pump2Active\":"       + pump2Active       + "," +
                "\"forwardBilgeCm\":"    + forwardBilgeCm    + "," +
                "\"midshipsBilgeCm\":"   + midshipsBilgeCm   + "," +
                "\"aftBilgeCm\":"        + aftBilgeCm        + "," +
                "\"engineRoomBilgeCm\":" + engineRoomBilgeCm +
                "}";
    }
}
