package com.simulator.drone.model.ship;

public class StabilityDetail {

    /** GM 0.5–2.5 m */
    private double metacentricHeightMeters;
    /** 10–25 s */
    private double rollPeriodSeconds;
    /** ~4 770 loaded */
    private double displacementTonnes;
    /** −1 to 1 m (0 = even keel) */
    private double trimMeters;
    /** 3–5 m */
    private double draftForwardMeters;
    /** 3–5 m */
    private double draftAftMeters;

    public StabilityDetail() {}

    public StabilityDetail(double metacentricHeightMeters, double rollPeriodSeconds,
                           double displacementTonnes, double trimMeters,
                           double draftForwardMeters, double draftAftMeters) {
        this.metacentricHeightMeters = metacentricHeightMeters;
        this.rollPeriodSeconds       = rollPeriodSeconds;
        this.displacementTonnes      = displacementTonnes;
        this.trimMeters              = trimMeters;
        this.draftForwardMeters      = draftForwardMeters;
        this.draftAftMeters          = draftAftMeters;
    }

    public double getMetacentricHeightMeters() { return metacentricHeightMeters; }
    public void setMetacentricHeightMeters(double metacentricHeightMeters) { this.metacentricHeightMeters = metacentricHeightMeters; }

    public double getRollPeriodSeconds() { return rollPeriodSeconds; }
    public void setRollPeriodSeconds(double rollPeriodSeconds) { this.rollPeriodSeconds = rollPeriodSeconds; }

    public double getDisplacementTonnes() { return displacementTonnes; }
    public void setDisplacementTonnes(double displacementTonnes) { this.displacementTonnes = displacementTonnes; }

    public double getTrimMeters() { return trimMeters; }
    public void setTrimMeters(double trimMeters) { this.trimMeters = trimMeters; }

    public double getDraftForwardMeters() { return draftForwardMeters; }
    public void setDraftForwardMeters(double draftForwardMeters) { this.draftForwardMeters = draftForwardMeters; }

    public double getDraftAftMeters() { return draftAftMeters; }
    public void setDraftAftMeters(double draftAftMeters) { this.draftAftMeters = draftAftMeters; }

    @Override
    public String toString() {
        return "{" +
                "\"metacentricHeightMeters\":" + metacentricHeightMeters + "," +
                "\"rollPeriodSeconds\":"       + rollPeriodSeconds       + "," +
                "\"displacementTonnes\":"      + displacementTonnes      + "," +
                "\"trimMeters\":"              + trimMeters              + "," +
                "\"draftForwardMeters\":"      + draftForwardMeters      + "," +
                "\"draftAftMeters\":"          + draftAftMeters          +
                "}";
    }
}
