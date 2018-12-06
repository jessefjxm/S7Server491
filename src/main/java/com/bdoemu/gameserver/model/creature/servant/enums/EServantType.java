package com.bdoemu.gameserver.model.creature.servant.enums;

public enum EServantType {
    Vehicle(0),
    Ship(1),
    Pet(2);

    private byte id;

    private EServantType(final int id) {
        this.id = (byte) id;
    }

    public static EServantType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public boolean isShip() {
        return this == EServantType.Ship;
    }

    public boolean isVehicle() {
        return this == EServantType.Vehicle;
    }

    public boolean isPet() {
        return this == EServantType.Pet;
    }
}
