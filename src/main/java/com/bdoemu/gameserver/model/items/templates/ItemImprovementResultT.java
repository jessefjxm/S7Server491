// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemImprovementResultT {
    private int index;
    private int[] groupItems;

    public ItemImprovementResultT(final ResultSet rs) throws SQLException {
        this.groupItems = new int[5];
        this.index = rs.getInt("index");
        for (int i = 0; i < this.groupItems.length; ++i) {
            this.groupItems[i] = rs.getInt("groupItem" + i);
        }
    }

    public int getIndex() {
        return this.index;
    }

    public int[] getGroupItems() {
        return this.groupItems;
    }

    public boolean containItem(final int itemId) {
        for (final int groupItem : this.groupItems) {
            if (groupItem == itemId) {
                return true;
            }
        }
        return false;
    }
}
