// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductToolPropertyT {
    private int itemKey;
    private int enchantLevel;
    private int param0;
    private int param1;
    private int param2;
    private int param3;
    private int autofishingTimePercents;

    public ProductToolPropertyT(final ResultSet resultSet) throws SQLException {
        this.itemKey = resultSet.getInt("ItemKey");
        this.enchantLevel = resultSet.getInt("EnchantLevel");
        this.param0 = resultSet.getInt("Param_0");
        this.param1 = resultSet.getInt("Param_1");
        this.param2 = resultSet.getInt("Param_2");
        this.param3 = resultSet.getInt("Param_3");
        this.autofishingTimePercents = resultSet.getInt("AutofishingTimePercents");
    }

    public int getItemKey() {
        return this.itemKey;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public int getParam0() {
        return this.param0;
    }

    public int getParam1() {
        return this.param1;
    }

    public int getParam2() {
        return this.param2;
    }

    public int getParam3() {
        return this.param3;
    }

    public int getAutofishingTimePercents() {
        return this.autofishingTimePercents;
    }
}
