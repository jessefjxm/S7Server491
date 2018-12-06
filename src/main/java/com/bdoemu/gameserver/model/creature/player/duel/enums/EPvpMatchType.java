package com.bdoemu.gameserver.model.creature.player.duel.enums;

public enum EPvpMatchType {
    Duel(1);

    private int id;

    EPvpMatchType(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
