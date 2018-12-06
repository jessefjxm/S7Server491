// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

public interface ICardEvent {
    void onEvent();

    boolean canAct();
}
