package com.simulator.drone.model.ship;

public class WeaponSystem {

    private boolean bofors57mmOperational;
    /** 0-1000 */
    private int bofors57mmAmmoRounds;
    /** 20-400°C */
    private double bofors57mmBarrelTempCelsius;
    /** Phalanx CIWS */
    private boolean ciswsOperational;
    /** 0-1550 */
    private int ciswsAmmoRounds;
    /** 0-8 */
    private int harpoonMissilesReady;
    /** 0-8 Evolved Sea Sparrow */
    private int vlsEssmCellsReady;
    /** 0-6 */
    private int mk46TorpedoesReady;
    /** "SAFE", "TIGHT", or "WEAPONS_FREE" */
    private String readinessState;

    // Component detail objects
    private BoforsGunDetail       bofors57mmDetail;
    private CIWSDetail            ciswsDetail;
    private HarpoonLauncherDetail harpoonDetail;
    private VLSDetail             vlsDetail;
    private TorpedoDetail         torpedoDetail;

    public WeaponSystem() {}

    public WeaponSystem(boolean bofors57mmOperational, int bofors57mmAmmoRounds,
                        double bofors57mmBarrelTempCelsius, boolean ciswsOperational,
                        int ciswsAmmoRounds, int harpoonMissilesReady,
                        int vlsEssmCellsReady, int mk46TorpedoesReady,
                        String readinessState) {
        this.bofors57mmOperational       = bofors57mmOperational;
        this.bofors57mmAmmoRounds        = bofors57mmAmmoRounds;
        this.bofors57mmBarrelTempCelsius = bofors57mmBarrelTempCelsius;
        this.ciswsOperational            = ciswsOperational;
        this.ciswsAmmoRounds             = ciswsAmmoRounds;
        this.harpoonMissilesReady        = harpoonMissilesReady;
        this.vlsEssmCellsReady           = vlsEssmCellsReady;
        this.mk46TorpedoesReady          = mk46TorpedoesReady;
        this.readinessState              = readinessState;
    }

    public boolean isBofors57mmOperational() { return bofors57mmOperational; }
    public void setBofors57mmOperational(boolean bofors57mmOperational) { this.bofors57mmOperational = bofors57mmOperational; }

    public int getBofors57mmAmmoRounds() { return bofors57mmAmmoRounds; }
    public void setBofors57mmAmmoRounds(int bofors57mmAmmoRounds) { this.bofors57mmAmmoRounds = bofors57mmAmmoRounds; }

    public double getBofors57mmBarrelTempCelsius() { return bofors57mmBarrelTempCelsius; }
    public void setBofors57mmBarrelTempCelsius(double bofors57mmBarrelTempCelsius) { this.bofors57mmBarrelTempCelsius = bofors57mmBarrelTempCelsius; }

    public boolean isCiswsOperational() { return ciswsOperational; }
    public void setCiswsOperational(boolean ciswsOperational) { this.ciswsOperational = ciswsOperational; }

    public int getCiswsAmmoRounds() { return ciswsAmmoRounds; }
    public void setCiswsAmmoRounds(int ciswsAmmoRounds) { this.ciswsAmmoRounds = ciswsAmmoRounds; }

    public int getHarpoonMissilesReady() { return harpoonMissilesReady; }
    public void setHarpoonMissilesReady(int harpoonMissilesReady) { this.harpoonMissilesReady = harpoonMissilesReady; }

    public int getVlsEssmCellsReady() { return vlsEssmCellsReady; }
    public void setVlsEssmCellsReady(int vlsEssmCellsReady) { this.vlsEssmCellsReady = vlsEssmCellsReady; }

    public int getMk46TorpedoesReady() { return mk46TorpedoesReady; }
    public void setMk46TorpedoesReady(int mk46TorpedoesReady) { this.mk46TorpedoesReady = mk46TorpedoesReady; }

    public String getReadinessState() { return readinessState; }
    public void setReadinessState(String readinessState) { this.readinessState = readinessState; }

    public BoforsGunDetail getBofors57mmDetail() { return bofors57mmDetail; }
    public void setBofors57mmDetail(BoforsGunDetail bofors57mmDetail) { this.bofors57mmDetail = bofors57mmDetail; }

    public CIWSDetail getCiswsDetail() { return ciswsDetail; }
    public void setCiswsDetail(CIWSDetail ciswsDetail) { this.ciswsDetail = ciswsDetail; }

    public HarpoonLauncherDetail getHarpoonDetail() { return harpoonDetail; }
    public void setHarpoonDetail(HarpoonLauncherDetail harpoonDetail) { this.harpoonDetail = harpoonDetail; }

    public VLSDetail getVlsDetail() { return vlsDetail; }
    public void setVlsDetail(VLSDetail vlsDetail) { this.vlsDetail = vlsDetail; }

    public TorpedoDetail getTorpedoDetail() { return torpedoDetail; }
    public void setTorpedoDetail(TorpedoDetail torpedoDetail) { this.torpedoDetail = torpedoDetail; }

    @Override
    public String toString() {
        return "{" +
                "\"bofors57mmOperational\":"       + bofors57mmOperational       + "," +
                "\"bofors57mmAmmoRounds\":"        + bofors57mmAmmoRounds        + "," +
                "\"bofors57mmBarrelTempCelsius\":" + bofors57mmBarrelTempCelsius + "," +
                "\"ciswsOperational\":"            + ciswsOperational            + "," +
                "\"ciswsAmmoRounds\":"             + ciswsAmmoRounds             + "," +
                "\"harpoonMissilesReady\":"        + harpoonMissilesReady        + "," +
                "\"vlsEssmCellsReady\":"           + vlsEssmCellsReady           + "," +
                "\"mk46TorpedoesReady\":"          + mk46TorpedoesReady          + "," +
                "\"readinessState\":"              + (readinessState != null ? "\"" + readinessState + "\"" : "null") + "," +
                "\"bofors57mmDetail\":"            + (bofors57mmDetail != null ? bofors57mmDetail.toString() : "null") + "," +
                "\"ciswsDetail\":"                 + (ciswsDetail      != null ? ciswsDetail.toString()      : "null") + "," +
                "\"harpoonDetail\":"               + (harpoonDetail    != null ? harpoonDetail.toString()    : "null") + "," +
                "\"vlsDetail\":"                   + (vlsDetail        != null ? vlsDetail.toString()        : "null") + "," +
                "\"torpedoDetail\":"               + (torpedoDetail    != null ? torpedoDetail.toString()    : "null") +
                "}";
    }
}
