package com.bdoemu.gameserver.model.creature.monster;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.sendable.SMResetInstanceSummonActor;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.ai.deprecated.AIService;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroList;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.enums.ETribeType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.skills.buffs.CreatureBuffList;
import com.bdoemu.gameserver.model.stats.containers.MonsterGameStats;
import com.bdoemu.gameserver.model.stats.containers.MonsterLifeStats;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.List;

public class Monster extends Creature {
    private InstanceSummon instanceSummon;

    public Monster(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.actionStorage = new ActionStorage(this);
        this.setBuffList(new CreatureBuffList(this));
        this.setGameStats(new MonsterGameStats(this));
        this.setLifeStats(new MonsterLifeStats(this));
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new CreatureAggroList(this);
    }

    public boolean isInstanceSummon() {
        return this.instanceSummon != null;
    }

    public InstanceSummon getInstanceSummon() {
        return this.instanceSummon;
    }

    public void setInstanceSummon(final InstanceSummon instanceSummon) {
        this.instanceSummon = instanceSummon;
    }

    @Override
    public void onDie(final Creature attacker, final long actionHash) {
        if (!this.isSpawned()) {
            return;
        }
        super.onDie(attacker, actionHash);
        final IParty party = this.getParty();
        if (party != null)
            party.removeMember(this);
        World.getInstance().deSpawn(this, ERemoveActorType.DespawnMonster);
    }

    @Override
    public boolean notSee(final Creature object, final ERemoveActorType type, final boolean outOfRange) {
        return super.notSee(object, type, outOfRange);
    }

    @Override
    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        return true;
    }

    @Override
    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        return true;
    }

    @Override
    public MonsterGameStats getGameStats() {
        return (MonsterGameStats) super.getGameStats();
    }

    @Override
    public boolean isEnemy(final Creature creature) {
        if (creature == this)
            return false;

        final InstanceSummon instanceSummon = this.getInstanceSummon();
        if (instanceSummon != null)
            return instanceSummon.getOwners().contains(creature);

        final Creature myOwner = this.getOwner();
        if (myOwner != null && myOwner != this)
            return myOwner.isEnemy(creature);

        switch (creature.getCharKind()) {
            case Player : return !creature.isPlayer() || ((Player) creature).isReadyToPlay();
            case Npc    : return creature.getTemplate().getTribeType() == ETribeType.Human;
            case Vehicle: return creature.getOwner() != null;
            default     : return super.isEnemy(creature);
        }
    }

    @Override
    public long getPartyCache() {
        final Creature owner = this.getOwner();
        return (owner != null && owner != this) ? owner.getPartyCache() : -2097153L;
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        final InstanceSummon instanceSummon = this.getInstanceSummon();
        if (instanceSummon != null) {
            InstanceSummonService.getInstance().clear(instanceSummon);

            for (Player p : instanceSummon.getOwners())
                this.sendBroadcastPacket(new SMResetInstanceSummonActor(p));
        }
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Creatures, (long) this.getGameObjectId());
    }

    @Override
    public boolean canRespawn() {
        final Creature owner = this.getOwner();
        return (owner == null || !owner.getSummonStorage().getSummons().contains(this)) && !this.isInstanceSummon();
    }
}
