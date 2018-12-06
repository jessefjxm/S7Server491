// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MainItemSubGroupT {
    private List<ItemSubGroupT> itemSubGroups;
    private int selectRate;
    private int subGroupId;
    private IAcceptConditionHandler[][] conditions;

    public MainItemSubGroupT(final ResultSet rs, final int index, final List<ItemSubGroupT> itemSubGroupMap, final int subGroupId) throws SQLException {
        this.itemSubGroups = itemSubGroupMap;
        this.subGroupId = subGroupId;
        this.selectRate = rs.getInt("SelectRate" + index);
        final String conditionData = rs.getString("Condition" + index);
        if (conditionData != null) {
            this.conditions = ConditionService.getAcceptConditions(conditionData);
        }
    }

    public int getSubGroupId() {
        return this.subGroupId;
    }

    public IAcceptConditionHandler[][] getConditions() {
        return this.conditions;
    }

    public int getSelectRate() {
        return this.selectRate;
    }

    public ItemSubGroupT getItemSubGroup(final int itemId, final int enchantLevel) {
        for (final ItemSubGroupT template : this.itemSubGroups) {
            if (template.getItemId() == itemId && template.getEnchantLevel() == enchantLevel) {
                return template;
            }
        }
        return null;
    }

    public List<ItemSubGroupT> getItemSubGroups() {
        return (this.itemSubGroups == null) ? Collections.emptyList() : this.itemSubGroups;
    }
}
