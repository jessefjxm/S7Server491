package com.bdoemu.gameserver.model.creature.collect;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.dataholders.CollectData;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.EmptyAggroList;
import com.bdoemu.gameserver.model.creature.collect.templates.CollectTemplate;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

import java.util.List;

public class Collect extends Creature {
    private final CollectTemplate collectTemplate;

    public Collect(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.collectTemplate = CollectData.getInstance().getTemplate(this.getCreatureId());
        this.aggroList = new EmptyAggroList();
    }

    public int getIndex() {
        return this.getSpawnPlacement().getIndex();
    }

    @Override
    public boolean isTimeToRespawn() {
        if (this.getRespawnTime() <= System.currentTimeMillis()) {
            SpawnService.getInstance().spawn(this.getSpawnPlacement(), true);
            return true;
        }
        return false;
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Collect, (long) this.getGameObjectId());
    }

    public CollectTemplate getCollectTemplate() {
        return this.collectTemplate;
    }

    @Override
    public void initAi() {
    }

    @Override
    public CreatureAI getAi() {
        return null;
    }

    @Override
    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        return false;
    }

    @Override
    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        return false;
    }

    @Override
    public ECharKind getCharKind() {
        return ECharKind.Collect;
    }
}
