package com.simulator.drone.model.ship;

public class ElectricalBusDetail {

    /** ~254 V (440 V 3-phase line-to-neutral) */
    private double phase1VoltageVolts;
    private double phase2VoltageVolts;
    private double phase3VoltageVolts;
    /** 85–100% */
    private double powerFactorPercent;
    /** 0–5% */
    private double harmonicDistortionPercent;
    /** count of tripped breakers */
    private int    activeBreakerTrips;

    public ElectricalBusDetail() {}

    public ElectricalBusDetail(double phase1VoltageVolts, double phase2VoltageVolts,
                               double phase3VoltageVolts, double powerFactorPercent,
                               double harmonicDistortionPercent, int activeBreakerTrips) {
        this.phase1VoltageVolts         = phase1VoltageVolts;
        this.phase2VoltageVolts         = phase2VoltageVolts;
        this.phase3VoltageVolts         = phase3VoltageVolts;
        this.powerFactorPercent         = powerFactorPercent;
        this.harmonicDistortionPercent  = harmonicDistortionPercent;
        this.activeBreakerTrips         = activeBreakerTrips;
    }

    public double getPhase1VoltageVolts() { return phase1VoltageVolts; }
    public void setPhase1VoltageVolts(double phase1VoltageVolts) { this.phase1VoltageVolts = phase1VoltageVolts; }

    public double getPhase2VoltageVolts() { return phase2VoltageVolts; }
    public void setPhase2VoltageVolts(double phase2VoltageVolts) { this.phase2VoltageVolts = phase2VoltageVolts; }

    public double getPhase3VoltageVolts() { return phase3VoltageVolts; }
    public void setPhase3VoltageVolts(double phase3VoltageVolts) { this.phase3VoltageVolts = phase3VoltageVolts; }

    public double getPowerFactorPercent() { return powerFactorPercent; }
    public void setPowerFactorPercent(double powerFactorPercent) { this.powerFactorPercent = powerFactorPercent; }

    public double getHarmonicDistortionPercent() { return harmonicDistortionPercent; }
    public void setHarmonicDistortionPercent(double harmonicDistortionPercent) { this.harmonicDistortionPercent = harmonicDistortionPercent; }

    public int getActiveBreakerTrips() { return activeBreakerTrips; }
    public void setActiveBreakerTrips(int activeBreakerTrips) { this.activeBreakerTrips = activeBreakerTrips; }

    @Override
    public String toString() {
        return "{" +
                "\"phase1VoltageVolts\":"        + phase1VoltageVolts        + "," +
                "\"phase2VoltageVolts\":"        + phase2VoltageVolts        + "," +
                "\"phase3VoltageVolts\":"        + phase3VoltageVolts        + "," +
                "\"powerFactorPercent\":"        + powerFactorPercent        + "," +
                "\"harmonicDistortionPercent\":" + harmonicDistortionPercent + "," +
                "\"activeBreakerTrips\":"        + activeBreakerTrips        +
                "}";
    }
}
