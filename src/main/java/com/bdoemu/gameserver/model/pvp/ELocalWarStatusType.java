package com.bdoemu.gameserver.model.pvp;

public enum ELocalWarStatusType {
    Waiting,
    Started,
    Ending;

    public boolean isStarted() {
        return this == ELocalWarStatusType.Started;
    }

    public boolean isEnding() {
        return this == ELocalWarStatusType.Ending;
    }

    public boolean isWaiting() {
        return this == ELocalWarStatusType.Waiting;
    }
}
