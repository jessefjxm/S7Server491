package com.bdoemu.gameserver.model.fishing.templates;

import com.bdoemu.commons.utils.Rnd;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FishingT {
    private int dropId;
    private int dropIdHarpoon;
    private int dropIdNet;
    private int minWaitTime;
    private int maxWaitTime;
    private HashMap<Integer, Integer> drops;
    private Color color;

    public FishingT(final ResultSet rs) throws SQLException {
        this.drops = new HashMap<>();
        this.color = new Color(rs.getInt("R"), rs.getInt("G"), rs.getInt("B"));
        this.dropId = rs.getInt("DropID");
        this.dropIdHarpoon = rs.getInt("DropIDHarpoon");
        this.dropIdNet = rs.getInt("DropIDNet");
        for (int i = 1; i <= 5; ++i) {
            if (rs.getString("DropID" + i) != null) {
                this.drops.put(rs.getInt("DropID" + i), rs.getInt("DropRate" + i));
            }
        }
        this.minWaitTime = rs.getInt("MinWaitTime");
        this.maxWaitTime = rs.getInt("MaxWaitTime");
    }

    public Integer calculateDrop() {
        final double random = 1000000.0 * Rnd.nextDouble();
        double chanceFrom = 0.0;
        for (final Map.Entry<Integer, Integer> entry : this.drops.entrySet()) {
            if (chanceFrom >= 1000000.0) {
                chanceFrom = 0.0;
            }
            final double chance = entry.getValue();
            if (random >= chanceFrom && random <= chance + chanceFrom) {
                return entry.getKey();
            }
            chanceFrom += chance;
        }
        return -1;
    }

    public int getMinWaitTime() {
        return this.minWaitTime;
    }

    public int getMaxWaitTime() {
        return this.maxWaitTime;
    }

    public int getDropIdNet() {
        return this.dropIdNet;
    }

    public int getDropIdHarpoon() {
        return this.dropIdHarpoon;
    }

    public int getDropId() {
        return this.dropId;
    }

    public Color getColor() {
        return this.color;
    }
}
