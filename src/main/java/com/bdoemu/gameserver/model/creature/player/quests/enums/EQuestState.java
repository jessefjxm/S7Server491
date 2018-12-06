package com.bdoemu.gameserver.model.creature.player.quests.enums;

public enum EQuestState {
    Progress(0),
    NormalCleared(1),
    SubCleared(2),
    DoGuide(3);

    private byte id;

    EQuestState(final int id) {
        this.id = (byte) id;
    }

    public static EQuestState valueOf(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Invalid EQuestState id: " + id);
        }
        return values()[id];
    }

    public byte getId() {
        return this.id;
    }
}
