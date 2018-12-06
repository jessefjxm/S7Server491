package com.bdoemu.gameserver.model.creature.enums;

import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.Gate;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.houses.HouseHold;

public enum ECharKind {
    Player(0, ERemoveActorType.OutOfRange, Player.class),
    Monster(1, ERemoveActorType.OutOfRange, Monster.class),
    Npc(2, ERemoveActorType.OutOfRange, Npc.class),
    Vehicle(3, ERemoveActorType.OutOfRange, Servant.class),
    Collect(4, ERemoveActorType.None, Collect.class),
    Alterego(5, ERemoveActorType.None, Gate.class),
    Gate(6, ERemoveActorType.None, null),
    Household(7, ERemoveActorType.None, HouseHold.class),
    Installation(8, ERemoveActorType.None, null),
    Deadbody(9, ERemoveActorType.None, DeadBody.class);

    private byte id;
    private ERemoveActorType outOfRangeRemoveActorType;
    private Class actorClass;

    ECharKind(final int id, final ERemoveActorType outOfRangeActorType, final Class actorClass) {
        this.id = (byte) id;
        this.outOfRangeRemoveActorType = outOfRangeActorType;
        this.actorClass = actorClass;
    }

    public static ECharKind valueOf(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Invalid ECharKind id: " + id);
        }
        return values()[id];
    }

    public static ECharKind forClass(final Class actorClass) {
        for (final ECharKind charKind : values()) {
            if (charKind.actorClass != null && charKind.actorClass.getSimpleName().equals(actorClass.getSimpleName())) {
                return charKind;
            }
        }
        return null;
    }

    public boolean isHousehold() {
        return this == ECharKind.Household;
    }

    public boolean isMonster() {
        return this == ECharKind.Monster;
    }

    public boolean isVehicle() {
        return this == ECharKind.Vehicle;
    }

    public boolean isNpc() {
        return this == ECharKind.Npc;
    }

    public boolean isCollect() {
        return this == ECharKind.Collect;
    }

    public boolean isDeadbody() {
        return this == ECharKind.Deadbody;
    }

    public boolean isPlayer() {
        return this == ECharKind.Player;
    }

    public boolean isAlterego() {
        return this == ECharKind.Alterego;
    }

    public byte getId() {
        return this.id;
    }

    public ERemoveActorType getOutOfRangeRemoveActorType() {
        return this.outOfRangeRemoveActorType;
    }
}
