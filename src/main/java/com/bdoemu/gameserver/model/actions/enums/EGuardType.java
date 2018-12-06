package com.bdoemu.gameserver.model.actions.enums;

public enum EGuardType {
    None,
    SuperArmor,
    Defence,
    DefenceAndRotate,
    Avoid,
    DamageImmune;

    public boolean isAvoid() {
        return this == EGuardType.Avoid;
    }

    public boolean isDefence() {
        return this == EGuardType.Defence;
    }
}