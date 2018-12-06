package com.bdoemu.gameserver.model.misc.enums;

public enum EQuickSlotType {
    Empty(0),
    Item(1),
    Skill(2),
    CashItem(3);

    private byte id;

    EQuickSlotType(final int id) {
        this.id = (byte) id;
    }

    public static EQuickSlotType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}