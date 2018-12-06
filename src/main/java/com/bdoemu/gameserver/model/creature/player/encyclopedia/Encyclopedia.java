package com.bdoemu.gameserver.model.creature.player.encyclopedia;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.EncyclopediaData;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Encyclopedia extends JSONable {
    private final EncyclopediaT template;
    private int count;
    private int maxItemSize;

    public Encyclopedia(final EncyclopediaT template, final int maxItemSize) {
        this.template = template;
        this.maxItemSize = maxItemSize;
    }

    public Encyclopedia(final BasicDBObject basicDBObject) {
        this.template = EncyclopediaData.getInstance().getTemplate(basicDBObject.getInt("typeKey"));
        this.count = basicDBObject.getInt("count");
        this.maxItemSize = basicDBObject.getInt("maxItemSize");
    }

    public EncyclopediaT getTemplate() {
        return this.template;
    }

    public int getKey() {
        return this.template.getKey();
    }

    public int getTypeKey() {
        return this.template.getTypeKey();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getMaxItemSize() {
        return this.maxItemSize;
    }

    public void setMaxItemSize(final int maxItemSize) {
        this.maxItemSize = maxItemSize;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("typeKey", this.getTypeKey());
        builder.append("count", this.count);
        builder.append("maxItemSize", this.maxItemSize);
        return builder.get();
    }
}
