// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlchemyStoneChangeT {
    private int itemId;
    private int needItemId;
    private int mainGroup;
    private int breakRate;
    private int downRate;
    private int downGroup;
    private long needItemCount;
    private IAcceptConditionHandler[][] conditions;

    public AlchemyStoneChangeT(final ResultSet rs) throws SQLException {
        this.itemId = rs.getInt("ItemKey");
        this.needItemId = rs.getInt("NeedItemKey");
        this.needItemCount = rs.getLong("NeedItemCount");
        this.mainGroup = rs.getInt("MainGroup");
        this.breakRate = rs.getInt("BreakRate");
        this.downRate = rs.getInt("DownRate");
        this.downGroup = rs.getInt("DownGroup");
        final String condition = rs.getString("Condition");
        if (condition != null) {
            this.conditions = ConditionService.getAcceptConditions(condition);
        }
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getNeedItemId() {
        return this.needItemId;
    }

    public long getNeedItemCount() {
        return this.needItemCount;
    }

    public int getMainGroup() {
        return this.mainGroup;
    }

    public int getBreakRate() {
        return this.breakRate;
    }

    public int getDownRate() {
        return this.downRate;
    }

    public int getDownGroup() {
        return this.downGroup;
    }

    public IAcceptConditionHandler[][] getConditions() {
        return this.conditions;
    }
}
