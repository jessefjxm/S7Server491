// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AwakeningAbilityT {
    private int abilityId;

    public AwakeningAbilityT(final ResultSet rs) throws SQLException {
        this.abilityId = rs.getInt("Index");
    }

    public int getAbilityId() {
        return this.abilityId;
    }
}
