// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum ECashProductCategory {
    None(0),
    Pearl(1),
    Mileage(2),
    Normal(3),
    Costumes(4),
    Furniture(5),
    Servant(6),
    Pet(7),
    Beauty(8);

    private byte id;

    private ECashProductCategory(final int id) {
        this.id = (byte) id;
    }

    public static ECashProductCategory valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
