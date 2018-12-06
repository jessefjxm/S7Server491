package com.bdoemu.gameserver.model.creature.player.fitness.enums;

public enum EFitnessType {
    Stamina,
    Strength,
    Health;

    public static EFitnessType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            throw new IllegalArgumentException("Invalid EFitnessType id: " + reqType);
        }
        return values()[reqType];
    }
}
