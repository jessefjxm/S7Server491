package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeState;

public class OnlineChallenge extends AChallenge {
    public synchronized boolean check() {
        if (this.template.getProgressCondition() != null) {
            return false;
        }
        if (this.completeCount > 0) {
            this.state = EChallengeState.Complete;
            return false;
        }
        if (this.state.isComplete()) {
            return false;
        }
        if (this.template.getConditionParam1() == 1) {
            final int hours = (int) (this.player.getPlayedTime() / 1000L / 60L / 60L);
            if (hours >= this.template.getConditionParam2()) {
                ++this.completeCount;
                this.state = EChallengeState.Complete;
                this.handler.setReward(this.template.getChallengeId());
                return true;
            }
        } else if (this.template.getConditionParam1() == 0) {
            final int hours = (int) (this.handler.getAccountData().getPlayedTime() / 1000L / 60L / 60L);
            if (hours >= this.template.getConditionParam2()) {
                ++this.completeCount;
                this.state = EChallengeState.Complete;
                this.handler.setReward(this.template.getChallengeId());
                return true;
            }
        }
        this.state = EChallengeState.Progress;
        return false;
    }

    @Override
    protected void init() {
        this.check();
    }
}
