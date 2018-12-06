package com.bdoemu.gameserver.model.actions.enums;

public enum EAttackAbsorbType {
    HP(0),
    MP(1),
    SubResourcePoint(3),
    None(4);

    private int id;

    EAttackAbsorbType(final int id) {
        this.id = id;
    }

    public static EAttackAbsorbType valueOf(final int id) {
        for (final EAttackAbsorbType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Found unknown EAttackAbsorbType with id= " + id);
    }

    public int getId() {
        return this.id;
    }
}