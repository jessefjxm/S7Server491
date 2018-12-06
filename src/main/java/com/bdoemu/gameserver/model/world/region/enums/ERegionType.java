package com.bdoemu.gameserver.model.world.region.enums;

public enum ERegionType {
    MinorTown(0),
    MainTown(1),
    Hunting(2),
    Siege(3),
    Fortress(4),
    CastleInSiege(5),
    Arena(6);

    private byte id;

    ERegionType(final int id) {
        this.id = (byte) id;
    }

    public static ERegionType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public boolean isMinorTown() {
        return this == ERegionType.MinorTown;
    }

    public boolean isMainTown() {
        return this == ERegionType.MainTown;
    }

    public boolean isArena() {
        return this == ERegionType.Arena;
    }
}