package com.bdoemu.gameserver.model.chat.enums;

public enum EChatNoticeType {
    Normal(0),
    Campaign(1),
    Emergency(2),
    Tip(3),
    GuildBoss(4),
    OfferingCarrier(5),
    Defence(6),
    KeepNotify(7),
    ClearKeepNotify(8),
    Hunting(9),
    HuntingEnd(10),
    Kzarka(11),
    Nuberu(12),
    FieldBoss(13),
    PvPHost(14),
    SavageDefence(15),
    SavageDefenceBoss(16),
    None(15);

    private byte id;

    EChatNoticeType(final int id) {
        this.id = (byte) id;
    }

    public static EChatNoticeType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }
}
