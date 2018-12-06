package com.bdoemu.gameserver.model.creature.player.exploration.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WaypointTemplate {
    private float resurrectionPointX;
    private float resurrectionPointY;
    private float resurrectionPointZ;
    private int waypointKey;
    private int needExplorePoint;
    private int discoveryRange;
    private Integer itemKeyOnDiscovery;
    private Integer itemKeyOnInvestment;
    private boolean isAutoDiscovery;
    private PlantZoneWorkingT plantZoneWorkingT;

    public WaypointTemplate(final ResultSet rs, final PlantZoneWorkingT plantZoneWorkingT) throws SQLException {
        this.plantZoneWorkingT = plantZoneWorkingT;
        this.waypointKey = rs.getInt("WaypointKey");
        this.discoveryRange = rs.getInt("DiscoveryRange");
        this.isAutoDiscovery = (rs.getByte("isAutoDiscovery") == 1);
        this.needExplorePoint = rs.getInt("NeedExplorePoint");
        if (rs.getString("GivingDropItemKeyOnDiscovery") != null) {
            this.itemKeyOnDiscovery = rs.getInt("GivingDropItemKeyOnDiscovery");
        }
        if (rs.getString("GivingDropItemKeyOnInvestment") != null) {
            this.itemKeyOnInvestment = rs.getInt("GivingDropItemKeyOnInvestment");
        }
        this.resurrectionPointX = rs.getFloat("ExploreResurrectionPointX");
        this.resurrectionPointY = rs.getFloat("ExploreResurrectionPointZ");
        this.resurrectionPointZ = rs.getFloat("ExploreResurrectionPointY");
    }

    public PlantZoneWorkingT getPlantZoneWorkingT() {
        return this.plantZoneWorkingT;
    }

    public int getNeedExplorePoint() {
        return this.needExplorePoint;
    }

    public boolean isAutoDiscovery() {
        return this.isAutoDiscovery;
    }

    public Integer getItemKeyOnDiscovery() {
        return this.itemKeyOnDiscovery;
    }

    public Integer getItemKeyOnInvestment() {
        return this.itemKeyOnInvestment;
    }

    public int getWaypointKey() {
        return this.waypointKey;
    }

    public int getDiscoveryRange() {
        return this.discoveryRange;
    }

    public float getX() {
        return this.resurrectionPointX;
    }

    public float getY() {
        return this.resurrectionPointY;
    }

    public float getZ() {
        return this.resurrectionPointZ;
    }
}
