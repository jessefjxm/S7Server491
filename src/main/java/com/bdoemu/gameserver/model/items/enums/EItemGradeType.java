// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EItemGradeType {
    white(0),
    green(1),
    blue(2),
    yellow(3),
    orange(4);

    private byte id;

    private EItemGradeType(final int id) {
        this.id = (byte) id;
    }

    public static EItemGradeType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }

    public boolean isWhite() {
        return this == EItemGradeType.white;
    }
}
