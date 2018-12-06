package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.houses.HouseHold;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class HouseholdLifeStats extends LifeStats<HouseHold> {
    public HouseholdLifeStats(final HouseHold owner) {
        super(owner, owner.getGameStats().getHp().getIntMaxValue(), owner.getGameStats().getMp().getIntMaxValue());
    }

    public HouseholdLifeStats(final HouseHold owner, final BasicDBObject lifeStats) {
        super(owner, lifeStats.getDouble("hp"), lifeStats.getDouble("mp"));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("hp", (Object) ((HouseHold) this.owner).getGameStats().getHp().getValue());
        builder.append("mp", (Object) ((HouseHold) this.owner).getGameStats().getMp().getValue());
        return builder.get();
    }
}