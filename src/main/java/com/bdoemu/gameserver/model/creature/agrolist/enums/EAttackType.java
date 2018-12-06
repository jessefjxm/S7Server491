package com.bdoemu.gameserver.model.creature.agrolist.enums;

public enum EAttackType {
    BackAttack(0),
    CounterAttack(1),
    DownAttack(2),
    SpeedAttack(3),
    AirAttack(4),
    FrontAttack(5);

    private byte id;

    EAttackType(final int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

    public boolean isBackAttack() {
        return this == EAttackType.BackAttack;
    }
}
