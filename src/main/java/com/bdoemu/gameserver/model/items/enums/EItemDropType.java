// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EItemDropType {
    DeadBodyActor(0),
    CollectInfo(1),
    Fishing(2),
    Harvest(3),
    Steal(4),
    CollectUseTool(5);

    private byte id;

    private EItemDropType(final int id) {
        this.id = (byte) id;
    }

    public static EItemDropType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
