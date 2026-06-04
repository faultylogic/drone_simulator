package com.simulator.drone.model.ship;

public class PropulsionSystem {

    /** LM2500 #1 output 0-100% */
    private double gasTurbine1Percent;
    /** LM2500 #2 output 0-100% */
    private double gasTurbine2Percent;
    /** SEMT Pielstick output 0-100% */
    private double dieselOutputPercent;
    /** 0-350 RPM */
    private double portShaftRpm;
    /** 0-350 RPM */
    private double stbdShaftRpm;
    /** 60-110°C normal */
    private double gearboxTempCelsius;
    /** litres per hour */
    private double fuelConsumptionLph;
    /** any alarm active */
    private boolean engineCasualty;

    // Component detail objects
    private GasTurbineDetail  gasTurbine1Detail;
    private GasTurbineDetail  gasTurbine2Detail;
    private DieselEngineDetail dieselDetail;
    private ShaftDetail       portShaftDetail;
    private ShaftDetail       stbdShaftDetail;

    public PropulsionSystem() {}

    public PropulsionSystem(double gasTurbine1Percent, double gasTurbine2Percent,
                            double dieselOutputPercent, double portShaftRpm,
                            double stbdShaftRpm, double gearboxTempCelsius,
                            double fuelConsumptionLph, boolean engineCasualty) {
        this.gasTurbine1Percent  = gasTurbine1Percent;
        this.gasTurbine2Percent  = gasTurbine2Percent;
        this.dieselOutputPercent = dieselOutputPercent;
        this.portShaftRpm        = portShaftRpm;
        this.stbdShaftRpm        = stbdShaftRpm;
        this.gearboxTempCelsius  = gearboxTempCelsius;
        this.fuelConsumptionLph  = fuelConsumptionLph;
        this.engineCasualty      = engineCasualty;
    }

    public double getGasTurbine1Percent() { return gasTurbine1Percent; }
    public void setGasTurbine1Percent(double gasTurbine1Percent) { this.gasTurbine1Percent = gasTurbine1Percent; }

    public double getGasTurbine2Percent() { return gasTurbine2Percent; }
    public void setGasTurbine2Percent(double gasTurbine2Percent) { this.gasTurbine2Percent = gasTurbine2Percent; }

    public double getDieselOutputPercent() { return dieselOutputPercent; }
    public void setDieselOutputPercent(double dieselOutputPercent) { this.dieselOutputPercent = dieselOutputPercent; }

    public double getPortShaftRpm() { return portShaftRpm; }
    public void setPortShaftRpm(double portShaftRpm) { this.portShaftRpm = portShaftRpm; }

    public double getStbdShaftRpm() { return stbdShaftRpm; }
    public void setStbdShaftRpm(double stbdShaftRpm) { this.stbdShaftRpm = stbdShaftRpm; }

    public double getGearboxTempCelsius() { return gearboxTempCelsius; }
    public void setGearboxTempCelsius(double gearboxTempCelsius) { this.gearboxTempCelsius = gearboxTempCelsius; }

    public double getFuelConsumptionLph() { return fuelConsumptionLph; }
    public void setFuelConsumptionLph(double fuelConsumptionLph) { this.fuelConsumptionLph = fuelConsumptionLph; }

    public boolean isEngineCasualty() { return engineCasualty; }
    public void setEngineCasualty(boolean engineCasualty) { this.engineCasualty = engineCasualty; }

    public GasTurbineDetail getGasTurbine1Detail() { return gasTurbine1Detail; }
    public void setGasTurbine1Detail(GasTurbineDetail gasTurbine1Detail) { this.gasTurbine1Detail = gasTurbine1Detail; }

    public GasTurbineDetail getGasTurbine2Detail() { return gasTurbine2Detail; }
    public void setGasTurbine2Detail(GasTurbineDetail gasTurbine2Detail) { this.gasTurbine2Detail = gasTurbine2Detail; }

    public DieselEngineDetail getDieselDetail() { return dieselDetail; }
    public void setDieselDetail(DieselEngineDetail dieselDetail) { this.dieselDetail = dieselDetail; }

    public ShaftDetail getPortShaftDetail() { return portShaftDetail; }
    public void setPortShaftDetail(ShaftDetail portShaftDetail) { this.portShaftDetail = portShaftDetail; }

    public ShaftDetail getStbdShaftDetail() { return stbdShaftDetail; }
    public void setStbdShaftDetail(ShaftDetail stbdShaftDetail) { this.stbdShaftDetail = stbdShaftDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"gasTurbine1Percent\":"  + gasTurbine1Percent  + "," +
                "\"gasTurbine2Percent\":"  + gasTurbine2Percent  + "," +
                "\"dieselOutputPercent\":" + dieselOutputPercent + "," +
                "\"portShaftRpm\":"        + portShaftRpm        + "," +
                "\"stbdShaftRpm\":"        + stbdShaftRpm        + "," +
                "\"gearboxTempCelsius\":"  + gearboxTempCelsius  + "," +
                "\"fuelConsumptionLph\":"  + fuelConsumptionLph  + "," +
                "\"engineCasualty\":"      + engineCasualty      + "," +
                "\"gasTurbine1Detail\":"   + (gasTurbine1Detail  != null ? gasTurbine1Detail.toString()  : "null") + "," +
                "\"gasTurbine2Detail\":"   + (gasTurbine2Detail  != null ? gasTurbine2Detail.toString()  : "null") + "," +
                "\"dieselDetail\":"        + (dieselDetail       != null ? dieselDetail.toString()       : "null") + "," +
                "\"portShaftDetail\":"     + (portShaftDetail    != null ? portShaftDetail.toString()    : "null") + "," +
                "\"stbdShaftDetail\":"     + (stbdShaftDetail    != null ? stbdShaftDetail.toString()    : "null") +
                "}";
    }
}
