package com.bdoemu.gameserver.model.actions.enums;

import java.util.ArrayList;
import java.util.List;

public enum ETargetType {
    Self(1),
    Catchee(2),
    CombineWaveAlly(4),
    CombineWavePlayer(8),
    Monster(16),
    AllyPlayer(32),
    AllyOtherPlayer(64),
    NonAllyPlayer(128),
    Npc(256),
    Enemy(512),
    AlterEgo(1024),
    Collect(2048),
    Character(4096),
    Corpse(8192),
    VehicleHorse(16384),
    Tent(32768),
    Installation(65536),
    OwnerInTheHouse(131072),
    PKPlayer(262144),
    RidingVehicle(524288),
    EnemyLordOrKingsTent(1048576),
    EnemyBarricade(2097152),
    PetDetectPosition(4194304),
    AllyLordOrKingsTent(8388608),
    OwnerPlayer(16777216),
    Interaction(33554432),
    InteractionPlayer(67108864),
    AllyMonster(134217728),
    AllyVehicle(268435456),
    EliteMonster(536870912),
    LordOrKingPlayer(1073741824);

    private int id;

    ETargetType(final int id) {
        this.id = id;
    }

    public static List<ETargetType> valueof(final int id) {
        final List<ETargetType> list = new ArrayList<ETargetType>();
        for (final ETargetType type : values()) {
            if ((id & type.id) == type.id) {
                list.add(type);
            }
        }
        return list;
    }

    public static int toInt(final List<ETargetType> targetTypes) {
        int targetsInt = 0;
        for (final ETargetType targetType : targetTypes) {
            targetsInt |= targetType.getId();
        }
        return targetsInt;
    }

    public int getId() {
        return this.id;
    }

    public boolean isSelf() {
        return this == ETargetType.Self;
    }
}