package com.bdoemu.gameserver.model.creature.player.rewards.templates;

public class SkillExpRewardT {
    private int exp;

    public SkillExpRewardT(final String param) {
        this.exp = Integer.parseInt(param);
    }

    public int getExp() {
        return this.exp;
    }
}
