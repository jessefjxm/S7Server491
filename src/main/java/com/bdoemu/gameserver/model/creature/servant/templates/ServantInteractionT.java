package com.bdoemu.gameserver.model.creature.servant.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServantInteractionT {
    private int creatureId;
    private long coolTime;
    private List<Integer> buffList;

    public ServantInteractionT(final ResultSet rs) throws SQLException {
        this.buffList = new ArrayList<>();
        this.creatureId = rs.getInt("CharacterKey");
        this.coolTime = rs.getLong("CoolTime");
        for (final String split : rs.getString("BuffList").split(",")) {
            this.buffList.add(Integer.parseInt(split.trim()));
        }
    }

    public List<Integer> getBuffList() {
        return this.buffList;
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public long getCoolTime() {
        return this.coolTime;
    }
}
