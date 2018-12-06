package com.bdoemu.gameserver.model.creature.player.rewards.templates;

public class ExpRewardT {
    private long exp;

    public ExpRewardT(final String param) {
        this.exp = Long.parseLong(param);
    }

    public long getExp() {
        return this.exp;
    }
}
