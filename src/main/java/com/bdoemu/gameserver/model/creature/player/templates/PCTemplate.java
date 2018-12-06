package com.bdoemu.gameserver.model.creature.player.templates;

import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.PCSetData;
import com.bdoemu.gameserver.model.stats.templates.PlayerStatsTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PCTemplate {
    private int playerClass;
    private int level;
    private double requireExp;
    private double requireExpLimit;
    private PlayerStatsTemplate gameStatsTemplate;

    public PCTemplate(final ResultSet rs) throws SQLException {
        this.playerClass = rs.getInt("Class");
        this.level = rs.getInt("Level");
        this.requireExp = rs.getFloat("RequireEXP");
        this.requireExpLimit = rs.getFloat("RequireExpLimit");
        this.gameStatsTemplate = new PlayerStatsTemplate(rs, CreatureData.getInstance().getTemplate(PCSetData.getInstance().getTemplate(this.playerClass).getCharacterKey()));
    }

    public PlayerStatsTemplate getGameStatsTemplate() {
        return this.gameStatsTemplate;
    }

    public double getRequireExp() {
        return this.requireExp;
    }

    public double getRequireExpLimit() {
        return this.requireExpLimit;
    }

    public int getPlayerClass() {
        return this.playerClass;
    }

    public int getLevel() {
        return this.level;
    }
}
