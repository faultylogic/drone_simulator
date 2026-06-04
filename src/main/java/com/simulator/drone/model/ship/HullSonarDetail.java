package com.simulator.drone.model.ship;

public class HullSonarDetail {

    /** 0–48 transducer elements */
    private int    activeElements;
    /** 0–300 kW (active mode) */
    private double transmitPowerKw;
    /** 20–60 dB */
    private double selfNoiseDb;
    /** 0–20°C */
    private double waterTempCelsius;
    /** 30–36 PSU */
    private double salinityPsu;
    /** "ACTIVE_CW", "ACTIVE_SINGLE", "PASSIVE", "SILENT" */
    private String pingMode;

    public HullSonarDetail() {}

    public HullSonarDetail(int activeElements, double transmitPowerKw,
                           double selfNoiseDb, double waterTempCelsius,
                           double salinityPsu, String pingMode) {
        this.activeElements  = activeElements;
        this.transmitPowerKw = transmitPowerKw;
        this.selfNoiseDb     = selfNoiseDb;
        this.waterTempCelsius = waterTempCelsius;
        this.salinityPsu     = salinityPsu;
        this.pingMode        = pingMode;
    }

    public int getActiveElements() { return activeElements; }
    public void setActiveElements(int activeElements) { this.activeElements = activeElements; }

    public double getTransmitPowerKw() { return transmitPowerKw; }
    public void setTransmitPowerKw(double transmitPowerKw) { this.transmitPowerKw = transmitPowerKw; }

    public double getSelfNoiseDb() { return selfNoiseDb; }
    public void setSelfNoiseDb(double selfNoiseDb) { this.selfNoiseDb = selfNoiseDb; }

    public double getWaterTempCelsius() { return waterTempCelsius; }
    public void setWaterTempCelsius(double waterTempCelsius) { this.waterTempCelsius = waterTempCelsius; }

    public double getSalinityPsu() { return salinityPsu; }
    public void setSalinityPsu(double salinityPsu) { this.salinityPsu = salinityPsu; }

    public String getPingMode() { return pingMode; }
    public void setPingMode(String pingMode) { this.pingMode = pingMode; }

    @Override
    public String toString() {
        return "{" +
                "\"activeElements\":"  + activeElements  + "," +
                "\"transmitPowerKw\":" + transmitPowerKw + "," +
                "\"selfNoiseDb\":"     + selfNoiseDb     + "," +
                "\"waterTempCelsius\":" + waterTempCelsius + "," +
                "\"salinityPsu\":"     + salinityPsu     + "," +
                "\"pingMode\":"        + (pingMode != null ? "\"" + pingMode + "\"" : "null") +
                "}";
    }
}
