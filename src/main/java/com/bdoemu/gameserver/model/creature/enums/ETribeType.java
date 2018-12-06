package com.bdoemu.gameserver.model.creature.enums;

public enum ETribeType {
    Human(0),
    Ain(1),
    Etc(2),
    Boss(3),
    Reptile(4),
    Untribe(5),
    Hunting(6),
    Elephant(7),
    Cannon(8),
    Siege(9);

    private byte id;

    ETribeType(final int id) {
        this.id = (byte) id;
    }

    public static ETribeType valueOf(final int id) {
        for (final ETribeType tribeType : values()) {
            if (tribeType.id == id) {
                return tribeType;
            }
        }
        throw new IllegalArgumentException("Invalid ETribeType id: " + id);
    }
}
