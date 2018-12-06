package com.bdoemu.gameserver.model.creature.player.encyclopedia.enums;

public enum EEncyclopediaType {
    Fishing;

    public static EEncyclopediaType valueOf(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Invalid EEncyclopediaType  id: " + id);
        }
        return values()[id];
    }
}
