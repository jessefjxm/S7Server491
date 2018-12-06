package com.bdoemu.gameserver.model.creature.player.enums;

public enum ERespawnType {
    Ressurect(1),
    RespawnInNode(3),
    RespawnInTown(4),
    AutoRespawn(5),
    RedBattlefieldRespawn(7);

    private int id;

    ERespawnType(final int id) {
        this.id = id;
    }

    public static ERespawnType valueOf(final int id) {
        for (final ERespawnType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }
}
