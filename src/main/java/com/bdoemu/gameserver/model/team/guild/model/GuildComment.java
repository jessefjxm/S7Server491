// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild.model;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuildComment extends JSONable {
    private static DateFormat dateFormat;

    static {
        GuildComment.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    private final int objectId;
    private String registerDate;
    private String content;
    private String family;

    public GuildComment(final String family, final String content) {
        this.objectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.GuildComments);
        this.family = family;
        this.content = content;
        this.registerDate = GuildComment.dateFormat.format(new Date());
    }

    public GuildComment(final BasicDBObject dbObject) {
        this.objectId = dbObject.getInt("objectId");
        this.registerDate = dbObject.getString("registerDate");
        this.content = dbObject.getString("content");
        this.family = dbObject.getString("family");
    }

    public String getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(final String registerDate) {
        this.registerDate = registerDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getFamily() {
        return this.family;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("objectId", (Object) this.objectId);
        builder.append("registerDate", (Object) this.registerDate);
        builder.append("content", (Object) this.content);
        builder.append("family", (Object) this.family);
        return builder.get();
    }
}
