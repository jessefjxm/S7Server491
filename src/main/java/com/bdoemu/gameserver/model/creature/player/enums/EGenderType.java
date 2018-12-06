package com.bdoemu.gameserver.model.creature.player.enums;

public enum EGenderType {
    Man,
    Woman;

    public boolean isWoman() {
        return this == EGenderType.Woman;
    }
}
