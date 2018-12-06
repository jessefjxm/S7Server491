package com.bdoemu.gameserver.model.alchemy.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialT {
    private int itemKey;
    private int materialNo;
    private int materialQuality;

    public MaterialT(final ResultSet rs) throws SQLException {
        this.itemKey = rs.getInt("ItemKey");
        this.materialNo = rs.getInt("MaterialNo");
        this.materialQuality = rs.getInt("MaterialQuality");
    }

    public int getItemKey() {
        return this.itemKey;
    }

    public int getMaterialNo() {
        return this.materialNo;
    }

    public int getMaterialQuality() {
        return this.materialQuality;
    }
}
