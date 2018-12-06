package com.bdoemu.gameserver.model.creature.player.Knowledge.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KnowledgeLearningT {
    private int creatureId;
    private int selectRate;
    private int knowledgeIndex;

    public KnowledgeLearningT(final ResultSet rs) throws SQLException {
        this.creatureId = rs.getInt("Key");
        this.selectRate = rs.getInt("SelectRate");
        this.knowledgeIndex = rs.getInt("KnowledgeIndex");
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public int getKnowledgeIndex() {
        return this.knowledgeIndex;
    }

    public int getSelectRate() {
        return this.selectRate;
    }
}
