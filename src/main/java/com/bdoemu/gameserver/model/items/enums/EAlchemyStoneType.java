// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EAlchemyStoneType {
    StoneOfDestruction,
    StoneOfProtection,
    StoneOfLife;

    public static EAlchemyStoneType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
