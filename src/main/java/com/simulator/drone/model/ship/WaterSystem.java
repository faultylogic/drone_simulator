package com.simulator.drone.model.ship;

public class WaterSystem {

    /** 0-100% */
    private double potableFreshWaterPercent;
    /** 15-45°C */
    private double seawaterCoolingTempCelsius;
    /** 0-30 cm */
    private double bilgeWaterLevelCm;
    /** 7.5-11 bar */
    private double fireMainPressureBar;
    /** 0-2000 L/hr */
    private double desalinatorOutputLph;
    /** 0-100% */
    private double sewageTankPercent;

    // Component detail objects
    private SeawaterCoolingDetail coolingDetail;
    private FireFightingDetail    firefightingDetail;
    private BilgeDetail           bilgeDetail;

    public WaterSystem() {}

    public WaterSystem(double potableFreshWaterPercent, double seawaterCoolingTempCelsius,
                       double bilgeWaterLevelCm, double fireMainPressureBar,
                       double desalinatorOutputLph, double sewageTankPercent) {
        this.potableFreshWaterPercent  = potableFreshWaterPercent;
        this.seawaterCoolingTempCelsius = seawaterCoolingTempCelsius;
        this.bilgeWaterLevelCm         = bilgeWaterLevelCm;
        this.fireMainPressureBar       = fireMainPressureBar;
        this.desalinatorOutputLph      = desalinatorOutputLph;
        this.sewageTankPercent         = sewageTankPercent;
    }

    public double getPotableFreshWaterPercent() { return potableFreshWaterPercent; }
    public void setPotableFreshWaterPercent(double potableFreshWaterPercent) { this.potableFreshWaterPercent = potableFreshWaterPercent; }

    public double getSeawaterCoolingTempCelsius() { return seawaterCoolingTempCelsius; }
    public void setSeawaterCoolingTempCelsius(double seawaterCoolingTempCelsius) { this.seawaterCoolingTempCelsius = seawaterCoolingTempCelsius; }

    public double getBilgeWaterLevelCm() { return bilgeWaterLevelCm; }
    public void setBilgeWaterLevelCm(double bilgeWaterLevelCm) { this.bilgeWaterLevelCm = bilgeWaterLevelCm; }

    public double getFireMainPressureBar() { return fireMainPressureBar; }
    public void setFireMainPressureBar(double fireMainPressureBar) { this.fireMainPressureBar = fireMainPressureBar; }

    public double getDesalinatorOutputLph() { return desalinatorOutputLph; }
    public void setDesalinatorOutputLph(double desalinatorOutputLph) { this.desalinatorOutputLph = desalinatorOutputLph; }

    public double getSewageTankPercent() { return sewageTankPercent; }
    public void setSewageTankPercent(double sewageTankPercent) { this.sewageTankPercent = sewageTankPercent; }

    public SeawaterCoolingDetail getCoolingDetail() { return coolingDetail; }
    public void setCoolingDetail(SeawaterCoolingDetail coolingDetail) { this.coolingDetail = coolingDetail; }

    public FireFightingDetail getFirefightingDetail() { return firefightingDetail; }
    public void setFirefightingDetail(FireFightingDetail firefightingDetail) { this.firefightingDetail = firefightingDetail; }

    public BilgeDetail getBilgeDetail() { return bilgeDetail; }
    public void setBilgeDetail(BilgeDetail bilgeDetail) { this.bilgeDetail = bilgeDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"potableFreshWaterPercent\":"   + potableFreshWaterPercent   + "," +
                "\"seawaterCoolingTempCelsius\":" + seawaterCoolingTempCelsius + "," +
                "\"bilgeWaterLevelCm\":"          + bilgeWaterLevelCm          + "," +
                "\"fireMainPressureBar\":"        + fireMainPressureBar        + "," +
                "\"desalinatorOutputLph\":"       + desalinatorOutputLph       + "," +
                "\"sewageTankPercent\":"          + sewageTankPercent          + "," +
                "\"coolingDetail\":"             + (coolingDetail       != null ? coolingDetail.toString()       : "null") + "," +
                "\"firefightingDetail\":"        + (firefightingDetail  != null ? firefightingDetail.toString()  : "null") + "," +
                "\"bilgeDetail\":"               + (bilgeDetail         != null ? bilgeDetail.toString()         : "null") +
                "}";
    }
}
