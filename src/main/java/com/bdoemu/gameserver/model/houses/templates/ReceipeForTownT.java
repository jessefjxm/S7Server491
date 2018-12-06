// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.templates;

import com.bdoemu.gameserver.model.houses.enums.EHouseReceipeType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceipeForTownT {
    private int receipeKey;
    private int transferKey;
    private EHouseReceipeType houseReceipeType;
    private List<Integer>[] exchangeKeys;

    public ReceipeForTownT(final ResultSet rs) throws SQLException {
        this.exchangeKeys = (List<Integer>[]) new List[5];
        this.receipeKey = rs.getInt("ReceipeKey");
        this.transferKey = rs.getInt("TransferKey");
        this.houseReceipeType = EHouseReceipeType.valueOf(rs.getInt("GroupType"));
        for (int index = 0; index < this.exchangeKeys.length; ++index) {
            final String exchangeKey = rs.getString("ExchangeKey" + (index + 1));
            if (exchangeKey != null) {
                this.exchangeKeys[index] = new ArrayList<Integer>();
                for (final String key : exchangeKey.split(",")) {
                    this.exchangeKeys[index].add(Integer.parseInt(key.trim()));
                }
            }
        }
    }

    public List<Integer>[] getExchangeKeys() {
        return this.exchangeKeys;
    }

    public EHouseReceipeType getHouseReceipeType() {
        return this.houseReceipeType;
    }

    public int getTransferKey() {
        return this.transferKey;
    }

    public int getReceipeKey() {
        return this.receipeKey;
    }
}
