package com.bdoemu.gameserver.model.stats.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TranslateStatT {
    private int point;
    private int movespeedPercent;
    private int attackspeedPercent;
    private int castingspeedPercent;
    private int criticalPercent;
    private int dropItemPercent;
    private int fishingPercent;
    private int collectionPercent;

    public TranslateStatT(final ResultSet rs) throws SQLException {
        this.point = rs.getInt("Point");
        this.movespeedPercent = rs.getInt("MovespeedPercent");
        this.attackspeedPercent = rs.getInt("AttackspeedPercent");
        this.castingspeedPercent = rs.getInt("CastingspeedPercent");
        this.criticalPercent = rs.getInt("CriticalPercent");
        this.dropItemPercent = rs.getInt("DropItemPercent");
        this.fishingPercent = rs.getInt("FishingPercent");
        this.collectionPercent = rs.getInt("CollectionPercent");
    }

    public int getFishingPercent() {
        return this.fishingPercent;
    }

    public int getDropItemPercent() {
        return this.dropItemPercent;
    }

    public int getCriticalPercent() {
        return this.criticalPercent;
    }

    public int getCollectionPercent() {
        return this.collectionPercent;
    }

    public int getCastingspeedPercent() {
        return this.castingspeedPercent;
    }

    public int getAttackspeedPercent() {
        return this.attackspeedPercent;
    }

    public int getMovespeedPercent() {
        return this.movespeedPercent;
    }

    public int getPoint() {
        return this.point;
    }
}
