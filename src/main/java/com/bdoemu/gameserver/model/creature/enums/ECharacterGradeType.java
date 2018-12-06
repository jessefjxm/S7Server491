package com.bdoemu.gameserver.model.creature.enums;

public enum ECharacterGradeType {
    Normal(0),
    Elite(1),
    Hero(2),
    Legend(3),
    Boss(4),
    Assistant(5),
    unk6(6),
    unk7(7);

    private byte id;

    ECharacterGradeType(final int id) {
        this.id = (byte) id;
    }

    public static ECharacterGradeType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid ECharacterGradeType id: " + reqType);
        }
        return values()[reqType];
    }

    public boolean isGreatherOrEqual(final ECharacterGradeType gradeType) {
        return this.ordinal() >= gradeType.ordinal();
    }
}
