package com.bdoemu.gameserver.model.creature.enums;

public enum EInstallationType {
    Default(0),
    Carpenter(1),
    Alchemy1(2),
    Founding(3),
    Cooking(4),
    Forging(5),
    Treasure(6),
    Smithing(7),
    Weaving(8),
    Bed(9),
    Havest(10),
    Mortar(11),
    Anvil(12),
    Stump(13),
    FireBowl(14),
    VendingMachine(15),
    ConsignmentSale(16),
    Buff(17),
    RoomServiceTable(18),
    Bookcase(19),
    WarehouseBox(20),
    Alchemy(21),
    Jukebox(22),
    Pet(23),
    WallPaper(24),
    FloorMaterial(25),
    Chandelier(26),
    Curtain(27),
    Scarecrow(28),
    Waterway(29),
    Maid(30),
    CurtainTied(31),
    LivestockHarvest(32),
    FirePlace(33),
    InstanceWater(34),
    Shelf(35),
    Bath(36);

    private byte id;

    EInstallationType(final int id) {
        this.id = (byte) id;
    }

    public static EInstallationType valueOf(final int id) {
        for (final EInstallationType installationType : values()) {
            if (installationType.getId() == id) {
                return installationType;
            }
        }
        throw new IllegalArgumentException("Invalid InstallationType id: " + id);
    }

    public byte getId() {
        return this.id;
    }

    public boolean isJukebox() {
        return this == EInstallationType.Jukebox;
    }

    public boolean isScarecrow() {
        return this == EInstallationType.Scarecrow;
    }

    public boolean isWaterway() {
        return this == EInstallationType.Waterway;
    }

    public boolean isHavest() {
        return this == EInstallationType.Havest;
    }

    public boolean isLivestockHarvest() {
        return this == EInstallationType.LivestockHarvest;
    }

    public boolean isWarehouseBox() {
        return this == EInstallationType.WarehouseBox;
    }
}
