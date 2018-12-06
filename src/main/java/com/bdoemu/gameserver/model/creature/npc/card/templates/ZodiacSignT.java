// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZodiacSignT {
    private int zodiacSignKey;
    private Integer[] steps;

    public ZodiacSignT(final ResultSet rs) throws SQLException {
        this.steps = new Integer[20];
        this.zodiacSignKey = rs.getInt("ZodiacSignIndexKey");
        for (int index = 0; index < 20; ++index) {
            if (rs.getString("Index" + index) != null) {
                this.steps[index] = rs.getInt("Index" + index);
            }
        }
    }

    public Integer[] getSteps() {
        return this.steps;
    }

    public int getZodiacSignKey() {
        return this.zodiacSignKey;
    }
}
