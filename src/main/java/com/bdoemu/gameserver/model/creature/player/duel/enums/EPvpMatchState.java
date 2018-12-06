package com.bdoemu.gameserver.model.creature.player.duel.enums;

public enum EPvpMatchState {
    Duel(0),
    Unk1(1),
    Normal(2);

    private int id;

    EPvpMatchState(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
