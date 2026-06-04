package com.simulator.drone.model.ship;

public class GeneratorDetail {

    /** 440 ±5 V */
    private double outputVoltageVolts;
    /** 0–100% */
    private double loadPercent;
    /** 60–120°C */
    private double windingTempCelsius;
    /** 40–80°C */
    private double bearingTempCelsius;
    /** 0.1–3 mm/s */
    private double vibrationMms;
    /** "ONLINE", "STANDBY", "OFFLINE" */
    private String operationalStatus;

    public GeneratorDetail() {}

    public GeneratorDetail(double outputVoltageVolts, double loadPercent,
                           double windingTempCelsius, double bearingTempCelsius,
                           double vibrationMms, String operationalStatus) {
        this.outputVoltageVolts = outputVoltageVolts;
        this.loadPercent        = loadPercent;
        this.windingTempCelsius = windingTempCelsius;
        this.bearingTempCelsius = bearingTempCelsius;
        this.vibrationMms       = vibrationMms;
        this.operationalStatus  = operationalStatus;
    }

    public double getOutputVoltageVolts() { return outputVoltageVolts; }
    public void setOutputVoltageVolts(double outputVoltageVolts) { this.outputVoltageVolts = outputVoltageVolts; }

    public double getLoadPercent() { return loadPercent; }
    public void setLoadPercent(double loadPercent) { this.loadPercent = loadPercent; }

    public double getWindingTempCelsius() { return windingTempCelsius; }
    public void setWindingTempCelsius(double windingTempCelsius) { this.windingTempCelsius = windingTempCelsius; }

    public double getBearingTempCelsius() { return bearingTempCelsius; }
    public void setBearingTempCelsius(double bearingTempCelsius) { this.bearingTempCelsius = bearingTempCelsius; }

    public double getVibrationMms() { return vibrationMms; }
    public void setVibrationMms(double vibrationMms) { this.vibrationMms = vibrationMms; }

    public String getOperationalStatus() { return operationalStatus; }
    public void setOperationalStatus(String operationalStatus) { this.operationalStatus = operationalStatus; }

    @Override
    public String toString() {
        return "{" +
                "\"outputVoltageVolts\":" + outputVoltageVolts + "," +
                "\"loadPercent\":"        + loadPercent        + "," +
                "\"windingTempCelsius\":" + windingTempCelsius + "," +
                "\"bearingTempCelsius\":" + bearingTempCelsius + "," +
                "\"vibrationMms\":"       + vibrationMms       + "," +
                "\"operationalStatus\":"  + (operationalStatus != null ? "\"" + operationalStatus + "\"" : "null") +
                "}";
    }
}
