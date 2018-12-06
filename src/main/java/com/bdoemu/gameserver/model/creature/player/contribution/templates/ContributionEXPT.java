package com.bdoemu.gameserver.model.creature.player.contribution.templates;

public class ContributionEXPT {
    private int explorePoint;
    private int requireEXP;

    public ContributionEXPT(final int explorePoint, final int requireEXP) {
        this.explorePoint = explorePoint;
        this.requireEXP = requireEXP;
    }

    public int getExplorePoint() {
        return this.explorePoint;
    }

    public int getRequireEXP() {
        return this.requireEXP;
    }
}
