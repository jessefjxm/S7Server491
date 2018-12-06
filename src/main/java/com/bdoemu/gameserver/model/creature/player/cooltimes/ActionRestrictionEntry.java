package com.bdoemu.gameserver.model.creature.player.cooltimes;

public class ActionRestrictionEntry {
    private int _actionRestrictionType;
    private long _expiryTime;

    public ActionRestrictionEntry(int type, long expireTime) {
        _actionRestrictionType = type;
        _expiryTime = expireTime;
    }

    public int getActionRestrictionType() {
        return _actionRestrictionType;
    }

    public long getExpirationTime() {
        return _expiryTime;
    }

    public void setExpirationTime(long newExpirationTime) {
        _expiryTime = newExpirationTime;
    }

    public boolean isExpired() {
        return _expiryTime < System.currentTimeMillis();
    }
}