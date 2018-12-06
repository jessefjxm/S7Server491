package com.bdoemu.gameserver.model.creature.npc.worker.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantWorkerPassiveSkillT {
    private int skillId;
    private int skillPrice;
    private int buffType;
    private int buffValue0;
    private int buffValue1;
    private int buffValue2;

    public PlantWorkerPassiveSkillT(final ResultSet rs) throws SQLException {
        this.skillId = rs.getInt("Key");
        this.skillPrice = rs.getInt("Skill_Price");
        this.buffType = rs.getInt("BuffType0");
        this.buffValue0 = rs.getInt("Type0_BuffValue0");
        this.buffValue1 = rs.getInt("Type0_BuffValue1");
        this.buffValue2 = rs.getInt("Type0_BuffValue2");
    }

    public int getSkillId() {
        return this.skillId;
    }

    public int getSkillPrice() {
        return this.skillPrice;
    }

    public int getBuffType() {
        return buffType;
    }

    public int getBuffValue0() {
        return buffValue0;
    }

    public int getBuffValue1() {
        return buffValue1;
    }

    public int getBuffValue2() {
        return buffValue2;
    }
}
