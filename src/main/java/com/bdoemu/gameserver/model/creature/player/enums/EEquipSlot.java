package com.bdoemu.gameserver.model.creature.player.enums;

public enum EEquipSlot {
    rightHand(0),
    leftHand(1),
    subTool(2),
    chest(3),
    glove(4),
    boots(5),
    helm(6),
    necklace(7),
    ring1(8),
    ring2(9),
    earing1(10),
    earing2(11),
    belt(12),
    lantern(13),
    avatarChest(14),
    avatarGlove(15),
    avatarBoots(16),
    avatarHelm(17),
    avatarWeapon(18),
    avatarSubWeapon(19),
    avatarUnderWear(20),
    faceDecoration1(21),
    faceDecoration2(22),
    faceDecoration3(23),
    installation4(24),
    body(25),
    avatarBody(26),
    alchemyStone(27),
    explorationBonus0(28),
    awakenWeapon(29),
    avatarAwakenWeapon(30);

    private byte id;

    EEquipSlot(final int id) {
        this.id = (byte) id;
    }

    public static EEquipSlot valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EEquipSlot id: " + reqType);
        }
        return values()[reqType];
    }

    public static EEquipSlot valueof(final String value) {
        for (final EEquipSlot type : values()) {
            if (type.name().toLowerCase().equals(value.toLowerCase())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid EEquipSlot id: " + value);
    }

    public byte getId() {
        return this.id;
    }
}
