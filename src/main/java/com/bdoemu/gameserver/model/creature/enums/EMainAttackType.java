package com.bdoemu.gameserver.model.creature.enums;

public enum EMainAttackType {
    DDD,
    RDD,
    MDD;

    public static EMainAttackType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EMainAttackType id: " + reqType);
        }
        return values()[reqType];
    }
}
