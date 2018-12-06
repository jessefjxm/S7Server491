// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.enums;

public enum EShopType {
    Shop(0),
    AlchemyShop(1),
    ArmorShop(2),
    CrystalShop(3),
    InstallationShop(4),
    ToolShop(5),
    WharfShop(6),
    RandomNpcWorkerShop(7),
    Alchemy2Shop(8),
    PubShop(9),
    PremiumShop(10),
    StableShop(11),
    SecretShop(12),
    RandomShop(13),
    MarketPlaceShop(14);

    private byte id;

    private EShopType(final int id) {
        this.id = (byte) id;
    }

    public static EShopType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EShopType id: " + reqType);
        }
        return values()[reqType];
    }

    public boolean isRandomShop() {
        return this == EShopType.RandomShop;
    }

    public boolean isRandomNpcWorkerShop() {
        return this == EShopType.RandomNpcWorkerShop;
    }
}
