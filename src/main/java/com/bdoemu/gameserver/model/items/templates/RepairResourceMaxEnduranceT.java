// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepairResourceMaxEnduranceT {
    private int itemId;
    private int repairCount;
    private int needMoney;

    public RepairResourceMaxEnduranceT(final int index, final ResultSet rs) throws SQLException {
        this.itemId = rs.getInt("Item" + index);
        this.repairCount = rs.getInt("RepairCount" + index);
        this.needMoney = rs.getInt("NeedMoney" + index);
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getNeedMoney() {
        return this.needMoney;
    }

    public int getRepairCount() {
        return this.repairCount;
    }
}
