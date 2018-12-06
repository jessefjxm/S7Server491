package com.bdoemu.gameserver.model.creature.player.intimacy;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMUpdateIntimacy;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IntimacyHandler extends JSONable {
    private final ConcurrentHashMap<Integer, Integer> intimacyMap;
    private Player player;

    public IntimacyHandler(final Player player) {
        this.intimacyMap = new ConcurrentHashMap<>();
        this.player = player;
    }

    public IntimacyHandler(final Player player, final BasicDBObject dbObject) {
        this(player);
        final BasicDBList intimacyListDB = (BasicDBList) dbObject.get("intimacyList");
        for (Object anIntimacyListDB : intimacyListDB) {
            final BasicDBObject intimacyDB = (BasicDBObject) anIntimacyListDB;
            this.intimacyMap.put(intimacyDB.getInt("npcId"), intimacyDB.getInt("level"));
        }
    }

    public Map<Integer, Integer> getIntimacyMap() {
        return this.intimacyMap;
    }

    public void updateIntimacy(final int creatureId, final int sessionId, final int count) {
        this.intimacyMap.compute(creatureId, (integer, integer2) -> (integer2 == null) ? count : (integer2 + count));
        this.player.sendPacket(new SMUpdateIntimacy(creatureId, count, sessionId));
    }

    public boolean checkOnContactIntimacy(final int creatureId, final int gameObjectId) {
        if (this.intimacyMap.putIfAbsent(creatureId, 1) == null) {
            this.player.sendPacket(new SMUpdateIntimacy(creatureId, 1, gameObjectId));
            return true;
        }
        return false;
    }

    public boolean contains(final int npcId) {
        return this.intimacyMap.containsKey(npcId);
    }

    public int getIntimacy(final int npcId) {
        final Integer intimacy = this.intimacyMap.get(npcId);
        return (intimacy == null) ? 0 : intimacy;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList intimacyListDB = new BasicDBList();
        for (final Map.Entry<Integer, Integer> entry : this.intimacyMap.entrySet()) {
            final BasicDBObject obj = new BasicDBObject();
            obj.append("npcId", entry.getKey());
            obj.append("level", entry.getValue());
            intimacyListDB.add(obj);
        }
        builder.append("intimacyList", intimacyListDB);
        return builder.get();
    }
}
