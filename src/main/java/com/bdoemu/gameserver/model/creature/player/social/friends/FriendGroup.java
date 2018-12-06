package com.bdoemu.gameserver.model.creature.player.social.friends;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class FriendGroup extends JSONable {
    private String name;
    private int groupId;

    public FriendGroup(final int groupId) {
        this.name = "NEW GROUP";
        this.groupId = groupId;
    }

    public FriendGroup(final BasicDBObject dbObject) {
        this.name = dbObject.getString("name");
        this.groupId = dbObject.getInt("groupId");
    }

    public int getGroupId() {
        return this.groupId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("groupId", this.groupId);
        builder.append("name", this.name);
        return builder.get();
    }
}
