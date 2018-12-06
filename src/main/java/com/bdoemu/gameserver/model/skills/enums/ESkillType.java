// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.enums;

public enum ESkillType {
    Equip,
    Active,
    Passive;

    public boolean isActive() {
        return this == ESkillType.Active;
    }

    public boolean isPassive() {
        return this == ESkillType.Passive;
    }

    public boolean isEquip() {
        return this == ESkillType.Equip;
    }
}
