package com.simulator.drone.model;

import java.time.Instant;

public class DronePayload {

    private String droneId;
    private String shipId;
    private double latitude;
    private double longitude;
    private double altitudeMeters;
    private double speedMps;
    private double headingDegrees;
    private int batteryPercent;
    private double payloadKg;
    private DroneStatus status;
    private Instant timestamp;

    public DronePayload() {}

    public DronePayload(String droneId, String shipId, double latitude, double longitude,
                        double altitudeMeters, double speedMps, double headingDegrees,
                        int batteryPercent, double payloadKg, DroneStatus status) {
        this.droneId = droneId;
        this.shipId = shipId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitudeMeters = altitudeMeters;
        this.speedMps = speedMps;
        this.headingDegrees = headingDegrees;
        this.batteryPercent = batteryPercent;
        this.payloadKg = payloadKg;
        this.status = status;
        this.timestamp = Instant.now();
    }

    public String getDroneId() { return droneId; }
    public void setDroneId(String droneId) { this.droneId = droneId; }

    public String getShipId() { return shipId; }
    public void setShipId(String shipId) { this.shipId = shipId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getAltitudeMeters() { return altitudeMeters; }
    public void setAltitudeMeters(double altitudeMeters) { this.altitudeMeters = altitudeMeters; }

    public double getSpeedMps() { return speedMps; }
    public void setSpeedMps(double speedMps) { this.speedMps = speedMps; }

    public double getHeadingDegrees() { return headingDegrees; }
    public void setHeadingDegrees(double headingDegrees) { this.headingDegrees = headingDegrees; }

    public int getBatteryPercent() { return batteryPercent; }
    public void setBatteryPercent(int batteryPercent) { this.batteryPercent = batteryPercent; }

    public double getPayloadKg() { return payloadKg; }
    public void setPayloadKg(double payloadKg) { this.payloadKg = payloadKg; }

    public DroneStatus getStatus() { return status; }
    public void setStatus(DroneStatus status) { this.status = status; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "{" +
                "\"droneId\":" + (droneId != null ? "\"" + droneId + "\"" : "null") + "," +
                "\"shipId\":"  + (shipId  != null ? "\"" + shipId  + "\"" : "null") + "," +
                "\"latitude\":" + latitude + "," +
                "\"longitude\":" + longitude + "," +
                "\"altitudeMeters\":" + altitudeMeters + "," +
                "\"speedMps\":" + speedMps + "," +
                "\"headingDegrees\":" + headingDegrees + "," +
                "\"batteryPercent\":" + batteryPercent + "," +
                "\"payloadKg\":"      + payloadKg + "," +
                "\"status\":" + (status != null ? "\"" + status.name() + "\"" : "null") + "," +
                "\"timestamp\":" + (timestamp != null ? "\"" + timestamp + "\"" : "null") +
                "}";
    }
}
