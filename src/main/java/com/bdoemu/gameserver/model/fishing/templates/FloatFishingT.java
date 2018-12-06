package com.bdoemu.gameserver.model.fishing.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatFishingT {
    private int fishingGroupKey;
    private int dropId;
    private int dropRate;
    private int pointSize;
    private double startPositionX;
    private double startPositionY;
    private double startPositionZ;
    private double endPositionX;
    private double endPositionY;
    private int spawnCharacterKey;
    private int spawnActionIndex;
    private int minWaitTime;
    private int maxWaitTime;
    private int minFishCount;
    private int maxFishCount;
    private int pointRemainTime;
    private int availableFishingLevel;
    private int observerFishingLevel;

    public FloatFishingT(final ResultSet rs) throws SQLException {
        this.fishingGroupKey = rs.getInt("FishingGroupKey");
        this.dropId = rs.getInt("DropID2");
        this.dropRate = rs.getInt("DropRate2");
        this.minWaitTime = rs.getInt("MinWaitTime");
        this.maxWaitTime = rs.getInt("MaxWaitTime");
        this.pointRemainTime = rs.getInt("PointRemainTime");
        this.pointSize = rs.getInt("PointSize");
        this.minWaitTime = rs.getInt("MinWaitTime");
        this.maxWaitTime = rs.getInt("MaxWaitTime");
        this.availableFishingLevel = rs.getInt("AvailableFishingLevel");
        this.observerFishingLevel = rs.getInt("ObserveFishingLevel");
        this.startPositionX = rs.getDouble("StartPositionX");
        this.startPositionZ = rs.getDouble("StartPositionY");
        this.startPositionY = rs.getDouble("StartPositionZ");
        this.endPositionX = rs.getDouble("EndPositionX");
        this.endPositionY = rs.getDouble("EndPositionZ");
        this.spawnCharacterKey = rs.getInt("SpawnCharacterKey");
        this.spawnActionIndex = rs.getInt("SpawnActionIndex");
    }

    public int getFishingGroupKey() {
        return this.fishingGroupKey;
    }

    public int getDropId() {
        return this.dropId;
    }

    public int getDropRate() {
        return this.dropRate;
    }

    public int getPointSize() {
        return this.pointSize;
    }

    public double getStartPositionX() {
        return this.startPositionX;
    }

    public double getStartPositionY() {
        return this.startPositionY;
    }

    public double getStartPositionZ() {
        return this.startPositionZ;
    }

    public double getEndPositionX() {
        return this.endPositionX;
    }

    public double getEndPositionY() {
        return this.endPositionY;
    }

    public int getSpawnCharacterKey() {
        return this.spawnCharacterKey;
    }

    public int getMinWaitTime() {
        return this.minWaitTime;
    }

    public int getMaxWaitTime() {
        return this.maxWaitTime;
    }

    public int getMinFishCount() {
        return this.minFishCount;
    }

    public int getMaxFishCount() {
        return this.maxFishCount;
    }

    public int getPointRemainTime() {
        return this.pointRemainTime;
    }

    public int getAvailableFishingLevel() {
        return this.availableFishingLevel;
    }

    public int getObserverFishingLevel() {
        return this.observerFishingLevel;
    }

    public int getSpawnActionIndex() {
        return this.spawnActionIndex;
    }
}
