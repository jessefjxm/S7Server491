package com.bdoemu.gameserver.model.creature.player.challenge.templates;

import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeCompleteType;
import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeResetType;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChallengeT {
    private int challengeId;
    private int conditionParam1;
    private int conditionParam2;
    private Integer progressCondition;
    private EChallengeCompleteType completeType;
    private RewardTemplate baseReward;
    private RewardTemplate selectReward;
    private EChallengeResetType resetType;
    private int resetValue;

    public ChallengeT(final ResultSet rs) throws SQLException {
        this.challengeId = rs.getInt("Key");
        this.completeType = EChallengeCompleteType.valueOf(rs.getInt("CompleteConditionType"));
        this.conditionParam1 = rs.getInt("ConditionParam1");
        this.conditionParam2 = rs.getInt("ConditionParam2");
        if (rs.getString("ProgressCondition") != null) {
            this.progressCondition = rs.getInt("ProgressCondition");
        }
        this.baseReward = RewardTemplate.parse(rs.getString("BaseReward"));
        this.selectReward = RewardTemplate.parse(rs.getString("SelectReward"));
        this.resetType = EChallengeResetType.values()[rs.getInt("ResetType")];
        this.resetValue = rs.getInt("ResetValue");
    }

    public RewardTemplate getBaseRewardT() {
        return this.baseReward;
    }

    public RewardTemplate getSelectRewardT() {
        return this.selectReward;
    }

    public Integer getProgressCondition() {
        return this.progressCondition;
    }

    public EChallengeCompleteType getCompleteType() {
        return this.completeType;
    }

    public int getChallengeId() {
        return this.challengeId;
    }

    public int getConditionParam1() {
        return this.conditionParam1;
    }

    public int getConditionParam2() {
        return this.conditionParam2;
    }

    public EChallengeResetType getResetType() {
        return this.resetType;
    }

    public int getResetValue() {
        return this.resetValue;
    }
}
