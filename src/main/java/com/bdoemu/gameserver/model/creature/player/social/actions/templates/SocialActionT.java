package com.bdoemu.gameserver.model.creature.player.social.actions.templates;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SocialActionT {
    private int index;
    private long actionHash;
    private IAcceptConditionHandler[][] conditions;

    public SocialActionT(final ResultSet rs) throws SQLException {
        this.index = rs.getInt("Index");
        this.actionHash = HashUtil.generateHashA(rs.getString("ActionName"));
        this.conditions = ConditionService.getAcceptConditions(rs.getString("UseCondition"));
    }

    public int getIndex() {
        return this.index;
    }

    public long getActionHash() {
        return this.actionHash;
    }

    public IAcceptConditionHandler[][] getConditions() {
        return this.conditions;
    }
}
