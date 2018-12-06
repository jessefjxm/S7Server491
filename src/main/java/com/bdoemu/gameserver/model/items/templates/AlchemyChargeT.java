// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.items.enums.EAlchemyStoneType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlchemyChargeT {
    private int itemId;
    private int chargePoint;
    private EAlchemyStoneType stoneType;

    public AlchemyChargeT(final ResultSet rs) throws SQLException {
        this.itemId = rs.getInt("ItemKey");
        this.stoneType = EAlchemyStoneType.valueOf(rs.getInt("Type"));
        this.chargePoint = rs.getInt("ChargePoint");
    }

    public int getChargePoint() {
        return this.chargePoint;
    }

    public int getItemId() {
        return this.itemId;
    }

    public EAlchemyStoneType getStoneType() {
        return this.stoneType;
    }
}
