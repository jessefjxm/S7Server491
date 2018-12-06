package com.bdoemu.gameserver.model.actions.enums;

public enum ESpUseType {
    Once,
    Continue,
    Recover,
    Stop,
    Reset,
    None;

    public int getId() {
        return this.ordinal();
    }

    public boolean isContinue() {
        return this == ESpUseType.Continue;
    }
}