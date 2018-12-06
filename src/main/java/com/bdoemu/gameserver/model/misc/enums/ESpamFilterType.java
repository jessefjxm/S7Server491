package com.bdoemu.gameserver.model.misc.enums;

public enum ESpamFilterType {
    REQUEST_CASH(500L);

    private long spamTime;

    ESpamFilterType(final long spamTime) {
        this.spamTime = spamTime;
    }

    public long getSpamTime() {
        return this.spamTime;
    }
}