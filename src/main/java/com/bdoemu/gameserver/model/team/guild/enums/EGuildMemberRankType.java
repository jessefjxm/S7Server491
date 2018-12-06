// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.enums;

public enum EGuildMemberRankType {
    Master,
    Officer,
    General,
    Quartermaster,
    None;

    public boolean isMaster() {
        return this == EGuildMemberRankType.Master;
    }

    public boolean isOfficer() {
        return this == EGuildMemberRankType.Officer;
    }
}
