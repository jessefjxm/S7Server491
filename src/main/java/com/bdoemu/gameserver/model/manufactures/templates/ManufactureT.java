package com.bdoemu.gameserver.model.manufactures.templates;

import com.bdoemu.gameserver.model.manufactures.ManufactureItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ManufactureT {
    private int resultDropGroup;
    private int successPercent;
    private HashMap<Integer, ManufactureItem> items;

    public ManufactureT(final ResultSet rs) throws SQLException {
        this.items = new HashMap<>();
        for (int i = 1; i <= 5; ++i) {
            if (rs.getString("MaterialItem" + i) != null) {
                final int itemId = rs.getInt("MaterialItem" + i);
                final long count = rs.getLong("MaterialItemCount" + i);
                this.items.put(itemId, new ManufactureItem(itemId, count));
            }
        }
        this.resultDropGroup = rs.getInt("ResultDropGroup");
        this.successPercent = rs.getInt("SuccessPercent");
    }

    public HashMap<Integer, ManufactureItem> getItems() {
        return this.items;
    }

    public int getResultDropGroup() {
        return this.resultDropGroup;
    }

    public int getSuccessPercent() {
        return this.successPercent;
    }
}
