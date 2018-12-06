// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EEnchantType {
    SafeEnchant,
    UnsafeEnchant,
    AccessoryEnchant,
    UnsafeRiskEnchant;

    public static EEnchantType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EEnchantType id: " + reqType);
        }
        return values()[reqType];
    }

    public boolean isAccessoryEnchant() {
        return this == EEnchantType.AccessoryEnchant;
    }

    public boolean isUnsafeRiskEnchant() {
        return this == EEnchantType.UnsafeRiskEnchant;
    }
}
