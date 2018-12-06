package com.bdoemu.gameserver.model.creature.agrolist.enums;

public enum EDmgType {
    Default(0),
    Critical(1),
    Block(2),
    Guard(3),
    Unk(4),
    Immuned(5),
    Evasion(6);

    private int id;

    EDmgType(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
