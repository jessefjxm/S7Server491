// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.guildquests.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuildQuestT {
    private int guildQuestNr;
    private int questLvl;
    private int activityPerDemand;
    private int limitMinute;
    private int needItemKey;
    private long needItemCount;
    private RewardTemplate rewardT;
    private List<ICompleteConditionHandler> completeConditions;
    private Set<Integer> contentsGroupKey;
    private boolean preoccupancy;

    public GuildQuestT(final ResultSet rs) throws SQLException {
        this.contentsGroupKey = new HashSet<Integer>();
        this.guildQuestNr = rs.getInt("GuildQuestNo");
        this.questLvl = rs.getInt("QuestLv");
        this.rewardT = RewardTemplate.parse(rs.getString("Reward"));
        this.completeConditions = ConditionService.getCompleteConditions(rs.getString("CompleteCondition"));
        this.activityPerDemand = rs.getInt("ActivityPerDemand");
        this.limitMinute = rs.getInt("LimitMinute");
        this.needItemKey = rs.getInt("NeedItemKey");
        this.needItemCount = rs.getLong("NeeItemCount");
        this.preoccupancy = (rs.getInt("Preoccupancy") == 1);
        final String[] split;
        final String[] contentGroupKeyData = split = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : split) {
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        }
    }

    public boolean isPreoccupancy() {
        return this.preoccupancy;
    }

    public RewardTemplate getRewardT() {
        return this.rewardT;
    }

    public List<ICompleteConditionHandler> getCompleteConditions() {
        return this.completeConditions;
    }

    public int getGuildQuestNr() {
        return this.guildQuestNr;
    }

    public int getQuestLevel() {
        return this.questLvl;
    }

    public int getNeedItemKey() {
        return this.needItemKey;
    }

    public long getNeedItemCount() {
        return this.needItemCount;
    }

    public int getQuestLvl() {
        return this.questLvl;
    }

    public int getLimitMinute() {
        return this.limitMinute;
    }

    public int getActivityPerDemand() {
        return this.activityPerDemand;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }
}
