package com.bdoemu.gameserver.model.creature.servant.enums;

public enum EServantKind {
    Horse(0, EServantType.Vehicle),
    Camel(1, EServantType.Vehicle),
    Donkey(2, EServantType.Vehicle),
    Elephant(3, EServantType.Vehicle),
    TwoWheelCarriage(4, EServantType.Vehicle),
    FourWheeledCarriage(5, EServantType.Vehicle),
    Ship(6, EServantType.Ship),
    Cat(7, EServantType.Pet),
    Dog(8, EServantType.Pet),
    MountainGoat(9, EServantType.Vehicle),
    Raft(10, EServantType.Ship),
    FishingBoat(11, EServantType.Ship),
    BattleWagon(12, EServantType.Vehicle),
    Elephant2(13, EServantType.Vehicle),
    Haller(14, EServantType.Ship),
    PersonTradeShip(15, EServantType.Ship),
    CampingTent(16, EServantType.Vehicle),
    BattleShip(17, EServantType.Ship);

    private byte id;
    private EServantType servantType;

    private EServantKind(final int id, final EServantType servantType) {
        this.id = (byte) id;
        this.servantType = servantType;
    }

    public static EServantKind valueOf(final int reqType) {
        for (final EServantKind servantKind : values()) {
            if (servantKind.id == reqType) {
                return servantKind;
            }
        }
        return null;
    }

    public byte getId() {
        return this.id;
    }

    public EServantType getServantType() {
        return this.servantType;
    }
}
