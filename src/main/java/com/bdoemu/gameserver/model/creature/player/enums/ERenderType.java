package com.bdoemu.gameserver.model.creature.player.enums;

public enum ERenderType {
    HELM(1),
    HELM_IN_BATTLE(2),
    UNDERWEAR(4),
    SHOW_NAME_WHEN_CAMOUFLAGE(8),
    CLOAK(16);

    private int mask;

    private ERenderType(final int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return this.mask;
    }
}
