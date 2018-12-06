package com.bdoemu.gameserver.model.creature.player.dye;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMListPalette;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Map;

public class DyeStorage extends JSONable {
    private final HashMap<Integer, Long> dyes;
    private final Player player;

    public DyeStorage(final Player player, final BasicDBObject dbObject) {
        this.dyes = new HashMap<>();
        this.player = player;
        final BasicDBList dyesDb = (BasicDBList) dbObject.get("dyes");
        for (final Object aDyesDb : dyesDb) {
            final BasicDBObject dyeDb = (BasicDBObject) aDyesDb;
            this.dyes.put(dyeDb.getInt("itemId"), dyeDb.getLong("count"));
        }
    }

    public DyeStorage(final Player player) {
        this.dyes = new HashMap<>();
        this.player = player;
    }

    public HashMap<Integer, Long> getDyes() {
        return this.dyes;
    }

    public void onLogin() {
        this.player.sendPacket(new SMListPalette(this.dyes));
    }

    public boolean removeDye(final int itemId, final long count) {
        Long currentCount = this.dyes.get(itemId);
        if (currentCount == null || currentCount < count) {
            return false;
        }
        currentCount -= count;
        if (currentCount <= 0L) {
            this.dyes.remove(itemId);
        } else {
            this.dyes.put(itemId, currentCount);
        }
        return true;
    }

    public void addDye(final int itemId, final long count) {
        this.dyes.merge(itemId, count, (a, b) -> a + b);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList dyesDb = new BasicDBList();
        for (final Map.Entry<Integer, Long> entry : this.dyes.entrySet()) {
            final BasicDBObject dyeDb = new BasicDBObject();
            dyeDb.append("itemId", entry.getKey());
            dyeDb.append("count", entry.getValue());
            dyesDb.add((Object) dyeDb);
        }
        builder.append("dyes", dyesDb);
        return builder.get();
    }
}
