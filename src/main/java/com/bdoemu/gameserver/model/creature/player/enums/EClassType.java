package com.bdoemu.gameserver.model.creature.player.enums;

public enum EClassType {
    Warrior(0, EGenderType.Man, true),
    WarriorType1(1, EGenderType.Man),
    WarriorF(2, EGenderType.Woman),
    WarriorType3(3, EGenderType.Woman),
    Ranger(4, EGenderType.Woman, true),
    RangerType1(5, EGenderType.Woman),
    RangerType2(6, EGenderType.Woman),
    RangerType3(7, EGenderType.Woman),
    Sorcerer(8, EGenderType.Woman, true),
    SorcererType1(9, EGenderType.Woman),
    SorcererF(10, EGenderType.Woman),
    SorcererType3(11, EGenderType.Woman),
    Giant(12, EGenderType.Man, true),
    BerserkerPredator(13, EGenderType.Man),
    BerserkerType2(14, EGenderType.Man),
    BerserkerType3(15, EGenderType.Man),
    Tamer(16, EGenderType.Woman, true),
    ShyWomen(17, EGenderType.Woman),
    Shy(18, EGenderType.Man),
    CombatTant(19, EGenderType.Man, true),
    BladeMaster(20, EGenderType.Man, true),
    BladeMasterWomen(21, EGenderType.Woman, true),
    TempMaehwa(22, EGenderType.Man),
    MusaType3(23, EGenderType.Man),
    Valkyrie(24, EGenderType.Woman, true),
    NinjaWomen(25, EGenderType.Woman, true),
    NinjaMan(26, EGenderType.Man, true),
    DarkElf(27, EGenderType.Woman, true),
    Wizard(28, EGenderType.Man, true),
    WizardType1(29, EGenderType.Man),
    Kunoichi(30, EGenderType.Man),
    WizardWomen(31, EGenderType.Woman, true);

    private int id;
    private EGenderType genderType;
    private boolean isEnabled;

    EClassType(final int id, final EGenderType genderType, final boolean isEnabled) {
        this(id, genderType);
        this.isEnabled = isEnabled;
    }

    EClassType(final int id, final EGenderType genderType) {
        this.isEnabled = false;
        this.id = id;
        this.genderType = genderType;
    }

    public static EClassType valueOf(final int id) {
        if (id < 0 || id > values().length - 1) {
            return null;
        }
        return values()[id];
    }

    public EGenderType getGenderType() {
        return this.genderType;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
