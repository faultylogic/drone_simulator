package com.simulator.drone.model.ship;

public class SeawaterCoolingDetail {

    private boolean pump1Active;
    private boolean pump2Active;
    /** 0–5 000 L/min */
    private double  flowRateLpm;
    /** 10–25°C (ocean temp) */
    private double  inletTempCelsius;
    /** 25–45°C */
    private double  outletTempCelsius;
    /** 0–2 bar differential pressure */
    private double  filterDpBar;

    public SeawaterCoolingDetail() {}

    public SeawaterCoolingDetail(boolean pump1Active, boolean pump2Active,
                                 double flowRateLpm, double inletTempCelsius,
                                 double outletTempCelsius, double filterDpBar) {
        this.pump1Active       = pump1Active;
        this.pump2Active       = pump2Active;
        this.flowRateLpm       = flowRateLpm;
        this.inletTempCelsius  = inletTempCelsius;
        this.outletTempCelsius = outletTempCelsius;
        this.filterDpBar       = filterDpBar;
    }

    public boolean isPump1Active() { return pump1Active; }
    public void setPump1Active(boolean pump1Active) { this.pump1Active = pump1Active; }

    public boolean isPump2Active() { return pump2Active; }
    public void setPump2Active(boolean pump2Active) { this.pump2Active = pump2Active; }

    public double getFlowRateLpm() { return flowRateLpm; }
    public void setFlowRateLpm(double flowRateLpm) { this.flowRateLpm = flowRateLpm; }

    public double getInletTempCelsius() { return inletTempCelsius; }
    public void setInletTempCelsius(double inletTempCelsius) { this.inletTempCelsius = inletTempCelsius; }

    public double getOutletTempCelsius() { return outletTempCelsius; }
    public void setOutletTempCelsius(double outletTempCelsius) { this.outletTempCelsius = outletTempCelsius; }

    public double getFilterDpBar() { return filterDpBar; }
    public void setFilterDpBar(double filterDpBar) { this.filterDpBar = filterDpBar; }

    @Override
    public String toString() {
        return "{" +
                "\"pump1Active\":"       + pump1Active       + "," +
                "\"pump2Active\":"       + pump2Active       + "," +
                "\"flowRateLpm\":"       + flowRateLpm       + "," +
                "\"inletTempCelsius\":"  + inletTempCelsius  + "," +
                "\"outletTempCelsius\":" + outletTempCelsius + "," +
                "\"filterDpBar\":"       + filterDpBar       +
                "}";
    }
}
