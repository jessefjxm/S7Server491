// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalityT {
    private int npcId;
    private int importance;
    private int zodiacIndex;
    private int theme1;
    private int theme2;
    private int theme3;
    private int count1;
    private int count2;
    private int count3;
    private int minPv;
    private int maxPv;
    private int minDv;
    private int maxDv;

    public PersonalityT(final ResultSet rs) throws SQLException {
        this.npcId = rs.getInt("Npc");
        this.importance = rs.getInt("NPC_Importance");
        this.theme1 = rs.getInt("Theme1");
        this.theme2 = rs.getInt("Theme2");
        this.theme3 = rs.getInt("Theme3");
        this.count1 = rs.getInt("NeedCount1");
        this.count2 = rs.getInt("NeedCount2");
        this.count3 = rs.getInt("NeedCount3");
        this.minPv = rs.getInt("MinPv");
        this.maxPv = rs.getInt("MaxPv");
        this.minDv = rs.getInt("MinDv");
        this.maxDv = rs.getInt("MaxDv");
        this.zodiacIndex = rs.getInt("ZodiacSignIndexKey");
    }

    public int getMinPv() {
        return this.minPv;
    }

    public int getMinDv() {
        return this.minDv;
    }

    public int getMaxPv() {
        return this.maxPv;
    }

    public int getMaxDv() {
        return this.maxDv;
    }

    public int getZodiacIndex() {
        return this.zodiacIndex;
    }

    public int getCount1() {
        return this.count1;
    }

    public int getCount2() {
        return this.count2;
    }

    public int getCount3() {
        return this.count3;
    }

    public int getTheme1() {
        return this.theme1;
    }

    public int getTheme2() {
        return this.theme2;
    }

    public int getTheme3() {
        return this.theme3;
    }

    public int getImportance() {
        return this.importance;
    }

    public int getNpcId() {
        return this.npcId;
    }
}
