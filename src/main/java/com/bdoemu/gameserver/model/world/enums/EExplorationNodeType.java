package com.bdoemu.gameserver.model.world.enums;

public enum EExplorationNodeType {
    Normal(0),
    Village(1),
    City(2),
    Gate(3),
    Farm(4),
    Trade(5),
    Collect(6),
    Quarry(7),
    Logging(8),
    Dangerous(9),
    Finance(10),
    FishTrap(11),
    MinorFinance(12),
    MonopolyFarm(13),
    Craft(14),
    Excavation(15);

    private byte id;

    EExplorationNodeType(final int id) {
        this.id = (byte) id;
    }

    public static EExplorationNodeType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}