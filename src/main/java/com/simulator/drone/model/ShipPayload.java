package com.simulator.drone.model;

import com.simulator.drone.model.ship.DamageControlSystem;
import com.simulator.drone.model.ship.PowerSystem;
import com.simulator.drone.model.ship.PropulsionSystem;
import com.simulator.drone.model.ship.RadarSystem;
import com.simulator.drone.model.ship.SonarSystem;
import com.simulator.drone.model.ship.WaterSystem;
import com.simulator.drone.model.ship.WeaponSystem;

import java.time.Instant;

public class ShipPayload {

    private String shipId;
    private double latitude;
    private double longitude;
    private double headingDegrees;
    private double speedKnots;
    private int crewActive;
    private int droneCount;
    private ShipAlertLevel alertLevel;
    private Instant timestamp;

    // Sub-systems — set after construction
    private PropulsionSystem propulsion;
    private PowerSystem power;
    private WeaponSystem weapons;
    private SonarSystem sonar;
    private RadarSystem radar;
    private WaterSystem waterSystems;
    private DamageControlSystem damageControl;

    public ShipPayload() {}

    public ShipPayload(String shipId, double latitude, double longitude,
                       double headingDegrees, double speedKnots,
                       int crewActive, int droneCount, ShipAlertLevel alertLevel) {
        this.shipId         = shipId;
        this.latitude       = latitude;
        this.longitude      = longitude;
        this.headingDegrees = headingDegrees;
        this.speedKnots     = speedKnots;
        this.crewActive     = crewActive;
        this.droneCount     = droneCount;
        this.alertLevel     = alertLevel;
        this.timestamp      = Instant.now();
    }

    public String getShipId() { return shipId; }
    public void setShipId(String shipId) { this.shipId = shipId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getHeadingDegrees() { return headingDegrees; }
    public void setHeadingDegrees(double headingDegrees) { this.headingDegrees = headingDegrees; }

    public double getSpeedKnots() { return speedKnots; }
    public void setSpeedKnots(double speedKnots) { this.speedKnots = speedKnots; }

    public int getCrewActive() { return crewActive; }
    public void setCrewActive(int crewActive) { this.crewActive = crewActive; }

    public int getDroneCount() { return droneCount; }
    public void setDroneCount(int droneCount) { this.droneCount = droneCount; }

    public ShipAlertLevel getAlertLevel() { return alertLevel; }
    public void setAlertLevel(ShipAlertLevel alertLevel) { this.alertLevel = alertLevel; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public PropulsionSystem getPropulsion() { return propulsion; }
    public void setPropulsion(PropulsionSystem propulsion) { this.propulsion = propulsion; }

    public PowerSystem getPower() { return power; }
    public void setPower(PowerSystem power) { this.power = power; }

    public WeaponSystem getWeapons() { return weapons; }
    public void setWeapons(WeaponSystem weapons) { this.weapons = weapons; }

    public SonarSystem getSonar() { return sonar; }
    public void setSonar(SonarSystem sonar) { this.sonar = sonar; }

    public RadarSystem getRadar() { return radar; }
    public void setRadar(RadarSystem radar) { this.radar = radar; }

    public WaterSystem getWaterSystems() { return waterSystems; }
    public void setWaterSystems(WaterSystem waterSystems) { this.waterSystems = waterSystems; }

    public DamageControlSystem getDamageControl() { return damageControl; }
    public void setDamageControl(DamageControlSystem damageControl) { this.damageControl = damageControl; }

    @Override
    public String toString() {
        return "{" +
                "\"shipId\":"         + (shipId != null ? "\"" + shipId + "\"" : "null") + "," +
                "\"latitude\":"       + latitude + "," +
                "\"longitude\":"      + longitude + "," +
                "\"headingDegrees\":" + headingDegrees + "," +
                "\"speedKnots\":"     + speedKnots + "," +
                "\"crewActive\":"     + crewActive + "," +
                "\"droneCount\":"     + droneCount + "," +
                "\"alertLevel\":"     + (alertLevel != null ? "\"" + alertLevel.name() + "\"" : "null") + "," +
                "\"timestamp\":"      + (timestamp != null ? "\"" + timestamp + "\"" : "null") + "," +
                "\"propulsion\":"     + (propulsion != null ? propulsion.toString() : "null") + "," +
                "\"power\":"          + (power != null ? power.toString() : "null") + "," +
                "\"weapons\":"        + (weapons != null ? weapons.toString() : "null") + "," +
                "\"sonar\":"          + (sonar != null ? sonar.toString() : "null") + "," +
                "\"radar\":"          + (radar != null ? radar.toString() : "null") + "," +
                "\"waterSystems\":"   + (waterSystems != null ? waterSystems.toString() : "null") + "," +
                "\"damageControl\":"  + (damageControl != null ? damageControl.toString() : "null") +
                "}";
    }
}
