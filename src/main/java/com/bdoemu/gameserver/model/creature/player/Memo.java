package com.bdoemu.gameserver.model.creature.player;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Memo extends JSONable {
    private String memoText;
    private long date;
    private long objectId;
    private int unk;
    private int questId1;
    private int questId2;
    private int group1;
    private int group2;
    private double x;
    private double y;
    private double z;
    private byte[] unkData;

    public Memo(final long objectId, final String memoText, final int unk, final float x, final float y, final float z, final int group1, final int group2, final int questId1, final int questId2, final byte[] unkData) {
        this.objectId = objectId;
        this.memoText = memoText;
        this.unk = unk;
        this.x = x;
        this.y = y;
        this.z = z;
        this.questId1 = questId1;
        this.questId2 = questId2;
        this.group1 = group1;
        this.group2 = group2;
        this.unkData = unkData;
        this.date = System.currentTimeMillis();
    }

    public Memo(final BasicDBObject dbObj) {
        this.date = dbObj.getLong("date");
        this.objectId = dbObj.getLong("objectId");
        this.unk = dbObj.getInt("unk");
        this.x = dbObj.getDouble("x");
        this.y = dbObj.getDouble("y");
        this.z = dbObj.getDouble("z");
        this.group1 = dbObj.getInt("group1");
        this.questId1 = dbObj.getInt("questId1");
        this.group2 = dbObj.getInt("group2");
        this.questId2 = dbObj.getInt("questId2");
        this.unkData = (byte[]) dbObj.get("unkData");
        this.memoText = dbObj.getString("memoText");
    }

    public long getObjectId() {
        return this.objectId;
    }

    public byte[] getUnkData() {
        return this.unkData;
    }

    public long getDate() {
        return this.date;
    }

    public int getQuestId2() {
        return this.questId2;
    }

    public int getQuestId1() {
        return this.questId1;
    }

    public int getGroup2() {
        return this.group2;
    }

    public int getGroup1() {
        return this.group1;
    }

    public int getUnk() {
        return this.unk;
    }

    public double getZ() {
        return this.z;
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }

    public String getMemoText() {
        return this.memoText;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("date", this.date);
        builder.append("objectId", this.objectId);
        builder.append("unk", this.unk);
        builder.append("x", this.x);
        builder.append("y", this.y);
        builder.append("z", this.z);
        builder.append("group1", this.group1);
        builder.append("questId1", this.questId1);
        builder.append("group2", this.group2);
        builder.append("questId2", this.questId2);
        builder.append("unkData", this.unkData);
        builder.append("memoText", this.memoText);
        return builder.get();
    }
}
