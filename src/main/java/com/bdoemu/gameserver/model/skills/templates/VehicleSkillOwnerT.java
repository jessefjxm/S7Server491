// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleSkillOwnerT {
    private final int[] ables;
    private final int[] addExp;
    private final int[] isLearn;
    private final int creatureId;

    public VehicleSkillOwnerT(final ResultSet rs) throws SQLException {
        this.ables = new int[42];
        this.addExp = new int[42];
        this.isLearn = new int[42];
        this.creatureId = rs.getInt("CharacterKey");
        for (int i = 0; i < this.ables.length; ++i) {
            this.ables[i] = rs.getInt("Able_" + (i + 1));
        }
        for (int i = 0; i < this.addExp.length; ++i) {
            this.addExp[i] = rs.getInt("AddExp_" + (i + 1));
        }
        for (int i = 0; i < this.isLearn.length; ++i) {
            this.isLearn[i] = rs.getInt("IsLearn_" + (i + 1));
        }
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public int[] getAbles() {
        return this.ables;
    }

    public int[] getAddExp() {
        return this.addExp;
    }

    public int[] getIsLearn() {
        return this.isLearn;
    }
}
