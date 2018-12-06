package com.bdoemu.gameserver.model.alchemy;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlchemyRecordStorage extends JSONable {
    private final HashMap<Integer, List<AlchemyRecord>> alchemyRecords;

    public AlchemyRecordStorage(final BasicDBObject basicDBObject) {
        this.alchemyRecords = new HashMap<>();
        final BasicDBList alchemyMapDB = (BasicDBList) basicDBObject.get("alchemyMap");
        for (final Object anAlchemyMapDB : alchemyMapDB) {
            final BasicDBObject recordDB = (BasicDBObject) anAlchemyMapDB;
            final int cardId = recordDB.getInt("cardId");
            this.alchemyRecords.put(cardId, new ArrayList<>());
            final BasicDBList alchemyRecordDBList = (BasicDBList) recordDB.get("alchemyRecords");
            for (final Object anAlchemyRecordDBList : alchemyRecordDBList) {
                this.alchemyRecords.get(cardId).add(new AlchemyRecord((BasicDBObject) anAlchemyRecordDBList));
            }
        }
    }

    public AlchemyRecordStorage() {
        this.alchemyRecords = new HashMap<>();
    }

    public List<AlchemyRecord> getAlchemyRecords(final int cardId) {
        synchronized (this.alchemyRecords) {
            if (!this.alchemyRecords.containsKey(cardId)) {
                return null;
            }
            return new ArrayList<>(this.alchemyRecords.get(cardId));
        }
    }

    public void addNewAlchemyRecord(final int cardId, final AlchemyRecord alchemyRecord) {
        synchronized (this.alchemyRecords) {
            if (!this.alchemyRecords.containsKey(cardId)) {
                this.alchemyRecords.put(cardId, new ArrayList<>());
            }
            final List<AlchemyRecord> records = this.alchemyRecords.get(cardId);
            if (records.size() >= 10) {
                records.remove(0);
            }
            this.alchemyRecords.get(cardId).add(alchemyRecord);
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList alchemyMapDB = new BasicDBList();
        for (final Map.Entry<Integer, List<AlchemyRecord>> alchemyEntry : this.alchemyRecords.entrySet()) {
            final BasicDBList alchemyRecordDBList = new BasicDBList();
            for (final AlchemyRecord alchemyRecord : alchemyEntry.getValue()) {
                alchemyRecordDBList.add(alchemyRecord.toDBObject());
            }
            final BasicDBObject recordDB = new BasicDBObject("cardId", alchemyEntry.getKey());
            recordDB.append("alchemyRecords", alchemyRecordDBList);
            alchemyMapDB.add(recordDB);
        }
        builder.append("alchemyMap", alchemyMapDB);
        return builder.get();
    }
}
