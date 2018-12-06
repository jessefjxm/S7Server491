// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EItemVestedType {
    None(0),
    onRecive(1),
    onEquip(2);

    private byte type;

    private EItemVestedType(final int type) {
        this.type = (byte) type;
    }

    public static EItemVestedType valueOf(final int id) {
        for (final EItemVestedType type : values()) {
            if (type.getType() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid EItemVestedType id: " + id);
    }

    public byte getType() {
        return this.type;
    }

    public boolean isOnEquip() {
        return this == EItemVestedType.onEquip;
    }

    public boolean isOnRecive() {
        return this == EItemVestedType.onRecive;
    }
}
