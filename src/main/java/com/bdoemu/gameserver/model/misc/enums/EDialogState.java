package com.bdoemu.gameserver.model.misc.enums;

public enum EDialogState {
    ReContact(0),
    QuestComplete(1),
    QuestList(2),
    DisplayQuest(3),
    AcceptQuest(4),
    RefuseQuest(5),
    ProgressQuest(6),
    RestartQuest(7),
    Function(8),
    Talk(9);

    private byte id;

    EDialogState(final int id) {
        this.id = (byte) id;
    }

    public static EDialogState valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }
}
