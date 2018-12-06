package com.bdoemu.gameserver.model.creature.player.quests.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.player.quests.enums.EQuestRegionType;
import com.bdoemu.gameserver.model.creature.player.quests.enums.EQuestType;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestTemplate {
    private int questGroupId;
    private int repeatTime;
    private int completeNpc;
    private int completeNpcDialogIndex;
    private int questId;
    private boolean unableToGiveup;
    private boolean isEscortQuest;
    private boolean isUserBaseQuest;
    private EQuestType questType;
    private EQuestRegionType questRegionType;
    private String[] removeQuest;
    private RewardTemplate baseRewardT;
    private RewardTemplate selectRewardT;
    private IAcceptConditionHandler[][] acceptConditions;
    private List<ICompleteConditionHandler> completeConditions;
    private Integer acceptPushItem;
    private Set<Integer> contentsGroupKey;

    public QuestTemplate(final ResultSet rs) throws SQLException {
        this.contentsGroupKey = new HashSet<Integer>();
        this.questGroupId = rs.getInt("QuestGroup");
        this.questId = rs.getInt("QuestID");
        this.questType = EQuestType.valueOf(rs.getByte("QuestType"));
        this.questRegionType = EQuestRegionType.valueOf(rs.getByte("Region"));
        this.unableToGiveup = (rs.getByte("UnableToGiveup") == 1);
        this.isUserBaseQuest = (rs.getByte("isUserBaseQuest") == 1);
        this.isEscortQuest = (rs.getByte("IsEscortQuest") == 1);
        final String removeQuests = rs.getString("RemoveQuest");
        if (rs.getString("RemoveQuest") != null) {
            this.removeQuest = removeQuests.split(",");
        }
        this.repeatTime = rs.getInt("RepeatTime");
        this.baseRewardT = RewardTemplate.parse(rs.getString("BaseReward"));
        this.selectRewardT = RewardTemplate.parse(rs.getString("SelectReward"));
        this.acceptConditions = ConditionService.getAcceptConditions(rs.getString("AcceptConditions"));
        this.completeConditions = ConditionService.getCompleteConditions(rs.getString("CompleteCondition"));
        this.completeNpc = rs.getInt("CompleteNpc");
        this.completeNpcDialogIndex = rs.getInt("CompleteNpcDialogIndex");
        if (rs.getString("AcceptPushItem") != null) {
            this.acceptPushItem = rs.getInt("AcceptPushItem");
        }
        final String[] split;
        final String[] contentGroupKeyData = split = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : split) {
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        }
    }

    public boolean isUserBaseQuest() {
        return this.isUserBaseQuest;
    }

    public boolean isEscortQuest() {
        return this.isEscortQuest;
    }

    public Integer getAcceptPushItem() {
        return this.acceptPushItem;
    }

    public boolean isUnableToGiveup() {
        return this.unableToGiveup;
    }

    public List<ICompleteConditionHandler> getCompleteConditions() {
        return this.completeConditions;
    }

    public int getCompleteNpc() {
        return this.completeNpc;
    }

    public int getCompleteNpcDialogIndex() {
        return this.completeNpcDialogIndex;
    }

    public int getQuestGroupId() {
        return this.questGroupId;
    }

    public int getQuestId() {
        return this.questId;
    }

    public int getRepeatTime() {
        return this.repeatTime;
    }

    public String[] getRemoveQuest() {
        return this.removeQuest;
    }

    public EQuestType getQuestType() {
        return this.questType;
    }

    public RewardTemplate getBaseRewardT() {
        return this.baseRewardT;
    }

    public RewardTemplate getSelectRewardT() {
        return this.selectRewardT;
    }

    public IAcceptConditionHandler[][] getAcceptConditions() {
        return this.acceptConditions;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }

    public EQuestRegionType getQuestRegionType() {
        return this.questRegionType;
    }
}
