package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.gameserver.model.creature.Creature;

import java.util.List;

public abstract class AggroInfo {
    protected Creature killer;
    protected long partyId;
    protected double hate;
    protected double dmg;
    private List<Long> winners;

    public AggroInfo() {
        this.partyId = -2097151L;
        this.hate = 1.0;
        this.winners = null;
    }

    public int getKillerSession() {
        return (this.killer != null) ? this.killer.getGameObjectId() : -1024;
    }

    public long getPartyId() {
        return this.partyId;
    }

    public Creature getKiller() {
        return this.killer;
    }

    public void setKiller(final Creature killer) {
        this.killer = killer;
    }

    public double getBalancedHate() {
        return dmg / (hate + 1);
    }

    public double getDmg() {
        return this.dmg;
    }

    public double getHate() {
        return this.hate;
    }

    public void setHate(final double hate) {
        this.hate = hate;
    }

    public void addDmg(final double dmg) {
        this.dmg += (int) dmg;
    }

    public void addHate(final double hate) {
        this.hate += hate;
    }

    public List<Long> getWinners() {
        return this.winners;
    }

    public void setWinners(final List<Long> winners) {
        this.winners = winners;
    }
}
