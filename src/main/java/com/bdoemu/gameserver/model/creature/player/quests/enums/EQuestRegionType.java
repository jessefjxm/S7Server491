package com.bdoemu.gameserver.model.creature.player.quests.enums;

public enum EQuestRegionType {
    None(0),
    Balenos(1),
    Serendia(2),
    NorthCalpheon(3),
    CalpheonBigCity(4),
    Keplan(5),
    SouthWestCalpheon(6),
    Media(7),
    Valencia(8),
    Kamasylvia(9);

    private byte id;

    EQuestRegionType(final int id) {
        this.id = (byte) id;
    }

    public static EQuestRegionType valueOf(final byte id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Invalid EQuestRegionType id: " + id);
        }
        return values()[id];
    }

    public byte getId() {
        return this.id;
    }
}
