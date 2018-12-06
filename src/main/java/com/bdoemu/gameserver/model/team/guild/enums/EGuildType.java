// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.enums;

public enum EGuildType {
    Clan,
    Guild;

    public boolean isClan() {
        return this == EGuildType.Clan;
    }

    public boolean isGuild() {
        return this == EGuildType.Guild;
    }
}
