package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ServantLifeStats extends LifeStats<Servant> {
    public ServantLifeStats(final Servant owner) {
        super(owner, owner.getGameStats().getHp().getIntMaxValue(), owner.getGameStats().getMp().getIntMaxValue());
    }

    public ServantLifeStats(final Servant servant, final BasicDBObject lifeStats) {
        super(servant, lifeStats.getDouble("hp"), lifeStats.getDouble("mp"));
        final GameStats<Servant> gameStats = this.owner.getGameStats();
        gameStats.getHp().addBonus(lifeStats.getDouble("hpBonus", 0.0));
        gameStats.getMp().addBonus(lifeStats.getDouble("mpBonus", 0.0));
        gameStats.getAccelerationRate().addBonus(lifeStats.getDouble("accelerationRateBonus", 0.0));
        gameStats.getMaxMoveSpeedRate().addBonus(lifeStats.getDouble("moveSpeedBonus", 0.0));
        gameStats.getCorneringSpeedRate().addBonus(lifeStats.getDouble("corneringBonus", 0.0));
        gameStats.getBrakeSpeedRate().addBonus(lifeStats.getDouble("brakeBonus", 0.0));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final GameStats<Servant> gameStats = this.owner.getGameStats();
        builder.append("hp", gameStats.getHp().getValue());
        builder.append("mp", gameStats.getMp().getValue());
        builder.append("hpBonus", gameStats.getHp().getBonus());
        builder.append("mpBonus", gameStats.getMp().getBonus());
        builder.append("accelerationRateBonus", gameStats.getAccelerationRate().getBonus());
        builder.append("moveSpeedBonus", gameStats.getMaxMoveSpeedRate().getBonus());
        builder.append("corneringBonus", gameStats.getCorneringSpeedRate().getBonus());
        builder.append("brakeBonus", gameStats.getBrakeSpeedRate().getBonus());
        return builder.get();
    }
}
