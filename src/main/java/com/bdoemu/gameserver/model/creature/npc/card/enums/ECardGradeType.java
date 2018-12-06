// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.enums;

public enum ECardGradeType {
    C(1),
    B(2),
    A(3),
    A_PLUS(4),
    S(5);

    private int level;

    private ECardGradeType(final int level) {
        this.level = level;
    }

    public static ECardGradeType valueof(final int level) {
        for (final ECardGradeType type : values()) {
            if (type.getLevel() == level) {
                return type;
            }
        }
        return null;
    }

    public int getLevel() {
        return this.level;
    }
}
