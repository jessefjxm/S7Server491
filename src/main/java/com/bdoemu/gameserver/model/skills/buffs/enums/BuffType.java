// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.enums;

public enum BuffType {
    Buff,
    Damage;

    public boolean isDmg() {
        return this == BuffType.Damage;
    }

    public boolean isBuff() {
        return this == BuffType.Buff;
    }
}
