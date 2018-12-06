package com.bdoemu.gameserver.model.actions.enums;

public enum ERotateType {
    SelfDir,
    ToTarget,
    ToSelfPlayer,
    Original,
    Unk;

    public static ERotateType valueof(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Unknow ERotateType:" + id);
        }
        return values()[id];
    }
}