package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.core.network.sendable.SMCompleteChallengeReward;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeState;

public class LevelUpChallenge extends AChallenge {
    public synchronized boolean check() {
        if (this.template.getProgressCondition() != null) {
            return false;
        }
        if (this.state.isComplete()) {
            return false;
        }
        if (this.completeCount > 0) {
            this.state = EChallengeState.Complete;
            return false;
        }
        if (this.player.getLevel() >= this.template.getConditionParam1()) {
            ++this.completeCount;
            this.state = EChallengeState.Complete;
            this.handler.setReward(this.template.getChallengeId());
            return true;
        }
        this.state = EChallengeState.Progress;
        return false;
    }

    private void regObserver() {
        if (!this.state.isComplete()) {
            final Observer observer = new Observer(EObserveType.levelUp, this.template.getConditionParam1()) {
                @Override
                public void notify(final EObserveType type, final Object... params) {
                    if (LevelUpChallenge.this.check()) {
                        LevelUpChallenge.this.player.getObserveController().removeObserver(this);
                        LevelUpChallenge.this.player.sendPacket(new SMCompleteChallengeReward(LevelUpChallenge.this));
                    }
                }
            };
            this.player.getObserveController().putObserver(observer);
        }
    }

    @Override
    protected void init() {
        this.check();
        if (!this.state.isComplete()) {
            this.regObserver();
        }
    }
}
