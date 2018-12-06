package com.bdoemu.gameserver.model.actions.enums;

public enum EApplySpeedBuffType {
    Move,
    Attack,
    Cast,
    None;

    public static EApplySpeedBuffType valueof(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Found unknown EApplySpeedBuffType with id= " + id);
        }
        return values()[id];
    }

    public boolean isMove() {
        return this == EApplySpeedBuffType.Move;
    }
}