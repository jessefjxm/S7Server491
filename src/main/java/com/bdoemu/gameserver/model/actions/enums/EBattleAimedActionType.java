package com.bdoemu.gameserver.model.actions.enums;

public enum EBattleAimedActionType {
    Normal,
    Crouch,
    Creep;

    public static EBattleAimedActionType valueof(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Found unknown ETargetBattleAimedActionType with id= " + id);
        }
        return values()[id];
    }
}