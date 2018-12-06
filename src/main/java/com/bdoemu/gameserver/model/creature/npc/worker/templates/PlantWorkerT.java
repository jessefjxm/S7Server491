// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantWorkerT {
    private int characterKey;
    private int actionPoint;
    private int luck;
    private int luckAddRateMin;
    private int luckAddRateMax;
    private int workingEfficiencyAddRateMin;
    private int workingEfficiencyAddRateMax;
    private int movementSped;
    private int[] experiences;
    private int[] upgrades;
    private int[] sellPrices;
    private boolean isSellable;
    private Integer upgradeCharacterKey;
    private Integer defaultSkillKey;

    public PlantWorkerT(final ResultSet rs) throws SQLException {
        this.experiences = new int[31];
        this.upgrades = new int[31];
        this.characterKey = rs.getInt("CharacterKey");
        this.actionPoint = rs.getInt("ActionPoint");
        this.luck = rs.getInt("Luck");
        this.luckAddRateMin = rs.getInt("LuckAddRateMin");
        this.luckAddRateMax = rs.getInt("LuckAddRateMax");
        this.workingEfficiencyAddRateMin = rs.getInt("WorkingEfficiencyAddRateMin");
        this.workingEfficiencyAddRateMax = rs.getInt("WorkingEfficiencyAddRateMax");
        this.movementSped = rs.getInt("MoveSpeed");
        if (rs.getString("DefaultSkillKey") != null) {
            this.defaultSkillKey = rs.getInt("DefaultSkillKey");
        }
        for (int i = 0; i < this.experiences.length; ++i) {
            this.experiences[i] = rs.getInt("Lv" + i);
        }
        for (int i = 0; i < this.upgrades.length; ++i) {
            this.upgrades[i] = rs.getInt("Upgrade" + i);
        }
        this.isSellable = (rs.getByte("IsSellable") == 1);
        if (this.isSellable) {
            this.sellPrices = new int[31];
            for (int i = 0; i < this.sellPrices.length; ++i) {
                this.sellPrices[i] = rs.getInt("SellPrice" + i);
            }
        }
        if (rs.getString("UpgradeCharacterKey") != null) {
            this.upgradeCharacterKey = rs.getInt("UpgradeCharacterKey");
        }
    }

    public int getWorkingEfficiencyAddRateMin() {
        return workingEfficiencyAddRateMin > 0 ? workingEfficiencyAddRateMin : 1;
    }

    public int getWorkingEfficiencyAddRateMax() {
        return workingEfficiencyAddRateMax > 0 ? workingEfficiencyAddRateMax : 1;
    }

    public int getMovementSpeed() {
        return movementSped;
    }

    public Integer getUpgradeCharacterKey() {
        return this.upgradeCharacterKey;
    }

    public Integer getDefaultSkillKey() {
        return this.defaultSkillKey;
    }

    public int getUgradeChance(final int level) {
        return this.upgrades[level];
    }

    public int getMaxExp(final int level) {
        return this.experiences[level];
    }

    public boolean isSellable() {
        return this.isSellable;
    }

    public int[] getSellPrices() {
        return this.sellPrices;
    }

    public int[] getExperiences() {
        return this.experiences;
    }

    public int getLuckAddRateMin() {
        return this.luckAddRateMin;
    }

    public int getLuckAddRateMax() {
        return this.luckAddRateMax;
    }

    public int getLuck() {
        return this.luck;
    }

    public int getActionPoint() {
        return this.actionPoint;
    }

    public int getCharacterKey() {
        return this.characterKey;
    }
}
