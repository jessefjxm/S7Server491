// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.enums;

public enum ESkillOwnerType {
    Character,
    Guild;

    public boolean isCharacter() {
        return this == ESkillOwnerType.Character;
    }

    public boolean isGuild() {
        return this == ESkillOwnerType.Guild;
    }
}
