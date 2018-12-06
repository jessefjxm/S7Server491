package com.bdoemu.gameserver.dataholders.binary;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.DebugConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.world.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StartupComponent("Data")
public class CollectSpawnData {
    private static final Logger log = LoggerFactory.getLogger((Class) CollectSpawnData.class);

    private CollectSpawnData() {
        this.load();
    }

    public static CollectSpawnData getInstance() {
        return Holder.INSTANCE;
    }

    public void load() {
        ServerInfoUtils.printSection("CollectSpawnData Loading");
        if (DebugConfig.DISABLE_COLLECTIONS_SPAWN) {
            CollectSpawnData.log.info("Collections spawn disabled duo debug config.");
            return;
        }
        int spawnedCount = 0;
        try (final FileBinaryReader reader = new FileBinaryReader("./data/static_data/binary/collectspawndata.bin")) {
            for (int sectorCount = reader.readD(), sectorIndex = 0; sectorIndex < sectorCount; ++sectorIndex) {
                final int sectorX = reader.readD();
                final int sectorY = reader.readD();
                final int sectorZ = reader.readD();
                for (int spawnCount = reader.readD(), spawnIndex = 0; spawnIndex < spawnCount; ++spawnIndex) {
                    final int npcId = reader.readD();
                    final int staticId = reader.readD();
                    final float x = reader.readF();
                    final float y = reader.readF();
                    final float z = reader.readF();
                    final int index = reader.readD();
                    final SpawnPlacementT spawnTemplate = new SpawnPlacementT(npcId, new Location(x, y, z), staticId, index, sectorX, sectorY, sectorZ);
                    SpawnService.getInstance().addSpawn(spawnTemplate);
                    ++spawnedCount;
                }
            }
            CollectSpawnData.log.info("Loaded {} Collection spawn's", spawnedCount);
        } catch (Exception e) {
            CollectSpawnData.log.error("Error while reading CollectSpawnData", e);
        }
    }

    private static class Holder {
        static final CollectSpawnData INSTANCE = new CollectSpawnData();
    }
}
