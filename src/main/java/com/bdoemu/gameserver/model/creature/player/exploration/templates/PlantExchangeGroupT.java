package com.bdoemu.gameserver.model.creature.player.exploration.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantExchangeGroupT {
    private int exchangeGroupKey;

    public PlantExchangeGroupT(final ResultSet rs) throws SQLException {
        this.exchangeGroupKey = rs.getInt("ExchangeKey_0");
    }

    public int getExchangeGroupKey() {
        return this.exchangeGroupKey;
    }
}
