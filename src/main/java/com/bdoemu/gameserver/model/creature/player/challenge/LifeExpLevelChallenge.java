package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.core.network.sendable.SMCompleteChallengeReward;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeState;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;

public class LifeExpLevelChallenge extends AChallenge {
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
        final ELifeExpType lifeExpType = ELifeExpType.valueOf(this.template.getConditionParam1());
        if (this.player.getLifeExperienceStorage().getLifeExperience(lifeExpType).getLevel() >= this.template.getConditionParam2()) {
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
            final ELifeExpType lifeExpType = ELifeExpType.valueOf(this.template.getConditionParam1());
            final Observer observer = new Observer(EObserveType.lifeExp, lifeExpType.ordinal()) {
                @Override
                public void notify(final EObserveType type, final Object... params) {
                    if (LifeExpLevelChallenge.this.check()) {
                        LifeExpLevelChallenge.this.player.getObserveController().removeObserver(this);
                        LifeExpLevelChallenge.this.player.sendPacket(new SMCompleteChallengeReward(LifeExpLevelChallenge.this));
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
