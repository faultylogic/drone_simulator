package com.simulator.drone.model.ship;

public class TowedArrayDetail {

    /** 0–200 m */
    private double depthMeters;
    /** 500–5 000 N */
    private double tensionNewtons;
    /** 0–96 */
    private int    activeElements;
    /** 2–15°C */
    private double arrayTempCelsius;
    /** 0–800 m distance behind ship */
    private double laybackMeters;
    /** "STOWED", "DEPLOYING", "DEPLOYED", "RECOVERING" */
    private String deploymentState;

    public TowedArrayDetail() {}

    public TowedArrayDetail(double depthMeters, double tensionNewtons,
                            int activeElements, double arrayTempCelsius,
                            double laybackMeters, String deploymentState) {
        this.depthMeters      = depthMeters;
        this.tensionNewtons   = tensionNewtons;
        this.activeElements   = activeElements;
        this.arrayTempCelsius = arrayTempCelsius;
        this.laybackMeters    = laybackMeters;
        this.deploymentState  = deploymentState;
    }

    public double getDepthMeters() { return depthMeters; }
    public void setDepthMeters(double depthMeters) { this.depthMeters = depthMeters; }

    public double getTensionNewtons() { return tensionNewtons; }
    public void setTensionNewtons(double tensionNewtons) { this.tensionNewtons = tensionNewtons; }

    public int getActiveElements() { return activeElements; }
    public void setActiveElements(int activeElements) { this.activeElements = activeElements; }

    public double getArrayTempCelsius() { return arrayTempCelsius; }
    public void setArrayTempCelsius(double arrayTempCelsius) { this.arrayTempCelsius = arrayTempCelsius; }

    public double getLaybackMeters() { return laybackMeters; }
    public void setLaybackMeters(double laybackMeters) { this.laybackMeters = laybackMeters; }

    public String getDeploymentState() { return deploymentState; }
    public void setDeploymentState(String deploymentState) { this.deploymentState = deploymentState; }

    @Override
    public String toString() {
        return "{" +
                "\"depthMeters\":"      + depthMeters      + "," +
                "\"tensionNewtons\":"   + tensionNewtons   + "," +
                "\"activeElements\":"   + activeElements   + "," +
                "\"arrayTempCelsius\":" + arrayTempCelsius + "," +
                "\"laybackMeters\":"    + laybackMeters    + "," +
                "\"deploymentState\":"  + (deploymentState != null ? "\"" + deploymentState + "\"" : "null") +
                "}";
    }
}
