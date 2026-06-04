package com.simulator.drone.model.ship;

public class PowerSystem {

    /** Allison 501-K34 generator #1, 0-1000 kW */
    private double gen1Kw;
    /** Allison 501-K34 generator #2, 0-1000 kW */
    private double gen2Kw;
    /** Allison 501-K34 generator #3, 0-1000 kW */
    private double gen3Kw;
    /** Allison 501-K34 generator #4, 0-1000 kW */
    private double gen4Kw;
    /** 0-4000 kW */
    private double totalLoadKw;
    /** ~440 V ship bus */
    private double busVoltageVolts;
    /** ~60 Hz */
    private double frequencyHz;

    // Component detail objects
    private GeneratorDetail    gen1Detail;
    private GeneratorDetail    gen2Detail;
    private GeneratorDetail    gen3Detail;
    private GeneratorDetail    gen4Detail;
    private ElectricalBusDetail busDetail;

    public PowerSystem() {}

    public PowerSystem(double gen1Kw, double gen2Kw, double gen3Kw, double gen4Kw,
                       double totalLoadKw, double busVoltageVolts, double frequencyHz) {
        this.gen1Kw         = gen1Kw;
        this.gen2Kw         = gen2Kw;
        this.gen3Kw         = gen3Kw;
        this.gen4Kw         = gen4Kw;
        this.totalLoadKw    = totalLoadKw;
        this.busVoltageVolts = busVoltageVolts;
        this.frequencyHz    = frequencyHz;
    }

    public double getGen1Kw() { return gen1Kw; }
    public void setGen1Kw(double gen1Kw) { this.gen1Kw = gen1Kw; }

    public double getGen2Kw() { return gen2Kw; }
    public void setGen2Kw(double gen2Kw) { this.gen2Kw = gen2Kw; }

    public double getGen3Kw() { return gen3Kw; }
    public void setGen3Kw(double gen3Kw) { this.gen3Kw = gen3Kw; }

    public double getGen4Kw() { return gen4Kw; }
    public void setGen4Kw(double gen4Kw) { this.gen4Kw = gen4Kw; }

    public double getTotalLoadKw() { return totalLoadKw; }
    public void setTotalLoadKw(double totalLoadKw) { this.totalLoadKw = totalLoadKw; }

    public double getBusVoltageVolts() { return busVoltageVolts; }
    public void setBusVoltageVolts(double busVoltageVolts) { this.busVoltageVolts = busVoltageVolts; }

    public double getFrequencyHz() { return frequencyHz; }
    public void setFrequencyHz(double frequencyHz) { this.frequencyHz = frequencyHz; }

    public GeneratorDetail getGen1Detail() { return gen1Detail; }
    public void setGen1Detail(GeneratorDetail gen1Detail) { this.gen1Detail = gen1Detail; }

    public GeneratorDetail getGen2Detail() { return gen2Detail; }
    public void setGen2Detail(GeneratorDetail gen2Detail) { this.gen2Detail = gen2Detail; }

    public GeneratorDetail getGen3Detail() { return gen3Detail; }
    public void setGen3Detail(GeneratorDetail gen3Detail) { this.gen3Detail = gen3Detail; }

    public GeneratorDetail getGen4Detail() { return gen4Detail; }
    public void setGen4Detail(GeneratorDetail gen4Detail) { this.gen4Detail = gen4Detail; }

    public ElectricalBusDetail getBusDetail() { return busDetail; }
    public void setBusDetail(ElectricalBusDetail busDetail) { this.busDetail = busDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"gen1Kw\":"          + gen1Kw          + "," +
                "\"gen2Kw\":"          + gen2Kw          + "," +
                "\"gen3Kw\":"          + gen3Kw          + "," +
                "\"gen4Kw\":"          + gen4Kw          + "," +
                "\"totalLoadKw\":"     + totalLoadKw     + "," +
                "\"busVoltageVolts\":" + busVoltageVolts + "," +
                "\"frequencyHz\":"     + frequencyHz     + "," +
                "\"gen1Detail\":"      + (gen1Detail  != null ? gen1Detail.toString()  : "null") + "," +
                "\"gen2Detail\":"      + (gen2Detail  != null ? gen2Detail.toString()  : "null") + "," +
                "\"gen3Detail\":"      + (gen3Detail  != null ? gen3Detail.toString()  : "null") + "," +
                "\"gen4Detail\":"      + (gen4Detail  != null ? gen4Detail.toString()  : "null") + "," +
                "\"busDetail\":"       + (busDetail   != null ? busDetail.toString()   : "null") +
                "}";
    }
}
