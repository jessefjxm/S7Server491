// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class HouseInfoT {
    private final HashMap<Integer, Integer> craftMap;
    private int houseId;
    private int needExplorePoint;
    private int affiliatedTown;
    private Integer needHouseId;

    public HouseInfoT(final ResultSet rs) throws SQLException {
        this.craftMap = new HashMap<Integer, Integer>();
        this.houseId = rs.getInt("CharacterKey");
        this.needExplorePoint = rs.getInt("NeedExplorePoint");
        this.affiliatedTown = rs.getInt("AffiliatedTown");
        if (rs.getString("NeedHouseKey") != null) {
            this.needHouseId = rs.getInt("NeedHouseKey");
        }
        final String[] split;
        final String[] craftList = split = rs.getString("CraftList").split(",");
        for (final String craftData : split) {
            final String[] splitedDate = craftData.split(":");
            final int craftKey = Integer.parseInt(splitedDate[0].trim());
            final int maxCraftLevel = Integer.parseInt(splitedDate[1].trim());
            this.craftMap.put(craftKey, maxCraftLevel);
        }
    }

    public int getAffiliatedTown() {
        return this.affiliatedTown;
    }

    public boolean containsReceipe(final int craftKey) {
        return this.craftMap.containsKey(craftKey);
    }

    public int getMaxLevel(final int craftKey) {
        return this.craftMap.get(craftKey);
    }

    public Integer getNeedHouseId() {
        return this.needHouseId;
    }

    public int getHouseId() {
        return this.houseId;
    }

    public int getNeedExplorePoint() {
        return this.needExplorePoint;
    }
}
