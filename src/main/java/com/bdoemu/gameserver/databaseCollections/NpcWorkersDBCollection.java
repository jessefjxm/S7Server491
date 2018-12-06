package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

@DatabaseCollection
public class NpcWorkersDBCollection extends ADatabaseCollection<NpcWorker, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(NpcWorkersDBCollection.class);

    private NpcWorkersDBCollection(final Class<NpcWorker> clazz) {
        super(clazz, "npcWorkers");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.NpcWorker, "_id"));
    }

    public static NpcWorkersDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public ConcurrentHashMap<Long, NpcWorker> load(final Player player) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", player.getAccountId());
        return super.load(filter);
    }

    private static class Holder {
        static final NpcWorkersDBCollection INSTANCE = new NpcWorkersDBCollection(NpcWorker.class);
    }
}
