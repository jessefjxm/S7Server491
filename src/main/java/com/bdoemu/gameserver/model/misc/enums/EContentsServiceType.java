package com.bdoemu.gameserver.model.misc.enums;

public enum EContentsServiceType {
    Closed(0),
    CBT(1),
    Pre(2),
    OBT(3),
    Commercial(4);

    private byte id;

    EContentsServiceType(final int id) {
        this.id = (byte) id;
    }

    public static EContentsServiceType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
