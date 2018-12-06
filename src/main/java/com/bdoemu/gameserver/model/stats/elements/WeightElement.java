package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class WeightElement extends Element {
    public WeightElement(final BasicDBObject dbObject) {
        super(ElementEnum.EXPAND, dbObject.getInt("weight"));
    }

    public WeightElement(final int value) {
        super(ElementEnum.EXPAND, value);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("weight", this.getIntValue());
        return builder.get();
    }
}