// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.enums;

public enum EFixedHouseType {
    Tent,
    House;

    public boolean isTent() {
        return this == EFixedHouseType.Tent;
    }
}
