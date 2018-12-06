package com.bdoemu.core.idFactory;

import com.bdoemu.commons.idfactory.IDFactory;
import com.bdoemu.commons.idfactory.IDStorage;
import com.bdoemu.core.startup.StartupComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StartupComponent("Database")
public class GameServerIDFactory extends IDFactory<GSIDStorageType> {
    private static final Logger log = LoggerFactory.getLogger(GameServerIDFactory.class);

    public static GameServerIDFactory getInstance() {
        return Holder.INSTANCE;
    }

    public void init() {
        for (final GSIDStorageType storageType : GSIDStorageType.values()) {
            this.storages.put(storageType, new IDStorage(storageType.getMinId(), storageType.getMaxId(), storageType.getSegment(), storageType.name()));
        }
    }

    public void report() {
        for (final GSIDStorageType storageType : GSIDStorageType.values()) {
            final IDStorage storage = this.storages.get(storageType);
            GameServerIDFactory.log.info("Init {} objectIdTypes used count: {}", storageType, storage.getUsedCount());
        }
    }

    private static class Holder {
        static final GameServerIDFactory INSTANCE = new GameServerIDFactory();
    }
}
