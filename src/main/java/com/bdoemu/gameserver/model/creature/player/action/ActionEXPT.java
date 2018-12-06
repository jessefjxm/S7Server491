package com.bdoemu.gameserver.model.creature.player.action;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionEXPT {
    private int classId;
    private int level;
    private double quest;
    private double collect;
    private double manufacture;
    private double alchemy;
    private double fishing;
    private double horseTaming;
    private double study;
    private double trade;
    private double farming;

    public ActionEXPT(final ResultSet rs) throws SQLException {
        this.classId = rs.getInt("Class");
        this.level = rs.getInt("Level");
        this.quest = rs.getDouble("Quest");
        this.collect = rs.getDouble("Collect");
        this.manufacture = rs.getDouble("Manufacture");
        this.alchemy = rs.getDouble("Alchemy");
        this.fishing = rs.getDouble("Fishing");
        this.horseTaming = rs.getDouble("HorseTaming");
        this.study = rs.getDouble("Study");
        this.trade = rs.getDouble("Trade");
        this.farming = rs.getDouble("Farming");
    }

    public double getManufacture() {
        return this.manufacture;
    }

    public double getHorseTaming() {
        return this.horseTaming;
    }

    public double getCollect() {
        return this.collect;
    }

    public double getFishing() {
        return this.fishing;
    }

    public double getAlchemy() {
        return this.alchemy;
    }

    public int getLevel() {
        return this.level;
    }

    public int getClassId() {
        return this.classId;
    }
}
