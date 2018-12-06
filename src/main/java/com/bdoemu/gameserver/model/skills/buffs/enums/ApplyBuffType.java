// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.enums;

public enum ApplyBuffType {
    Normal,
    ReApply,
    EnterWorld;

    public boolean isReApply() {
        return this == ApplyBuffType.ReApply;
    }

    public boolean isEnterWorld() {
        return this == ApplyBuffType.EnterWorld;
    }
}
