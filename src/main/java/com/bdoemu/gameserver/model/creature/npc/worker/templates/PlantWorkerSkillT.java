package com.bdoemu.gameserver.model.creature.npc.worker.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nullbyte
 */
public class PlantWorkerSkillT {
    private int _characterKey;
    private int _workingEfficiency;

    public PlantWorkerSkillT(final ResultSet rs) throws SQLException {
        _characterKey = rs.getInt("CharacterKey");
        _workingEfficiency = rs.getInt("WorkingEfficiency");
    }

    public int getCharacterKey() {
        return _characterKey;
    }

    public int getWorkingEfficiency() {
        return _workingEfficiency;
    }
}