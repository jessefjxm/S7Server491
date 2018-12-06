package com.bdoemu.gameserver.model.creature.services;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.xml.XmlDataLoader;
import com.bdoemu.core.configs.AIConfig;
import com.bdoemu.gameserver.dataholders.ContentsGroupOptionData;
import com.bdoemu.gameserver.dataholders.binary.CollectSpawnData;
import com.bdoemu.gameserver.dataholders.xml.RegionSpawnData;
import com.bdoemu.gameserver.dataholders.xml.SpawnPlacementData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static com.bdoemu.core.configs.AIConfig.SPAWN_AI;
import static com.bdoemu.core.configs.AIConfig.SPAWN_NPC;

@Reloadable(name = "all", group = "spawn")
public class SpawnService implements IReloadable {
    private static class Holder {
        static final SpawnService INSTANCE = new SpawnService();
    }
    private static final Logger log = LoggerFactory.getLogger(SpawnService.class);
    private static List<SpawnPlacementT> spawns = new ArrayList<>();
    private static HashMap<Long, SpawnPlacementT> spawnsStatic = new HashMap<>();
    private List<Creature> customTimeSpawns;

    public SpawnService() {
        this.customTimeSpawns = new CopyOnWriteArrayList<>();
    }

    public static SpawnService getInstance() {
        return Holder.INSTANCE;
    }

    public void spawnAll() {
        if (!AIConfig.SPAWN_AI) {
            log.info("Monster spawn has been disabled, but NPC's will still spawn.");
            return;
        }

        int disabledOnBoot = 0;
        int npcCount = 0;
        int monsterCount = 0;
        int collectCount = 0;
        int vehicleCount = 0;
        int gateCount = 0;
        int timeBasedNpcCount = 0;
        int timeBasedNpcSpawnedCount = 0;
        int disabledByContentOptions = 0;
        int totalProcessed = 0;
        int lastPercent = 0;
        boolean shownPercent = false;
        for (final SpawnPlacementT spawnPlacement : SpawnService.spawns) {
            ++totalProcessed;
            int percent = (totalProcessed * 100) / SpawnService.spawns.size();
            if ((!shownPercent || lastPercent != percent) && (percent % 10 == 0 || percent > 90)) {
                log.info("Loading AI [{}%]", percent);
                lastPercent = percent;
                shownPercent = true;
            }
            final Creature creature = Creature.newCreature(spawnPlacement);
            if (creature != null) {
                if (!AIConfig.SPAWN_AI && !creature.isNpc())
                    continue;

                if (!creature.isCollect() && creature.getActionStorage() == null) {
                    continue;
                }
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(creature.getTemplate().getContentsGroupKey())) {
                    if (spawnPlacement.isUseCustomSpawnTimeData()) {
                        this.customTimeSpawns.add(creature);
                        if (GameTimeService.getInstance().isCurrentHourBetween(spawnPlacement.getSpawnStartHourFromSpawn(), spawnPlacement.getSpawnEndHourFromSpawn())) {
                            World.getInstance().spawn(creature, true, false);
                            ++timeBasedNpcSpawnedCount;
                        }
                        ++timeBasedNpcCount;
                    } else if (!spawnPlacement.doSpawnOnBoot()) {
                        DeadBody queuedSpawnBody = new DeadBody(null, creature, null);
                        RespawnService.getInstance().putBody(queuedSpawnBody);
                        ++disabledOnBoot;
                    } else {
                        World.getInstance().spawn(creature, true, false);
                        if (creature.isMonster()) {
                            ++monsterCount;
                        } else if (creature.isNpc()) {
                            ++npcCount;
                        } else if (creature.isCollect()) {
                            ++collectCount;
                        } else if (creature.isVehicle()) {
                            ++vehicleCount;
                        } else {
                            if (!creature.isGate()) {
                                continue;
                            }
                            ++gateCount;
                        }
                    }
                } else {
                    ++disabledByContentOptions;
                }
            }
        }
        log.info("Spawned [{}] npc`s and [{} of {}] time based npc`s", npcCount, timeBasedNpcSpawnedCount, timeBasedNpcCount);
        log.info("Spawned [{}] collects.", collectCount);
        log.info("Spawned [{}] monsters.", monsterCount);
        log.info("Spawned [{}] gates.", gateCount);
        log.info("Spawned [{}] vehicles.", vehicleCount);
        log.info("Spawned [{}] to spawn queue.", disabledOnBoot);
        if (disabledByContentOptions > 0) {
            log.info("Disabled {} spawn's due ContentsGroupOptionData settings.", disabledByContentOptions);
        }
    }

    public void notifyHourChanged() {
        for (final Creature creature : this.customTimeSpawns) {
            final SpawnPlacementT spawnPlacement = creature.getSpawnPlacement();
            if (!GameTimeService.getInstance().isCurrentHourBetween(spawnPlacement.getSpawnStartHourFromSpawn(), spawnPlacement.getSpawnEndHourFromSpawn())) {
                if (!creature.isSpawned() || creature.getAi() == null) {
                    continue;
                }
                creature.getAi().HandleAtSpawnEndTime(creature, null);
            } else {
                if (creature.isSpawned()) {
                    continue;
                }
                World.getInstance().spawn(creature, true, false);
            }
        }
    }

    public void addSpawn(final SpawnPlacementT spawnPlacement) {
        SpawnService.spawns.add(spawnPlacement);
    }

    public void addSpawnStatic(final long staticKey, final SpawnPlacementT spawnPlacement) {
        SpawnService.spawnsStatic.put(staticKey, spawnPlacement);
    }

    public List<SpawnPlacementT> getSpawns() {
        return SpawnService.spawns;
    }

    public Map<Long, SpawnPlacementT> getSpawnsStatic() {
        return SpawnService.spawnsStatic;
    }

    public SpawnPlacementT getSpawnStatic(final long staticKey) {
        return SpawnService.spawnsStatic.get(staticKey);
    }

    public void spawn(final SpawnPlacementT template, final boolean isRespawn) {
        final Creature creature = Creature.newCreature(template);
        if (creature != null && creature.getActionStorage() != null) {
            World.getInstance().spawn(creature, true, isRespawn);
        }
    }

    public void reload() {
        SpawnService.spawns.clear();
        SpawnService.spawnsStatic.clear();
        this.customTimeSpawns.clear();
        for (final Creature creature : World.getInstance().getObjects()) {
            if (!creature.isPlayable()) {
                World.getInstance().deSpawn(creature, ERemoveActorType.None);
            }
        }
        XmlDataLoader.getInstance().load(RegionSpawnData.class);
        XmlDataLoader.getInstance().load(SpawnPlacementData.class);
        CollectSpawnData.getInstance().load();
        this.spawnAll();
    }
}
