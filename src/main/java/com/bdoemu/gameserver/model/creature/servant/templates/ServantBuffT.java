package com.bdoemu.gameserver.model.creature.servant.templates;

import com.bdoemu.commons.utils.HashUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantBuffT {
    private int index;
    private int skillId;
    private long actionHash;
    private long servantActionHash;

    public ServantBuffT(final ResultSet rs) throws SQLException {
        this.index = rs.getInt("Index");
        this.skillId = rs.getInt("SkillNo");
        this.actionHash = HashUtil.generateHashA(rs.getString("ActionName"));
        this.servantActionHash = HashUtil.generateHashA(rs.getString("ServantActionName"));
    }

    public long getActionHash() {
        return this.actionHash;
    }

    public long getServantActionHash() {
        return this.servantActionHash;
    }

    public int getIndex() {
        return this.index;
    }

    public int getSkillId() {
        return this.skillId;
    }
}
