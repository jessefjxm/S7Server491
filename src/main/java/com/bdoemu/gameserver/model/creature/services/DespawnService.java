package com.bdoemu.gameserver.model.creature.services;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@StartupComponent("Service")
public class DespawnService extends APeriodicTaskService {
    private static class Holder {
        static final DespawnService INSTANCE = new DespawnService();
    }
    private ConcurrentHashMap<Integer, DeadBody> map;

    private DespawnService() {
        super(1L, TimeUnit.SECONDS);
        this.map = new ConcurrentHashMap<>();
    }

    public static DespawnService getInstance() {
        return Holder.INSTANCE;
    }

    public void run() {
        this.map.values().stream().filter(obj -> obj.getDespawnTime() <= System.currentTimeMillis()).forEach(this::despawnBody);
    }

    public boolean despawnBody(final DeadBody deadBody) {
        if (this.map.values().remove(deadBody)) {
            World.getInstance().deSpawn(deadBody, ERemoveActorType.DespawnDeadBody);
            if (!deadBody.isOnlyLoot())
                RespawnService.getInstance().putBody(deadBody);
            return true;
        }
        return false;
    }

    public void putBody(final DeadBody deadBody) {
        final CreatureTemplate template = deadBody.getTemplate();
        deadBody.setDespawnTime(System.currentTimeMillis() + template.getVanishTime());
        this.map.put(deadBody.getGameObjectId(), deadBody);
    }
}
