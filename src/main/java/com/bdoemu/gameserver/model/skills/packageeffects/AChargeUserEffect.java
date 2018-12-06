package com.bdoemu.gameserver.model.skills.packageeffects;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public abstract class AChargeUserEffect extends JSONable {
    protected long effectEndTime;
    protected EChargeUserType chargeUserType;

    public AChargeUserEffect(final long effectEndTime, final EChargeUserType chargeUserType) {
        this.effectEndTime = effectEndTime;
        this.chargeUserType = chargeUserType;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("effectEndTime", this.effectEndTime);
        builder.append("chargeUserType", this.chargeUserType.ordinal());
        return builder.get();
    }

    public boolean isValid() {
        return System.currentTimeMillis() < this.effectEndTime;
    }

    public long getEndTime() {
        return this.isValid() ? this.effectEndTime : 0L;
    }

    public void setEndTime(long newTimeMillis) {
        this.effectEndTime = newTimeMillis;
    }

    public long getEndTimeSeconds() {
        return this.getEndTime() / 1000L;
    }

    public EChargeUserType getChargeUserType() {
        return this.chargeUserType;
    }

    public abstract void initEffect(final Player p0);

    public abstract void endEffect(final Player p0);
}
