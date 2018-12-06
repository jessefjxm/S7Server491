package com.bdoemu.gameserver.model.creature.player.rewards.templates;

public class IntimacyRewardT {
    private int npcId;
    private int intimacy;

    public IntimacyRewardT(final String[] params) {
        this.npcId = Integer.parseInt(params[0]);
        this.intimacy = Integer.parseInt(params[1].trim());
    }

    public int getIntimacy() {
        return this.intimacy;
    }

    public int getNpcId() {
        return this.npcId;
    }
}
