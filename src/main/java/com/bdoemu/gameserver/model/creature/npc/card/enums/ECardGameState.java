// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.enums;

public enum ECardGameState {
    SelectCards(1),
    TryCards(2),
    ReadyToReward(2),
    Rewarded(2);

    private byte id;

    private ECardGameState(final int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

    public boolean isSelectCards() {
        return this == ECardGameState.SelectCards;
    }

    public boolean isTryCards() {
        return this == ECardGameState.TryCards;
    }

    public boolean isReadyToReward() {
        return this == ECardGameState.ReadyToReward;
    }
}
