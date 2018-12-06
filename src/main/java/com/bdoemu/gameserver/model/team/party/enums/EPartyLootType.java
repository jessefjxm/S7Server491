package com.bdoemu.gameserver.model.team.party.enums;

public enum EPartyLootType {
    Free(0),
    Shuffle(1),
    Random(2),
    Master(3),
    PartyInven(4),
    Bound(5);

    private byte id;

    EPartyLootType(final int id) {
        this.id = (byte) id;
    }

    public static EPartyLootType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public byte getId() {
        return this.id;
    }

    public boolean isPartyInven() {
        return this == EPartyLootType.PartyInven;
    }
}