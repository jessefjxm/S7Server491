package com.bdoemu.gameserver.model.chat.enums;

public enum EChatResponseType {
    Rejected,
    Accepted;

    public boolean isAccepted() {
        return this.equals(EChatResponseType.Accepted);
    }
}
