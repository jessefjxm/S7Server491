package com.bdoemu.gameserver.model.creature.collect.enums;

public enum ECollectToolType {
    BareHands(0),
    EmptyBottle(1),
    Axe(2),
    Syringe(3),
    Hoe(4),
    ButcheryKnife(5),
    SkinKnife(6),
    Pickax(7),
    Tweezers(8),
    Pray(9),
    MagnifyingLens(10),
    None(11);

    private int id;

    ECollectToolType(final int id) {
        this.id = id;
    }

    public static ECollectToolType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid ECollectToolType id: " + reqType);
        }
        return values()[reqType];
    }

    public int getId() {
        return this.id;
    }

    public boolean isBareHands() {
        return this == ECollectToolType.BareHands;
    }
}
