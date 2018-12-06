// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemImprovementSourceT {
    private int sourceItemKey;
    private int type;
    private int[] results;

    public ItemImprovementSourceT(final ResultSet rs) throws SQLException {
        this.results = new int[5];
        this.sourceItemKey = rs.getInt("SourceItemKey");
        for (int i = 0; i < this.results.length; ++i) {
            this.results[i] = rs.getInt("result" + i);
        }
    }

    public int getType() {
        return this.type;
    }

    public int getSourceItemKey() {
        return this.sourceItemKey;
    }

    public int[] getResults() {
        return this.results;
    }
}
