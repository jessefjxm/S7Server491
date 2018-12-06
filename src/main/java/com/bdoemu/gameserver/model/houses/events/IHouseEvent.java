// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

public interface IHouseEvent {
    void onEvent();

    boolean canAct();
}
