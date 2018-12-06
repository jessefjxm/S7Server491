// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.ai.deprecated.AIService;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroList;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.stats.containers.GateGameStats;
import com.bdoemu.gameserver.model.stats.containers.GateLifeStats;

import java.util.List;

public class Gate extends Creature {
    public Gate(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.actionStorage = new ActionStorage(this);
        this.setGameStats(new GateGameStats(this));
        this.setLifeStats(new GateLifeStats(this));
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new CreatureAggroList(this);
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Creatures, (long) this.getGameObjectId());
    }

    @Override
    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        return true;
    }

    @Override
    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        return true;
    }

    @Override
    public ECharKind getCharKind() {
        return ECharKind.Alterego;
    }
}
