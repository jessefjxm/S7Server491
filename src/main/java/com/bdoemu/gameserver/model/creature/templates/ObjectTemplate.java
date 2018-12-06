package com.bdoemu.gameserver.model.creature.templates;

import com.bdoemu.gameserver.model.creature.enums.EInstallationType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectTemplate {
    private int interiorPoints;
    private int objectKind;
    private EInstallationType installationType;
    private Integer roomServiceDropId;
    private Integer productRecipe;
    private Integer seedRecipe;
    private Long usingTime;
    private Integer installationMaxCount;

    public ObjectTemplate(final ResultSet rs) throws SQLException {
        this.interiorPoints = rs.getInt("InteriorPoint");
        this.objectKind = rs.getInt("ObjectKind");
        this.installationType = EInstallationType.valueOf(rs.getInt("InstallationType"));
        if (rs.getString("RoomServiceDropID") != null) {
            this.roomServiceDropId = rs.getInt("RoomServiceDropID");
        }
        if (rs.getString("UsingTime") != null) {
            this.usingTime = rs.getLong("UsingTime");
        }
        if (rs.getString("InstallationMaxCount") != null) {
            this.installationMaxCount = rs.getInt("InstallationMaxCount");
        }
        if (rs.getString("ProductRecipe") != null) {
            this.productRecipe = rs.getInt("ProductRecipe");
        }
        if (rs.getString("SeedRecipe") != null) {
            this.seedRecipe = rs.getInt("SeedRecipe");
        }
    }

    public Integer getSeedRecipe() {
        return this.seedRecipe;
    }

    public Integer getProductRecipe() {
        return this.productRecipe;
    }

    public Integer getInstallationMaxCount() {
        return this.installationMaxCount;
    }

    public Long getUsingTime() {
        return this.usingTime;
    }

    public Integer getRoomServiceDropId() {
        return this.roomServiceDropId;
    }

    public EInstallationType getInstallationType() {
        return this.installationType;
    }

    public int getObjectKind() {
        return this.objectKind;
    }

    public int getInteriorPoints() {
        return this.interiorPoints;
    }
}
