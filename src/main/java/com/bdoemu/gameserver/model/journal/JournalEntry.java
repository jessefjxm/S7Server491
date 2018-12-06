package com.bdoemu.gameserver.model.journal;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class JournalEntry extends JSONable {
    private EJournalEntryType type;
    private long date;
    private short param0;
    private short param1;
    private short param2;
    private short param3;
    private short param4;
    private short param5;
    private String param6;

    public JournalEntry() {
    }

    public JournalEntry(final BasicDBObject dbObject) {
        this.date = dbObject.getLong("date");
        this.type = EJournalEntryType.values()[dbObject.getInt("type")];
        this.setParam0((short) dbObject.getInt("param0"));
        this.setParam1((short) dbObject.getInt("param1"));
        this.setParam2((short) dbObject.getInt("param2"));
        this.setParam3((short) dbObject.getInt("param3"));
        this.setParam4((short) dbObject.getInt("param4"));
        this.setParam5((short) dbObject.getInt("param5"));
        this.setParam6(dbObject.getString("param6"));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("date", this.date);
        builder.append("type", this.type.ordinal());
        builder.append("param0", this.getParam0());
        builder.append("param1", this.getParam1());
        builder.append("param2", this.getParam2());
        builder.append("param3", this.getParam3());
        builder.append("param4", this.getParam4());
        builder.append("param5", this.getParam5());
        builder.append("param6", this.getParam6());
        return builder.get();
    }

    public EJournalEntryType getType() {
        return this.type;
    }

    public void setType(final EJournalEntryType type) {
        this.type = type;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(final long date) {
        this.date = date;
    }

    public short getParam0() {
        return this.param0;
    }

    public void setParam0(final short param0) {
        this.param0 = param0;
    }

    public short getParam1() {
        return this.param1;
    }

    public void setParam1(final short param1) {
        this.param1 = param1;
    }

    public short getParam2() {
        return this.param2;
    }

    public void setParam2(final short param2) {
        this.param2 = param2;
    }

    public short getParam3() {
        return this.param3;
    }

    public void setParam3(final short param3) {
        this.param3 = param3;
    }

    public short getParam4() {
        return this.param4;
    }

    public void setParam4(final short param4) {
        this.param4 = param4;
    }

    public short getParam5() {
        return this.param5;
    }

    public void setParam5(final short param5) {
        this.param5 = param5;
    }

    public String getParam6() {
        return this.param6;
    }

    public void setParam6(final String param6) {
        this.param6 = param6;
    }
}
