package com.bdoemu.gameserver.model.chat.enums;

public enum EChatType {
    Undefine(0),
    Notice(1),
    World(2),
    Public(3),
    Private(4),
    System(5),
    Party(6),
    Guild(7),
    Client(8),
    Alliance(9),
    Friend(10),
    GameInfo3(11),
    WorldWithItem(12),
    Battle(13),
    LocalWar(14),
    RolePlay(15),
    NoticeOnlyTop(16),
    Arsha(17),
    Team(18),
    Count(19);

    private byte id;

    EChatType(final int id) {
        this.id = (byte) id;
    }

    public static EChatType valueOf(final int reqType) {
        if (reqType < 1 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }
}
