package com.bdoemu.gameserver.model.creature.player.quests.enums;

public enum EQuestRewardType {
    Exp(0),
    SkillExp(1),
    ProductExp(2),
    Item(3),
    Intimacy(4),
    Knowledge(5);

    private byte id;

    EQuestRewardType(final int id) {
        this.id = (byte) id;
    }

    public static EQuestRewardType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
