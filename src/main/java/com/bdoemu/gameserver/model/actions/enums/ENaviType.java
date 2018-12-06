package com.bdoemu.gameserver.model.actions.enums;

public enum ENaviType {
    none(0),
    air(1),
    wall(2),
    fence(4),
    ground(16),
    under_ground(32),
    water(64),
    under_water(128),
    ladder(256),
    all(ENaviType.ground.id | ENaviType.water.id | ENaviType.under_ground.id | ENaviType.under_water.id | ENaviType.air.id | ENaviType.wall.id | ENaviType.fence.id | ENaviType.ladder.id),
    under_water_ground(ENaviType.under_water.id | ENaviType.ground.id),
    under_waterground(ENaviType.under_water.id | ENaviType.ground.id),
    water_under_water(ENaviType.water.id | ENaviType.under_water.id);

    private int id;

    ENaviType(final int id) {
        this.id = id;
    }

    public static boolean hasAir(final int navigationTypes) {
        return (navigationTypes & ENaviType.air.getId()) != 0x0;
    }

    public int getId() {
        return this.id;
    }

    public boolean isAir() {
        return this == ENaviType.air;
    }
}