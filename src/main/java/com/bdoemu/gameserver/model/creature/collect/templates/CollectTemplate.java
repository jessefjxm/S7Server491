package com.bdoemu.gameserver.model.creature.collect.templates;

import com.bdoemu.gameserver.model.creature.collect.enums.ECollectToolType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

public class CollectTemplate {
    private int collectId;
    private int exp;
    private int spawnDelayTime;
    private int spawnVariableTime;
    private int itemDropId;
    private EnumMap<ECollectToolType, Integer> dropsByToolTypes;

    public CollectTemplate(final ResultSet rs) throws SQLException {
        this.dropsByToolTypes = new EnumMap<>(ECollectToolType.class);
        this.collectId = rs.getInt("Index");
        this.exp = rs.getInt("Exp");
        this.itemDropId = rs.getInt("ItemDropID");
        for (int i = 0; i <= 2; ++i) {
            if (rs.getString("ToolType_" + i) != null) {
                this.dropsByToolTypes.put(ECollectToolType.valueOf(rs.getInt("ToolType_" + i)), Integer.valueOf(rs.getInt("ItemDropID_" + i)));
            }
        }
        this.spawnDelayTime = rs.getInt("SpawnDelayTime");
        this.spawnVariableTime = rs.getInt("SpawnVariableTime");
    }

    public int getExp() {
        return this.exp;
    }

    public int getCollectId() {
        return this.collectId;
    }

    public int getItemDropId() {
        return this.itemDropId;
    }

    public int getSpawnVariableTime() {
        return this.spawnVariableTime;
    }

    public int getSpawnDelayTime() {
        return this.spawnDelayTime;
    }

    public Map<ECollectToolType, Integer> getDropsByToolTypes() {
        return this.dropsByToolTypes;
    }
}
