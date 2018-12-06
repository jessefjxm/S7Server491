package com.bdoemu.gameserver.model.creature.player.challenge.enums;

public enum EChallengeState {
    None,
    Progress,
    Complete;

    public boolean isProgress() {
        return this == EChallengeState.Progress;
    }

    public boolean isComplete() {
        return this == EChallengeState.Complete;
    }
}
