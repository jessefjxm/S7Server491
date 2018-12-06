// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.functions.FunctionService;
import com.bdoemu.gameserver.model.functions.IFunctionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NpcRelationT {
    private int npcId;
    private List<IFunctionHandler> discoverFunction;
    private IAcceptConditionHandler[][] discoverCondition1;
    private IAcceptConditionHandler[][] discoverCondition2;
    private IAcceptConditionHandler[][] discoverCondition3;
    private IAcceptConditionHandler[][] discoverCondition4;
    private IAcceptConditionHandler[][] discoverCondition5;

    public NpcRelationT(final ResultSet rs) throws SQLException {
        this.npcId = rs.getInt("NpcKey");
        this.discoverFunction = FunctionService.getFunctions(0, rs.getString("DiscoverFunction"));
        this.discoverCondition1 = ConditionService.getAcceptConditions(rs.getString("DiscoverCondition1"));
        this.discoverCondition2 = ConditionService.getAcceptConditions(rs.getString("DiscoverCondition2"));
        this.discoverCondition3 = ConditionService.getAcceptConditions(rs.getString("DiscoverCondition3"));
        this.discoverCondition4 = ConditionService.getAcceptConditions(rs.getString("DiscoverCondition4"));
        this.discoverCondition5 = ConditionService.getAcceptConditions(rs.getString("DiscoverCondition5"));
    }

    public IAcceptConditionHandler[][] getDiscoverCondition5() {
        return this.discoverCondition5;
    }

    public IAcceptConditionHandler[][] getDiscoverCondition4() {
        return this.discoverCondition4;
    }

    public IAcceptConditionHandler[][] getDiscoverCondition3() {
        return this.discoverCondition3;
    }

    public IAcceptConditionHandler[][] getDiscoverCondition2() {
        return this.discoverCondition2;
    }

    public IAcceptConditionHandler[][] getDiscoverCondition1() {
        return this.discoverCondition1;
    }

    public List<IFunctionHandler> getFunctionHandler() {
        return this.discoverFunction;
    }

    public int getNpcId() {
        return this.npcId;
    }

    public int getDiscoveredDialog(final Player player) {
        if (this.getDiscoverCondition1() == null) {
            return -1;
        }
        if (!ConditionService.checkCondition(this.getDiscoverCondition1(), player)) {
            if (this.getDiscoverCondition2() != null) {
                if (ConditionService.checkCondition(this.getDiscoverCondition2(), player)) {
                    return 1;
                }
                if (this.getDiscoverCondition3() != null) {
                    if (ConditionService.checkCondition(this.getDiscoverCondition3(), player)) {
                        return 2;
                    }
                    if (this.getDiscoverCondition4() != null) {
                        if (ConditionService.checkCondition(this.getDiscoverCondition4(), player)) {
                            return 3;
                        }
                        if (this.getDiscoverCondition5() != null && ConditionService.checkCondition(this.getDiscoverCondition5(), player)) {
                            return 4;
                        }
                    }
                }
            }
            return -2;
        }
        return 0;
    }
}
