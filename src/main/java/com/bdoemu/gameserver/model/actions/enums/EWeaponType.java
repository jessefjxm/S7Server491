package com.bdoemu.gameserver.model.actions.enums;

public enum EWeaponType {
    None,
    Weapon,
    AwakeningWeapon;

    public boolean isNone() {
        return this == EWeaponType.None;
    }

    public boolean isWeapon() {
        return this == EWeaponType.Weapon;
    }

    public boolean isAwakeningWeapon() {
        return this == EWeaponType.AwakeningWeapon;
    }
}