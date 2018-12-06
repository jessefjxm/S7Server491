package com.bdoemu.gameserver.model.creature.player.fishing;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class FishingTopRankBody extends JSONable {
    private int maxFishSize;
    private int key;
    private double x;
    private double y;
    private double z;
    private long accountId;
    private long characterObjectId;
    private long time;
    private String characterName;

    public FishingTopRankBody(final BasicDBObject basicDBObject) {
        this.maxFishSize = basicDBObject.getInt("maxFishSize");
        this.key = basicDBObject.getInt("key");
        this.accountId = basicDBObject.getLong("accountId");
        this.characterObjectId = basicDBObject.getLong("characterObjectId");
        this.characterName = basicDBObject.getString("characterName");
        this.x = basicDBObject.getDouble("x");
        this.y = basicDBObject.getDouble("y");
        this.z = basicDBObject.getDouble("z");
        this.time = basicDBObject.getLong("time");
    }

    public FishingTopRankBody(final Player player, final int maxFishSize, final int key) {
        this.maxFishSize = maxFishSize;
        this.key = key;
        this.accountId = player.getAccountId();
        this.characterObjectId = player.getObjectId();
        this.characterName = player.getName();
        this.x = player.getLocation().getX();
        this.y = player.getLocation().getY();
        this.z = player.getLocation().getZ();
        this.time = GameTimeService.getServerTimeInSecond();
    }

    public void update(final Player player, final int maxFishSize) {
        this.maxFishSize = maxFishSize;
        this.accountId = player.getAccountId();
        this.characterObjectId = player.getObjectId();
        this.characterName = player.getName();
        this.x = player.getLocation().getX();
        this.y = player.getLocation().getY();
        this.z = player.getLocation().getZ();
        this.time = GameTimeService.getServerTimeInSecond();
    }

    public int getKey() {
        return this.key;
    }

    public int getMaxFishSize() {
        return this.maxFishSize;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getCharacterObjectId() {
        return this.characterObjectId;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public String getFamilyName() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public long getTime() {
        return this.time;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("key", this.key);
        builder.append("accountId", this.accountId);
        builder.append("characterObjectId", this.characterObjectId);
        builder.append("characterName", this.characterName);
        builder.append("maxFishSize", this.maxFishSize);
        builder.append("time", this.time);
        builder.append("x", this.x);
        builder.append("y", this.y);
        builder.append("z", this.z);
        return builder.get();
    }
}
