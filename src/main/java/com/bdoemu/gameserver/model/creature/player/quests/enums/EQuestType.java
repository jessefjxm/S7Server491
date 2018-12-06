package com.bdoemu.gameserver.model.creature.player.quests.enums;

public enum EQuestType {
    BlackSpirit(0),
    Story(1),
    Town(2),
    Adventure(3),
    Trade(4),
    Craft(5),
    Repetition(6),
    Guild(7);

    private byte id;

    EQuestType(final int id) {
        this.id = (byte) id;
    }

    public static EQuestType valueOf(final byte id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Invalid EQuestType id: " + id);
        }
        return values()[id];
    }

    public byte getId() {
        return this.id;
    }
}
