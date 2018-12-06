// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseTransferT {
    private int transferKey;
    private int level;
    private int time;
    private Integer itemKey;
    private Long count;

    public HouseTransferT(final ResultSet rs) throws SQLException {
        this.transferKey = rs.getInt("TransferKey");
        this.level = rs.getInt("Level");
        this.time = rs.getInt("Time");
        if (rs.getString("ItemKey1") != null) {
            this.itemKey = rs.getInt("ItemKey1");
            this.count = rs.getLong("ItemCount1");
        }
    }

    public Integer getItemKey() {
        return this.itemKey;
    }

    public Long getCount() {
        return this.count;
    }

    public int getTransferKey() {
        return this.transferKey;
    }

    public int getTime() {
        return this.time;
    }

    public int getLevel() {
        return this.level;
    }
}
