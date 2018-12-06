// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.enums;

public enum ESkillAwakeningType {
    Normal,
    AwakeningWeapon;

    public boolean isNormal() {
        return this == ESkillAwakeningType.Normal;
    }

    public boolean isAwakeningWeapon() {
        return this == ESkillAwakeningType.AwakeningWeapon;
    }
}
