package com.bdoemu.gameserver.model.actions.enums;

public enum EActionEffectType {
    None(0),
    NotUseStaminaInMove(1);

    private int state;

    EActionEffectType(final int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}