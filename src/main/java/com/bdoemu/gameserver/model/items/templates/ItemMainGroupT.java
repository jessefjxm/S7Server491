// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemMainGroupT {
    private List<MainItemSubGroupT> mainItemSubGroups;
    private boolean isSelectOnlyOne;
    private int mainGroupId;

    public ItemMainGroupT(final ResultSet rs, final HashMap<Integer, List<ItemSubGroupT>> itemSubGroupMap) throws SQLException {
        this.mainItemSubGroups = new ArrayList<MainItemSubGroupT>();
        this.mainGroupId = rs.getInt("ItemMainGroupKey");
        this.isSelectOnlyOne = (rs.getByte("DoSelectOnlyOne") == 1);
        for (int i = 0; i < 4; ++i) {
            if (rs.getString("ItemSubGroupKey" + i) != null) {
                final int subGroupId = rs.getInt("ItemSubGroupKey" + i);
                this.mainItemSubGroups.add(new MainItemSubGroupT(rs, i, itemSubGroupMap.get(subGroupId), subGroupId));
            }
        }
    }

    public int getMainGroupId() {
        return this.mainGroupId;
    }

    public boolean isSelectOnlyOne() {
        return this.isSelectOnlyOne;
    }

    public List<MainItemSubGroupT> getMainItemSubGroups() {
        return this.mainItemSubGroups;
    }
}
