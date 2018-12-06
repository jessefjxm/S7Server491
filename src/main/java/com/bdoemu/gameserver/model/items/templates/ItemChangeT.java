// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemChangeT {
    private int fromItemId;
    private int fromEnchantLevel;
    private int toItemId;
    private int toEnchantLevel;
    private int type;

    public ItemChangeT(final ResultSet rs) throws SQLException {
        this.fromItemId = rs.getInt("FromItemKey");
        this.fromEnchantLevel = rs.getInt("FromEnchantLevel");
        this.toItemId = rs.getInt("ToItemKey");
        this.toEnchantLevel = rs.getInt("ToEnchantLevel");
        this.type = rs.getInt("Type");
    }

    public int getFromEnchantLevel() {
        return this.fromEnchantLevel;
    }

    public int getFromItemId() {
        return this.fromItemId;
    }

    public int getToEnchantLevel() {
        return this.toEnchantLevel;
    }

    public int getToItemId() {
        return this.toItemId;
    }
}
