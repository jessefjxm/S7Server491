package com.bdoemu.gameserver.model.creature.player.enums;

public enum EquipType {
    None(0),
    LongSword(1, EEquipSlot.rightHand),
    ElBlade(2, EEquipSlot.rightHand),
    Blade(3, EEquipSlot.rightHand),
    Staff(6, EEquipSlot.rightHand),
    Shield(8, EEquipSlot.leftHand),
    Armor(9, EEquipSlot.chest),
    Gloves(11, EEquipSlot.glove),
    Shoes(12, EEquipSlot.boots),
    Helm(13, EEquipSlot.helm),
    Necklace(15, EEquipSlot.necklace),
    Ring(16, EEquipSlot.ring1, EEquipSlot.ring2),
    Earrings(17, EEquipSlot.earing1, EEquipSlot.earing2),
    Belt(18, EEquipSlot.belt),
    Lantern(19, EEquipSlot.lantern),
    CostumeChest(22, EEquipSlot.avatarChest),
    CostumeGloves(24, EEquipSlot.avatarGlove),
    CostumeBoots(25, EEquipSlot.avatarBoots),
    CostumeHelm(26, EEquipSlot.avatarHelm),
    EnchantArm(28, EEquipSlot.rightHand),
    DoubleAxe(29, EEquipSlot.rightHand),
    CostumeMainWeapon(30, EEquipSlot.avatarWeapon),
    BowOneHanded(31, EEquipSlot.rightHand),
    Dagger(32, EEquipSlot.leftHand),
    Lease(33, EEquipSlot.leftHand),
    Knot(34, EEquipSlot.leftHand),
    CostumeSecondaryWeapon(35, EEquipSlot.avatarSubWeapon),
    Bow(36, EEquipSlot.leftHand),
    Pacifier(37, EEquipSlot.leftHand),
    Underwear(38, EEquipSlot.avatarUnderWear),
    FaceHairAccessory1(39, EEquipSlot.faceDecoration1),
    FaceHairAccessory2(40, EEquipSlot.faceDecoration2),
    FaceHairAccessory3(41, EEquipSlot.faceDecoration3),
    Rifle(43, EEquipSlot.rightHand),
    FishingRod(44, EEquipSlot.rightHand),
    Flute(45, EEquipSlot.rightHand),
    GatheringTools(46, EEquipSlot.subTool),
    Cane(47, null),
    FishingHarpoon(48, EEquipSlot.rightHand),
    FishingNet(49, EEquipSlot.rightHand),
    Drum(50, EEquipSlot.rightHand),
    VehicleCostume1(51, EEquipSlot.body),
    VehicleCostume2(52, EEquipSlot.avatarBody),
    HuntRifle(53, EEquipSlot.rightHand),
    AlchemyStone(54, EEquipSlot.alchemyStone),
    Shuriken(55, EEquipSlot.leftHand),
    Kunai(56, EEquipSlot.leftHand),
    AwakeningWeapon(57, EEquipSlot.awakenWeapon),
    CostumeAwakeningWeapon(58, EEquipSlot.avatarAwakenWeapon),
    Crucifix(59, EEquipSlot.leftHand),
    Cymbals(60, EEquipSlot.rightHand),
    Guitar(61, EEquipSlot.rightHand),
    Trumpet(62, EEquipSlot.rightHand),
    Kriegsmesser(63, EEquipSlot.rightHand),
    Gun(64, EEquipSlot.rightHand),
    Gauntlet(65, EEquipSlot.rightHand),
    Sherry_Main(66, EEquipSlot.leftHand),
    UnusedWeapon4(67, EEquipSlot.rightHand),
    Magazine(68, EEquipSlot.rightHand),
    EventHammer(69, EEquipSlot.rightHand),
    Unk1(70, EEquipSlot.rightHand),
    EventFruit(71, EEquipSlot.rightHand);

    private byte id;
    private EEquipSlot defaultSlot;
    private EEquipSlot auxSlot;

    EquipType(final int id) {
        this.id = (byte) id;
    }

    EquipType(final int id, final EEquipSlot defaultSlot, final EEquipSlot auxSlot) {
        this.id = (byte) id;
        this.defaultSlot = defaultSlot;
        this.auxSlot = auxSlot;
    }

    EquipType(final int id, final EEquipSlot defaultSlot) {
        this.id = (byte) id;
        this.defaultSlot = defaultSlot;
    }

    public static EquipType valueOf(final int id) {
        for (final EquipType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid EquipType id: " + id);
    }

    public EEquipSlot getAuxSlot() {
        return this.auxSlot;
    }

    public EEquipSlot getDefaultSlot() {
        return this.defaultSlot;
    }

    public boolean isNone() {
        return this == EquipType.None;
    }

    public boolean canEquip(final EEquipSlot equipSlot) {
        return (this.defaultSlot != null && this.defaultSlot.equals(equipSlot)) || (this.auxSlot != null && this.auxSlot.equals(equipSlot));
    }

    public boolean isFishingRod() {
        return this == EquipType.FishingRod;
    }

    public boolean isAlchemyStone() {
        return this == EquipType.AlchemyStone;
    }
}
