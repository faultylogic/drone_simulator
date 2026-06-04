package com.simulator.drone.model;

public class FleetConfig {

    private int shipCount;
    private int dronesPerShip;

    public FleetConfig() {
    }

    public FleetConfig(int shipCount, int dronesPerShip) {
        this.shipCount    = shipCount;
        this.dronesPerShip = dronesPerShip;
    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public int getDronesPerShip() {
        return dronesPerShip;
    }

    public void setDronesPerShip(int dronesPerShip) {
        this.dronesPerShip = dronesPerShip;
    }
}
