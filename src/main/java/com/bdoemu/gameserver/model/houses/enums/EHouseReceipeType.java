// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.enums;

public enum EHouseReceipeType {
    Empty(0),
    Lodging(1),
    Depot(2),
    Ranch(3),
    WeaponForgingWorkshop(4),
    ArmorForgingWorkshop(5),
    HandMadeWorkshop(6),
    WoodCraftWorkshop(7),
    JewelryWorkshop(8),
    ToolWorkshop(9),
    Refinery(10),
    ImproveWorkshop(11),
    CannonWorkshop(12),
    Shipyard(13),
    CarriageWorkshop(14),
    HorseArmorWorkshop(15),
    FurnitureWorkshop(16),
    LocalSpecailtiesWorkshop(17),
    Wardrobe(18),
    SiegeWeapons(19),
    ShipParts(20),
    WagonParts(21),
    AssetManagementshop(22),
    PotteryWorkshop(23);

    private byte id;

    private EHouseReceipeType(final int id) {
        this.id = (byte) id;
    }

    public static EHouseReceipeType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EHouseReceipeType id: " + reqType);
        }
        return values()[reqType];
    }

    public boolean isLodging() {
        return this == EHouseReceipeType.Lodging;
    }

    public boolean isDepot() {
        return this == EHouseReceipeType.Depot;
    }

    public boolean isRanch() {
        return this == EHouseReceipeType.Ranch;
    }

    public boolean isEmpty() {
        return this == EHouseReceipeType.Empty;
    }
}
