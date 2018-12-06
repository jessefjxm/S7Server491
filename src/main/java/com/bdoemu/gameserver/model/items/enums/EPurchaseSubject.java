// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EPurchaseSubject {
    None,
    Character,
    Account;

    public boolean isCharacter() {
        return this == EPurchaseSubject.Character;
    }

    public boolean isAccount() {
        return this == EPurchaseSubject.Account;
    }
}
