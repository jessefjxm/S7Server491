// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.events;

public interface INpcWorkerEvent {
    void onEvent();

    boolean canAct();
}
