package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeState;
import com.bdoemu.gameserver.service.GameTimeService;

public class EveryDayChallenge extends AChallenge {
    public synchronized boolean check() {
        if (this.template.getProgressCondition() != null) {
            return false;
        }
        this.state = EChallengeState.Progress;
        if (this.completeCount > 0) {
            this.state = EChallengeState.Complete;
        }
        if (this.lastRewardedDate == 0L) {
            this.lastRewardedDate = GameTimeService.getServerTimeInMillis();
        } else if (GameTimeService.getServerTimeInMillis() - this.lastRewardedDate >= 86400000L) {
            ++this.completeCount;
            this.state = EChallengeState.Complete;
            this.handler.setReward(this.template.getChallengeId());
            this.lastRewardedDate = GameTimeService.getServerTimeInMillis();
            return true;
        }
        return false;
    }

    @Override
    protected void init() {
        this.check();
    }
}
