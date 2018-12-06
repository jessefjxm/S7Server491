package com.bdoemu.gameserver.model.creature.servant.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantFormT {
    private int index;
    private int type;
    private int actionIndex;
    private int tier;

    public ServantFormT(final ResultSet resultSet) throws SQLException {
        this.index = resultSet.getInt("Index");
        this.type = resultSet.getInt("Type");
        this.actionIndex = resultSet.getInt("ActionIndex");
        this.tier = resultSet.getInt("Tier");
    }

    public int getIndex() {
        return this.index;
    }

    public int getType() {
        return this.type;
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    public int getTier() {
        return this.tier;
    }
}
