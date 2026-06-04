package com.simulator.drone.model.ship;

public class DieselEngineDetail {

    /** 70–95°C */
    private double coolantTempCelsius;
    /** 3–6 bar */
    private double oilPressureBar;
    /** 65–90°C */
    private double oilTempCelsius;
    /** 250–450°C */
    private double exhaustTempCelsius;
    /** 1.5–3.5 bar */
    private double turbochargerBoostBar;
    /** 0–600 RPM */
    private double rpmActual;

    public DieselEngineDetail() {}

    public DieselEngineDetail(double coolantTempCelsius, double oilPressureBar,
                              double oilTempCelsius, double exhaustTempCelsius,
                              double turbochargerBoostBar, double rpmActual) {
        this.coolantTempCelsius    = coolantTempCelsius;
        this.oilPressureBar        = oilPressureBar;
        this.oilTempCelsius        = oilTempCelsius;
        this.exhaustTempCelsius    = exhaustTempCelsius;
        this.turbochargerBoostBar  = turbochargerBoostBar;
        this.rpmActual             = rpmActual;
    }

    public double getCoolantTempCelsius() { return coolantTempCelsius; }
    public void setCoolantTempCelsius(double coolantTempCelsius) { this.coolantTempCelsius = coolantTempCelsius; }

    public double getOilPressureBar() { return oilPressureBar; }
    public void setOilPressureBar(double oilPressureBar) { this.oilPressureBar = oilPressureBar; }

    public double getOilTempCelsius() { return oilTempCelsius; }
    public void setOilTempCelsius(double oilTempCelsius) { this.oilTempCelsius = oilTempCelsius; }

    public double getExhaustTempCelsius() { return exhaustTempCelsius; }
    public void setExhaustTempCelsius(double exhaustTempCelsius) { this.exhaustTempCelsius = exhaustTempCelsius; }

    public double getTurbochargerBoostBar() { return turbochargerBoostBar; }
    public void setTurbochargerBoostBar(double turbochargerBoostBar) { this.turbochargerBoostBar = turbochargerBoostBar; }

    public double getRpmActual() { return rpmActual; }
    public void setRpmActual(double rpmActual) { this.rpmActual = rpmActual; }

    @Override
    public String toString() {
        return "{" +
                "\"coolantTempCelsius\":"   + coolantTempCelsius   + "," +
                "\"oilPressureBar\":"       + oilPressureBar       + "," +
                "\"oilTempCelsius\":"       + oilTempCelsius       + "," +
                "\"exhaustTempCelsius\":"   + exhaustTempCelsius   + "," +
                "\"turbochargerBoostBar\":" + turbochargerBoostBar + "," +
                "\"rpmActual\":"            + rpmActual            +
                "}";
    }
}
