package com.bdoemu.gameserver.model.creature.player.social.actions;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class SocialActionConfig extends JSONable {
    private int actionIndex;
    private byte type;
    private String chattingKeyword;

    public SocialActionConfig(final int actionIndex, final byte type, final String chattingKeyword) {
        this.actionIndex = actionIndex;
        this.type = type;
        this.chattingKeyword = chattingKeyword;
    }

    public SocialActionConfig(final BasicDBObject basicDBObject) {
        this.actionIndex = basicDBObject.getInt("actionIndex");
        this.type = (byte) basicDBObject.getInt("type");
        this.chattingKeyword = basicDBObject.getString("chattingKeyword");
    }

    public byte getType() {
        return this.type;
    }

    public int getActionIndex() {
        return this.actionIndex;
    }

    public String getChattingKeyword() {
        return this.chattingKeyword;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("actionIndex", this.actionIndex);
        builder.append("type", this.type);
        builder.append("chattingKeyword", this.chattingKeyword);
        return builder.get();
    }
}
