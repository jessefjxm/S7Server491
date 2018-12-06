package com.bdoemu.gameserver.model.creature.enums;

public enum ERemoveActorType {
    None(-2),
    Collect(-1),
    Logout(0),
    DespawnDeadBody(2),
    DespawnMonster(3),
    Teleport(4),
    DespawnEscort(5),
    DespawnTent(7),
    OutOfRange(9),
    SealServant(10),
    DespawnSummon(11),
    DespawnTamedServant(14),
    QuitPlayer(15);

    private byte id;

    ERemoveActorType(final int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

    public boolean isNone() {
        return this == ERemoveActorType.None;
    }

    public boolean isCollect() {
        return this == ERemoveActorType.Collect;
    }
}
