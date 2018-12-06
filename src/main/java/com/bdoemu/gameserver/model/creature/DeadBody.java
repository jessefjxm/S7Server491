package com.bdoemu.gameserver.model.creature;

import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.PartyAggroInfo;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PickupDroppedItemEvent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.AddPartyItem;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GeoService;

import java.util.List;
import java.util.Map;

public class DeadBody extends Creature {
    private Creature owner;
    private DropBag dropBag;
    private AggroInfo aggroInfo;
    private boolean canRespawn;
    private int winnerGameObjId;
    private boolean isLootOnly;

    public DeadBody(final AggroInfo aggroInfo, final Creature owner, final Creature lastKiller) {
        super((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.DeadBody), owner.getTemplate(), new SpawnPlacementT(owner.getLocation()));
        this.canRespawn = false;
        this.winnerGameObjId = -1024;
        this.aggroInfo = aggroInfo;
        this.owner = owner;
        this.canRespawn = owner.canRespawn();
        isLootOnly = false;
        final Integer dropId = owner.getTemplate().getDropId();
        if (dropId != null && aggroInfo != null) {
            Creature killer = aggroInfo.getKiller();
            if (aggroInfo instanceof PartyAggroInfo) {
                final PartyAggroInfo partyAggroInfo = (PartyAggroInfo) aggroInfo;
                final IParty<Player> party = partyAggroInfo.getParty();
                if (party != null) {
                    killer = party.getLeader();
                }
            }
            if (killer != null && killer.isPlayer()) {
                this.dropBag = ItemMainGroupService.getDropBag(dropId, (Player) killer, this.getGameObjectId(), this.getCreatureId(), EDropBagType.DeadBody, RateConfig.MONSTER_DROP_RATE);
                this.winnerGameObjId = aggroInfo.getKillerSession();
            }
        }
        if (owner.isPlayer()) {
            if (lastKiller != null && lastKiller.isPlayer()) {
                this.winnerGameObjId = lastKiller.getGameObjectId();
            } else {
                this.winnerGameObjId = owner.getGameObjectId();
            }
        }
    }

    public DeadBody(final AggroInfo aggroInfo, final Creature owner, final Creature lastKiller, final Location loc, int dropId) {
        super((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.DeadBody), owner.getTemplate(), new SpawnPlacementT(loc));
        isLootOnly = false;
        this.canRespawn = false;
        this.winnerGameObjId = -1024;
        this.aggroInfo = aggroInfo;
        this.owner = owner;
        this.canRespawn = owner.canRespawn();
        if (dropId > 0 && aggroInfo != null) {
            Creature killer = aggroInfo.getKiller();
            if (aggroInfo instanceof PartyAggroInfo) {
                final PartyAggroInfo partyAggroInfo = (PartyAggroInfo) aggroInfo;
                final IParty<Player> party = partyAggroInfo.getParty();
                if (party != null) {
                    killer = party.getLeader();
                }
            }
            if (killer != null && killer.isPlayer()) {
                this.dropBag = ItemMainGroupService.getDropBag(dropId, (Player) killer, this.getGameObjectId(), this.getCreatureId(), EDropBagType.DeadBody, RateConfig.MONSTER_DROP_RATE);
                this.winnerGameObjId = aggroInfo.getKillerSession();
            }
        }
        if (owner.isPlayer()) {
            if (lastKiller != null && lastKiller.isPlayer()) {
                this.winnerGameObjId = lastKiller.getGameObjectId();
            } else {
                this.winnerGameObjId = owner.getGameObjectId();
            }
        }
    }

    public static Location getRandomLocation(final Creature owner) {
        double[] randomPoint = getRandomPoint(700);
        return new Location(
                owner.getLocation().getX() + randomPoint[0],
                owner.getLocation().getY() + randomPoint[1],
                GeoService.getInstance().validateZ(
                        owner.getLocation().getX() + randomPoint[0],
                        owner.getLocation().getY() + randomPoint[1],
                        owner.getLocation().getZ()),
                Math.cos(randomPoint[2]),
                Math.sin(randomPoint[2])
        );
    }

    public static double[] getRandomPoint(int radius) {
        double angle = Math.random() * Math.PI * 2.0;
        return new double[]{
                Math.cos(angle) * radius,
                Math.sin(angle) * radius,
                angle
        };
    }

    @Override
    public boolean canRespawn() {
        return this.canRespawn;
    }

    public void setOnlyLoot() {
        isLootOnly = true;
    }

    public boolean isOnlyLoot() {
        return isLootOnly;
    }

    public void pickupDrop(final Player player) {
        for (final Map.Entry<Integer, Item> entry : this.dropBag.getDropMap().entrySet()) {
            this.pickupDrop(player, entry.getKey(), entry.getValue().getCount());
        }
    }

    public void pickupDrop(final Player player, final int slotIndex, final long count) {
        if (this.dropBag == null || this.dropBag.isEmpty()) {
            return;
        }
        final Item dropItem = this.dropBag.getDropMap().get(slotIndex);
        if (dropItem != null) {
            if (this.aggroInfo != null) {
                if (System.currentTimeMillis() < this.getSpawnTime() + this.getTemplate().getLootAuthorityTime()) {
                    final List<Long> winners = this.aggroInfo.getWinners();
                    if (winners != null && !winners.contains(player.getObjectId())) {
                        return;
                    }
                }
                if (this.aggroInfo instanceof PartyAggroInfo) {
                    final PartyAggroInfo partyAggroInfo = (PartyAggroInfo) this.aggroInfo;
                    final IParty<Player> party = partyAggroInfo.getParty();
                    if (party != null && dropItem.getTemplate().getBasePriceForItemMarket() > 0) {
                        final List<Long> winners2 = partyAggroInfo.getWinners();
                        if (winners2.size() > 1 && party.onEvent(new AddPartyItem(party, player, dropItem, this.dropBag, count, slotIndex, winners2))) {
                            return;
                        }
                    }
                }
            }
            player.getPlayerBag().onEvent(new PickupDroppedItemEvent(player, this.dropBag, slotIndex, 0, count));
        }
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.DeadBody, (long) this.getGameObjectId());
    }

    public long getLootAuthorityTime() {
        return this.getSpawnTime() + this.getOwner().getTemplate().getLootAuthorityTime();
    }

    public long getWinnerPartyId() {
        return (this.aggroInfo == null) ? -2097151L : this.aggroInfo.getPartyId();
    }

    @Override
    public CreatureAI getAi() {
        return null;
    }

    @Override
    public void initAi() {
    }

    public DropBag getDropBag() {
        return this.dropBag;
    }

    public void setDropBag(final DropBag dropBag) {
        this.dropBag = dropBag;
    }

    public boolean hasCollections() {
        return this.owner.getTemplate().getCollectDropId() != null;
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
    public boolean notSee(final Creature object, final ERemoveActorType type, final boolean outOfRange) {
        return super.notSee(object, type, outOfRange);
    }

    @Override
    public Creature getOwner() {
        return this.owner;
    }

    public int getWinnerGameObjId() {
        return this.winnerGameObjId;
    }

    public boolean dropIsEmpty() {
        return this.dropBag == null || this.dropBag.isEmpty();
    }

    @Override
    public ECharKind getCharKind() {
        return ECharKind.Deadbody;
    }
}
