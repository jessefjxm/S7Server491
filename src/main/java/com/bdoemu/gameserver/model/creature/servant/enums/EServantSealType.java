package com.bdoemu.gameserver.model.creature.servant.enums;

public enum EServantSealType {
    NORMAL,
    KILLED,
    LOGOUT,
    TAMING;

    public boolean isNormal() {
        return this == EServantSealType.NORMAL;
    }

    public boolean isLogout() {
        return this == EServantSealType.LOGOUT;
    }

    public boolean isKilled() {
        return this == EServantSealType.KILLED;
    }

    public boolean isTaming() {
        return this == EServantSealType.TAMING;
    }
}
