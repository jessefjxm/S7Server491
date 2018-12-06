package com.bdoemu.gameserver.model.trade.templates;

import com.bdoemu.gameserver.model.misc.enums.ETaxType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxTemplate {
    private ETerritoryKeyType territoryKey;
    private ETaxType taxType;
    private int minPercentOrPrice;
    private int maxPercentOrPrice;
    private int systemPercent;

    public TaxTemplate(final ResultSet rs) throws SQLException {
        this.territoryKey = ETerritoryKeyType.values()[rs.getInt("TerritoryKey")];
        this.taxType = ETaxType.values()[rs.getInt("TaxType")];
        this.minPercentOrPrice = rs.getInt("MinPercentOrPrice");
        this.maxPercentOrPrice = rs.getInt("MaxPercentOrPrice");
        this.systemPercent = rs.getInt("SystemPercent");
    }

    public ETerritoryKeyType getTerritoryKey() {
        return this.territoryKey;
    }

    public ETaxType getTaxType() {
        return this.taxType;
    }

    public int getMinPercentOrPrice() {
        return this.minPercentOrPrice;
    }

    public int getMaxPercentOrPrice() {
        return this.maxPercentOrPrice;
    }

    public int getSystemPercent() {
        return this.systemPercent;
    }
}
