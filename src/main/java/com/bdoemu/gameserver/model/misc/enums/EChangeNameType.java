package com.bdoemu.gameserver.model.misc.enums;

public enum EChangeNameType {
    PlayerName,
    GuildName,
    FamilyName;

    public boolean isFamilyName() {
        return this == EChangeNameType.FamilyName;
    }
}