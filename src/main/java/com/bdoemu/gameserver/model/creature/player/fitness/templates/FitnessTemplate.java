package com.bdoemu.gameserver.model.creature.player.fitness.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FitnessTemplate {
    private int level;
    private int needExp;
    private int addStamina;
    private int addWeight;
    private int addHp;
    private int addMp;

    public FitnessTemplate(final ResultSet rs) throws SQLException {
        this.level = rs.getInt("Level");
        this.needExp = rs.getInt("NeedExp");
        this.addStamina = rs.getInt("AddStamina");
        this.addWeight = rs.getInt("AddWeight");
        this.addHp = rs.getInt("AddHp");
        this.addMp = rs.getInt("AddMp");
    }

    public int getAddWeight() {
        return this.addWeight;
    }

    public int getAddMp() {
        return this.addMp;
    }

    public int getAddHp() {
        return this.addHp;
    }

    public int getAddStamina() {
        return this.addStamina;
    }

    public int getNeedExp() {
        return this.needExp;
    }

    public int getLevel() {
        return this.level;
    }
}
