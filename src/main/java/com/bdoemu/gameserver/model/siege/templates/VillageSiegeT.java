package com.bdoemu.gameserver.model.siege.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class VillageSiegeT {
    private int villageSiegeKey;
    private int r;
    private int g;
    private int b;
    private String regionName;
    private Set<Integer> regionKeys;

    public VillageSiegeT(final ResultSet rs) throws SQLException {
        this.regionKeys = new HashSet<>();
        this.villageSiegeKey = rs.getInt("VillageSiegeKey");
        this.r = rs.getInt("R");
        this.g = rs.getInt("G");
        this.b = rs.getInt("B");
        this.regionName = rs.getString("RegionName");
        final String[] regionKeyData = rs.getString("RegionKey").trim().split(",");
        for (final String regionKey : regionKeyData)
            this.getRegionKeys().add(Integer.parseInt(regionKey));
    }

    public int getVillageSiegeKey() {
        return this.villageSiegeKey;
    }

    public int getR() {
        return this.r;
    }

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public Set<Integer> getRegionKeys() {
        return this.regionKeys;
    }
}
