// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.alchemy.enums;

public enum EAlchemyType {
    Alchemy(0),
    Coocking(1);

    private byte id;

    private EAlchemyType(final int id) {
        this.id = (byte) id;
    }

    public static EAlchemyType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EAlchemyType id: " + reqType);
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }

    public boolean isAlchemy() {
        return this == EAlchemyType.Alchemy;
    }
}
