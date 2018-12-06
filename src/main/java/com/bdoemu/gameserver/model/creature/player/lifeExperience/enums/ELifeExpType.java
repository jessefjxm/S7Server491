package com.bdoemu.gameserver.model.creature.player.lifeExperience.enums;

public enum ELifeExpType {
    Gather,
    Fishing,
    Hunting,
    Cook,
    Alchemy,
    Process,
    Taming,
    Trading,
    Farming,
    Sail,
    Unused1,
    Unused2,
    Unused3,
    Unused4,
    Unused5,
    None;

    public static ELifeExpType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public boolean isNone() {
        return this == ELifeExpType.None;
    }
}
