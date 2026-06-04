package com.simulator.drone.model.ship;

public class HarpoonLauncherDetail {

    /** 20–35°C */
    private double canister1TempCelsius;
    private double canister2TempCelsius;
    private double canister3TempCelsius;
    private double canister4TempCelsius;
    private boolean launcherArmed;
    /** running count */
    private int    missilesFired;

    public HarpoonLauncherDetail() {}

    public HarpoonLauncherDetail(double canister1TempCelsius, double canister2TempCelsius,
                                 double canister3TempCelsius, double canister4TempCelsius,
                                 boolean launcherArmed, int missilesFired) {
        this.canister1TempCelsius = canister1TempCelsius;
        this.canister2TempCelsius = canister2TempCelsius;
        this.canister3TempCelsius = canister3TempCelsius;
        this.canister4TempCelsius = canister4TempCelsius;
        this.launcherArmed        = launcherArmed;
        this.missilesFired        = missilesFired;
    }

    public double getCanister1TempCelsius() { return canister1TempCelsius; }
    public void setCanister1TempCelsius(double canister1TempCelsius) { this.canister1TempCelsius = canister1TempCelsius; }

    public double getCanister2TempCelsius() { return canister2TempCelsius; }
    public void setCanister2TempCelsius(double canister2TempCelsius) { this.canister2TempCelsius = canister2TempCelsius; }

    public double getCanister3TempCelsius() { return canister3TempCelsius; }
    public void setCanister3TempCelsius(double canister3TempCelsius) { this.canister3TempCelsius = canister3TempCelsius; }

    public double getCanister4TempCelsius() { return canister4TempCelsius; }
    public void setCanister4TempCelsius(double canister4TempCelsius) { this.canister4TempCelsius = canister4TempCelsius; }

    public boolean isLauncherArmed() { return launcherArmed; }
    public void setLauncherArmed(boolean launcherArmed) { this.launcherArmed = launcherArmed; }

    public int getMissilesFired() { return missilesFired; }
    public void setMissilesFired(int missilesFired) { this.missilesFired = missilesFired; }

    @Override
    public String toString() {
        return "{" +
                "\"canister1TempCelsius\":" + canister1TempCelsius + "," +
                "\"canister2TempCelsius\":" + canister2TempCelsius + "," +
                "\"canister3TempCelsius\":" + canister3TempCelsius + "," +
                "\"canister4TempCelsius\":" + canister4TempCelsius + "," +
                "\"launcherArmed\":"        + launcherArmed        + "," +
                "\"missilesFired\":"        + missilesFired        +
                "}";
    }
}
