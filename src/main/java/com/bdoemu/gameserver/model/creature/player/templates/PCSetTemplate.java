package com.bdoemu.gameserver.model.creature.player.templates;

import com.bdoemu.gameserver.model.creature.enums.EMainAttackType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.world.Location;
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PCSetTemplate {
    private int playerClassId;
    private int characterKey;
    private int startWayPointNo;
    private SpawnPlacementT spawnPlacement;
    private List<Integer> defaultItemList;
    private EMainAttackType mainAttackType;

    public PCSetTemplate(final ResultSet rs) throws SQLException {
        this.playerClassId = rs.getInt("ClassType");
        final String startPos = rs.getString("StartPosition");
        final String itItemList = rs.getString("DefaultItemList");
        this.characterKey = rs.getInt("CharacterKey");
        this.startWayPointNo = rs.getInt("StartWayPointNo");
        if (startPos != null && itItemList != null) {
            final String[] positionData = startPos.split(",");
            if (positionData.length == 3) {
                final double startAngle = Math.toRadians(rs.getInt("StartDirection"));
                final double x = Double.parseDouble(positionData[0].trim());
                final double y = Double.parseDouble(positionData[2].trim());
                final double z = Double.parseDouble(positionData[1].trim());
                this.spawnPlacement = new SpawnPlacementT(new Location(x, y, z, Math.cos(startAngle), Math.sin(startAngle)));
            }
            this.defaultItemList = new ArrayList<>();
            for (final String item : itItemList.split(",")) {
                if (NumberUtils.isCreatable(item.trim())) {
                    this.defaultItemList.add(Integer.parseInt(item.trim()));
                }
            }
        }
        this.mainAttackType = EMainAttackType.valueOf(rs.getInt("MainAttackType"));
    }

    public EMainAttackType getMainAttackType() {
        return this.mainAttackType;
    }

    public int getStartWayPointNo() {
        return this.startWayPointNo;
    }

    public SpawnPlacementT getSpawnPlacement() {
        return this.spawnPlacement;
    }

    public int getPlayerClassId() {
        return this.playerClassId;
    }

    public EClassType getPlayerClass() {
        return EClassType.valueOf(this.playerClassId);
    }

    public List<Integer> getDefaultItemList() {
        return this.defaultItemList;
    }

    public int getCharacterKey() {
        return this.characterKey;
    }
}
