package com.bdoemu.gameserver.model.creature.servant.model;

import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.stats.templates.ServantStatsTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantTemplate {
    private int id;
    private int level;
    private long requireExp;
    private ServantStatsTemplate gameStatsTemplate;

    public ServantTemplate(final ResultSet rs) throws SQLException {
        this.id = rs.getInt("CharacterKey");
        final CreatureTemplate template = CreatureData.getInstance().getTemplate(this.id);
        if (template != null) {
            this.gameStatsTemplate = new ServantStatsTemplate(rs, CreatureData.getInstance().getTemplate(this.id));
        }
        this.level = rs.getInt("Level");
        this.requireExp = rs.getLong("RequireEXP");
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public long getRequireExp() {
        return this.requireExp;
    }

    public int getId() {
        return this.id;
    }

    public ServantStatsTemplate getGameStatsTemplate() {
        return this.gameStatsTemplate;
    }
}
