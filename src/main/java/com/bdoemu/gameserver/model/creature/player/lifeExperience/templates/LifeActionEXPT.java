package com.bdoemu.gameserver.model.creature.player.lifeExperience.templates;

import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LifeActionEXPT {
    private int itemId;
    private int exp;
    private int exp1;
    private ELifeExpType type;
    private ELifeExpType type1;

    public LifeActionEXPT(final ResultSet rs) throws SQLException {
        this.itemId = rs.getInt("ItemKey");
        this.type = ELifeExpType.valueOf(rs.getInt("LifeActionType0"));
        this.exp = rs.getInt("exp0");
        if (rs.getString("LifeActionType1") != null) {
            this.type1 = ELifeExpType.valueOf(rs.getInt("LifeActionType1"));
            this.exp1 = rs.getInt("exp1");
        }
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getExp() {
        return this.exp;
    }

    public int getExp1() {
        return this.exp1;
    }

    public ELifeExpType getType1() {
        return this.type1;
    }

    public ELifeExpType getType() {
        return this.type;
    }
}
