package com.bdoemu.gameserver.model.creature.player.enums;

public enum EJewelEquipType {
    All(0, new EquipType[]{EquipType.Helm, EquipType.Armor, EquipType.Gloves, EquipType.Shoes, EquipType.LongSword, EquipType.ElBlade, EquipType.Blade, EquipType.Staff, EquipType.EnchantArm, EquipType.DoubleAxe, EquipType.BowOneHanded, EquipType.Shield, EquipType.Dagger, EquipType.Lease, EquipType.Knot, EquipType.Bow, EquipType.Pacifier, EquipType.Shuriken, EquipType.Kunai, EquipType.Kriegsmesser, EquipType.Gauntlet, EquipType.Sherry_Main}),
    WeaponMain(1, new EquipType[]{EquipType.LongSword, EquipType.ElBlade, EquipType.Blade, EquipType.Staff, EquipType.EnchantArm, EquipType.DoubleAxe, EquipType.BowOneHanded, EquipType.Kriegsmesser, EquipType.Gauntlet}),
    WeaponSecondary(2, new EquipType[]{EquipType.Shield, EquipType.Dagger, EquipType.Lease, EquipType.Knot, EquipType.Bow, EquipType.Pacifier, EquipType.Shuriken, EquipType.Kunai, EquipType.Sherry_Main}),
    Helm(3, new EquipType[]{EquipType.Helm}),
    Armor(5, new EquipType[]{EquipType.Armor}),
    Gloves(7, new EquipType[]{EquipType.Gloves}),
    Shoes(8, new EquipType[]{EquipType.Shoes}),
    AwakeningWeapons(11, new EquipType[]{EquipType.AwakeningWeapon});

    private byte id;
    private EquipType[] equipTypes;

    EJewelEquipType(final int id, final EquipType[] equipTypes) {
        this.id = (byte) id;
        this.equipTypes = equipTypes;
    }

    public static EJewelEquipType valueOf(final int id) {
        for (final EJewelEquipType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid JewelEquipType id: " + id);
    }

    public byte getId() {
        return this.id;
    }

    public EquipType[] getEquipTypes() {
        return this.equipTypes;
    }

    public boolean canJewelAct(final EquipType type) {
        for (final EquipType equipType : this.equipTypes) {
            if (equipType == type) {
                return true;
            }
        }
        return false;
    }
}
