package com.bdoemu.gameserver.model.creature.servant.enums;

public enum EServantState {
    Stable(0),
    Field(1),
    RegisterMarket(2),
    RegisterMating(3),
    Mating(4),
    Coma(5),
    SkillTraining(6),
    StallionTraining(7);

    private byte id;

    private EServantState(final int id) {
        this.id = (byte) id;
    }

    public static EServantState valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public boolean skillTraining() {
        return this == EServantState.SkillTraining;
    }

    public boolean isStable() {
        return this == EServantState.Stable;
    }

    public boolean isRegisterMating() {
        return this == EServantState.RegisterMating;
    }

    public boolean isRegisterMarket() {
        return this == EServantState.RegisterMarket;
    }

    public boolean isComa() {
        return this == EServantState.Coma;
    }

    public boolean isField() {
        return this == EServantState.Field;
    }

    public boolean isMating() {
        return this == EServantState.Mating;
    }
}
