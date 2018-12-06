// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.events;

public interface IGuildEvent {
    void onEvent();

    boolean canAct();
}
