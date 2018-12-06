// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.items.enums.EAlchemyStoneType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlchemyUpgradeT {
    private int itemId;
    private long exp;
    private EAlchemyStoneType stoneType;

    public AlchemyUpgradeT(final ResultSet rs) throws SQLException {
        this.itemId = rs.getInt("ItemKey");
        this.stoneType = EAlchemyStoneType.valueOf(rs.getInt("Type"));
        this.exp = rs.getInt("Experience");
    }

    public long getExp() {
        return this.exp;
    }

    public int getItemId() {
        return this.itemId;
    }

    public EAlchemyStoneType getStoneType() {
        return this.stoneType;
    }
}
