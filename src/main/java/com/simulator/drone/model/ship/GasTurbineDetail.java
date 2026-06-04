package com.simulator.drone.model.ship;

public class GasTurbineDetail {

    /** 15–40°C */
    private double inletTempCelsius;
    /** 400–550°C at load */
    private double exhaustTempCelsius;
    /** 3 000–9 500 RPM */
    private double compressorSpeedRpm;
    /** 3–7 bar */
    private double oilPressureBar;
    /** 60–95°C */
    private double oilTempCelsius;
    /** 0.5–5 mm/s */
    private double vibrationMms;

    public GasTurbineDetail() {}

    public GasTurbineDetail(double inletTempCelsius, double exhaustTempCelsius,
                            double compressorSpeedRpm, double oilPressureBar,
                            double oilTempCelsius, double vibrationMms) {
        this.inletTempCelsius    = inletTempCelsius;
        this.exhaustTempCelsius  = exhaustTempCelsius;
        this.compressorSpeedRpm  = compressorSpeedRpm;
        this.oilPressureBar      = oilPressureBar;
        this.oilTempCelsius      = oilTempCelsius;
        this.vibrationMms        = vibrationMms;
    }

    public double getInletTempCelsius() { return inletTempCelsius; }
    public void setInletTempCelsius(double inletTempCelsius) { this.inletTempCelsius = inletTempCelsius; }

    public double getExhaustTempCelsius() { return exhaustTempCelsius; }
    public void setExhaustTempCelsius(double exhaustTempCelsius) { this.exhaustTempCelsius = exhaustTempCelsius; }

    public double getCompressorSpeedRpm() { return compressorSpeedRpm; }
    public void setCompressorSpeedRpm(double compressorSpeedRpm) { this.compressorSpeedRpm = compressorSpeedRpm; }

    public double getOilPressureBar() { return oilPressureBar; }
    public void setOilPressureBar(double oilPressureBar) { this.oilPressureBar = oilPressureBar; }

    public double getOilTempCelsius() { return oilTempCelsius; }
    public void setOilTempCelsius(double oilTempCelsius) { this.oilTempCelsius = oilTempCelsius; }

    public double getVibrationMms() { return vibrationMms; }
    public void setVibrationMms(double vibrationMms) { this.vibrationMms = vibrationMms; }

    @Override
    public String toString() {
        return "{" +
                "\"inletTempCelsius\":"   + inletTempCelsius   + "," +
                "\"exhaustTempCelsius\":" + exhaustTempCelsius + "," +
                "\"compressorSpeedRpm\":" + compressorSpeedRpm + "," +
                "\"oilPressureBar\":"     + oilPressureBar     + "," +
                "\"oilTempCelsius\":"     + oilTempCelsius     + "," +
                "\"vibrationMms\":"       + vibrationMms       +
                "}";
    }
}
