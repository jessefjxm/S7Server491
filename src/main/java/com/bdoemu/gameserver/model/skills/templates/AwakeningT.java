// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AwakeningT {
    private int skillId;
    private List<AwakeningAbilityT> abilityList;

    public AwakeningT(final ResultSet rs, final List<AwakeningAbilityT> abilityList) throws SQLException {
        this.skillId = rs.getInt("SkillNo");
        this.abilityList = abilityList;
    }

    public List<AwakeningAbilityT> getAbilityList() {
        return this.abilityList;
    }

    public int getSkillId() {
        return this.skillId;
    }
}
