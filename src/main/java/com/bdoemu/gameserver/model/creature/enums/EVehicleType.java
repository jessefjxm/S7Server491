package com.bdoemu.gameserver.model.creature.enums;

public enum EVehicleType {
    Horse(0),
    Cannon(1),
    Camel(2),
    Donkey(3),
    Elephant(4),
    Bomb(5),
    GateCrank(6),
    Ladder(7),
    Haetae(8),
    Carriage(9),
    TransferableSheep(10),
    TransferableCart(11),
    TransferableLog(12),
    TransferableBarrel(13),
    TransferableBox(14),
    TransferableSack(15),
    TransferableRaft(16),
    TransferablePumpkin(17),
    Boat(18),
    TransferableRock(19),
    QuestDonkey(20),
    Catapult(21),
    Ballista(22),
    Catapult2(23),
    HubMachine(24),
    Buoy(25),
    MilkCow(26),
    LumberVerticalSaw(27),
    CowCarriage(28),
    Raft(29),
    Cat(30),
    Dog(31),
    MountainGoat(32),
    FishingBoat(33),
    SailingBoat(34),
    Tank(35),
    BabyElephant(36),
    SiegeBastille(37),
    TrainingCannon(38),
    SiegeFrameTower(39),
    RidableBabyElephant(40),
    RidableBarricade(41),
    SiegeTower(42),
    SiegeTower_A(43),
    TradeShip(44),
    Unk(45),
    PersonTradeShip(46),
    CampingTent(47),
    RepairableCarriage(48),
    None(49);

    private byte id;

    EVehicleType(final int id) {
        this.id = (byte) id;
    }

    public static EVehicleType valueOf(final int id) {
        if (id < 0) {
            return EVehicleType.None;
        }
        if (id > EVehicleType.RepairableCarriage.ordinal()) {
            throw new IllegalArgumentException("Invalid EVehicleType id: " + id);
        }
        return values()[id];
    }

    public byte getId() {
        return this.id;
    }

    public boolean isHaetae() {
        return this == EVehicleType.Haetae;
    }

    public boolean isHorse() {
        return this == EVehicleType.Horse;
    }

    public boolean isCarriage() {
        return this == EVehicleType.Carriage;
    }

    public boolean isElephant() {
        return this == EVehicleType.Elephant;
    }
}
