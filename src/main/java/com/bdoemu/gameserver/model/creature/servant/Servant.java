package com.bdoemu.gameserver.model.creature.servant;

import com.bdoemu.commons.collection.BitMask;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.*;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.ai.deprecated.AIService;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroList;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.AbstractAddItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantSealType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.creature.servant.itemPack.ServantBag;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.model.creature.servant.tasks.PetHungryTask;
import com.bdoemu.gameserver.model.creature.servant.templates.PetTemplate;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.skills.CreatureSkill;
import com.bdoemu.gameserver.model.skills.ServantSkill;
import com.bdoemu.gameserver.model.skills.ServantSkillList;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.CreatureBuffList;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.stats.containers.ServantGameStats;
import com.bdoemu.gameserver.model.stats.containers.ServantLifeStats;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Servant extends Playable {
    private static final Logger log = LoggerFactory.getLogger(Servant.class);
    private final CopyOnWriteArrayList<ActiveBuff> petBuffs;
    private long carriageObjectId;
    private long skillTrainingTime;
    private long matingTime;
    private long accountId;
    private int hope;
    private int matingChildId;
    private int townId;
    private String name;
    private long exp;
    private BitMask actionsBitMask;
    private BitMask equipSkillsBitMask;
    private EServantState servantState;
    private PetTemplate petTemplate;
    private ServantSetTemplate servantSetTemplate;
    private int hunger;
    private int deathCount;
    private int matingCount;
    private boolean isClearedDeathCount;
    private boolean isClearedMatingCount;
    private boolean isImprint;
    private long lastSummoned;
    private long interactionCoolTime;
    private long lastInteractedDate;
    private int formIndex;
    private long currentOwnerObjectId;
    private ConcurrentHashMap<ERidingSlotType, Player> currentRiders;
    private ServantBag servantBag;
    private boolean tamingSugar;
    private boolean isTamed;
    private boolean isSeized;
    private ScheduledFuture<?> petHungryTask;
    private ServantSkillList servantSkillList;
    private EAuctionRegisterType auctionRegisterType;

    public Servant(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.carriageObjectId = 0L;
        this.townId = -1;
        this.petBuffs = new CopyOnWriteArrayList<>();
        this.servantState = EServantState.Stable;
        this.formIndex = 0;
        this.currentOwnerObjectId = -1L;
        this.currentRiders = new ConcurrentHashMap<>();
        this.tamingSugar = false;
        this.isTamed = false;
        this.isSeized = false;
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.setBuffList(new CreatureBuffList(this));
        this.setGameStats(new ServantGameStats(this));
        this.setLifeStats(new ServantLifeStats(this));
        this.actionStorage = new ActionStorage(this);
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new CreatureAggroList(this);
    }

    public Servant(final PetTemplate template, final Player owner, final String name) {
        super(-1024, CreatureData.getInstance().getTemplate(template.getId()), null);
        this.carriageObjectId = 0L;
        this.townId = -1;
        this.petBuffs = new CopyOnWriteArrayList<>();
        this.servantState = EServantState.Stable;
        this.formIndex = 0;
        this.currentOwnerObjectId = -1L;
        this.currentRiders = new ConcurrentHashMap<>();
        this.tamingSugar = false;
        this.isTamed = false;
        this.isSeized = false;
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.setOwner(owner);
        this.petTemplate = template;
        this.formIndex = this.petTemplate.getDefaultActionIndex();
        this.accountId = owner.getAccountId();
        this.name = name;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Vehicle);
        this.hunger = template.getHunger();
        this.actionsBitMask = new BitMask(PetActionData.getInstance().getActionsCount());
        this.equipSkillsBitMask = new BitMask(PetEquipSkillAquireData.getInstance().getSkillsCount());
        this.addRandomActionBit();
    }

    public Servant(final Servant servant) {
        super(-1024, CreatureData.getInstance().getTemplate(servant.getCreatureId()), null);
        this.carriageObjectId = 0L;
        this.townId = -1;
        this.petBuffs = new CopyOnWriteArrayList<>();
        this.servantState = EServantState.Stable;
        this.formIndex = 0;
        this.currentOwnerObjectId = -1L;
        this.currentRiders = new ConcurrentHashMap<>();
        this.tamingSugar = false;
        this.isTamed = false;
        this.isSeized = false;
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.servantSetTemplate = ServantSetData.getInstance().getTemplate(servant.getCreatureId());
        this.hope = servant.getHope();
        this.deathCount = servant.getDeathCount();
        this.matingCount = servant.getMatingCount();
        this.isClearedDeathCount = servant.isClearedDeathCount();
        this.isClearedMatingCount = servant.isClearedMatingCount();
        this.isImprint = servant.isImprint();
        this.isSeized = servant.isSeized();
        this.name = servant.getName();
        this.formIndex = servant.getFormIndex();
        this.objectId = servant.getObjectId();
        this.level = servant.getLevel();
        this.setGameStats(new ServantGameStats(this));
        this.setLifeStats(new ServantLifeStats(this));
        applyGameStats(servant.getGameStats());
        this.servantBag = new ServantBag(this);
        this.servantSkillList = new ServantSkillList(this, servant.getServantSkillList().getSkills());
    }

    void applyGameStats(ServantGameStats stats) {
        // Apply base stats
        getGameStats().getHp().addBonus(stats.getHp().getIntMaxValue() - getGameStats().getHp().getIntMaxValue());
        getGameStats().getMp().addBonus(stats.getMp().getIntMaxValue() - getGameStats().getMp().getIntMaxValue());

        // Apply speed stats
        getGameStats().getAccelerationRate().addBonus(stats.getAccelerationRate().getIntMaxValue() - getGameStats().getAccelerationRate().getIntMaxValue());
        getGameStats().getMaxMoveSpeedRate().addBonus(stats.getMaxMoveSpeedRate().getIntMaxValue() - getGameStats().getMaxMoveSpeedRate().getIntMaxValue());
        getGameStats().getCorneringSpeedRate().addBonus(stats.getCorneringSpeedRate().getIntMaxValue() - getGameStats().getCorneringSpeedRate().getIntMaxValue());
        getGameStats().getBrakeSpeedRate().addBonus(stats.getBrakeSpeedRate().getIntMaxValue() - getGameStats().getBrakeSpeedRate().getIntMaxValue());
    }

    public Servant(final ServantTemplate template, final Player owner, final String name) {
        super(-1024, CreatureData.getInstance().getTemplate(template.getId()), null);
        this.carriageObjectId = 0L;
        this.townId = -1;
        this.petBuffs = new CopyOnWriteArrayList<>();
        this.servantState = EServantState.Stable;
        this.formIndex = 0;
        this.currentOwnerObjectId = -1L;
        this.currentRiders = new ConcurrentHashMap<>();
        this.tamingSugar = false;
        this.isTamed = false;
        this.isSeized = false;
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.setOwner(owner);
        this.servantSetTemplate = ServantSetData.getInstance().getTemplate(template.getId());
        this.matingCount = this.servantSetTemplate.getMatingCount();
        this.name = name;
        this.formIndex = this.servantSetTemplate.getFormIndex();
        this.accountId = owner.getAccountId();
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Vehicle);
        this.setGameStats(new ServantGameStats(this));
        this.setLifeStats(new ServantLifeStats(this));
        this.servantBag = new ServantBag(this);
        this.servantSkillList = new ServantSkillList(this);
    }

    public Servant(final BasicDBObject dbObject) {
        super(-1024, CreatureData.getInstance().getTemplate(dbObject.getInt("creatureId")), new SpawnPlacementT(new Location((BasicDBObject) dbObject.get("location"))));
        this.carriageObjectId = 0L;
        this.townId = -1;
        this.petBuffs = new CopyOnWriteArrayList<>();
        this.servantState = EServantState.Stable;
        this.formIndex = 0;
        this.currentOwnerObjectId = -1L;
        this.currentRiders = new ConcurrentHashMap<>();
        this.tamingSugar = false;
        this.isTamed = false;
        this.isSeized = false;
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.objectId = dbObject.getLong("_id");
        this.carriageObjectId = dbObject.getLong("carriageObjectId");
        this.townId = dbObject.getInt("townId", 0);
        this.name = dbObject.getString("name");
        this.level = dbObject.getInt("level");
        this.exp = dbObject.getLong("exp");
        this.servantState = EServantState.valueOf(dbObject.getInt("servantState"));
        this.auctionRegisterType = EAuctionRegisterType.valueOf(dbObject.getInt("auctionRegisterType", 0));
        this.accountId = dbObject.getLong("accountId", 0L);
        this.hunger = dbObject.getInt("hunger");
        this.deathCount = dbObject.getInt("deathCount");
        this.matingCount = dbObject.getInt("matingCount");
        this.isClearedDeathCount = dbObject.getBoolean("isClearedDeathCount", false);
        this.isClearedMatingCount = dbObject.getBoolean("isClearedMatingCount", false);
        this.isImprint = dbObject.getBoolean("isImprint", false);
        this.isSeized = dbObject.getBoolean("isSeized", false);
        this.currentOwnerObjectId = dbObject.getLong("currentOwnerObjectId");
        this.servantSetTemplate = ServantSetData.getInstance().getTemplate(dbObject.getInt("creatureId"));
        this.skillTrainingTime = dbObject.getLong("skillTrainingTime", 0L);
        this.matingTime = dbObject.getLong("matingTime", 0L);
        this.interactionCoolTime = dbObject.getLong("interactionCoolTime", 0L);
        this.lastInteractedDate = dbObject.getLong("lastInteractedDate", 0L);
        this.hope = dbObject.getInt("hope", 0);
        this.matingChildId = dbObject.getInt("matingChildId", 0);
        if (this.servantSetTemplate != null) {
            this.setGameStats(new ServantGameStats(this));
            this.setLifeStats(new ServantLifeStats(this, (BasicDBObject) dbObject.get("lifeStats")));
            this.setBuffList(new CreatureBuffList(this));
            this.servantBag = new ServantBag((BasicDBObject) dbObject.get("servantBag"), this);
            final ServantEquipments servantEquipments = this.getEquipments();
            if (servantEquipments != null) {
                servantEquipments.equipItems();
            }
            if (dbObject.containsField("servantSkillList")) {
                this.servantSkillList = new ServantSkillList(this, (BasicDBObject) dbObject.get("servantSkillList"));
            } else {
                this.servantSkillList = new ServantSkillList(this);
            }
        } else {
            this.petTemplate = PetData.getInstance().getTemplate((dbObject.getInt("id", 0) != 0) ? dbObject.getInt("id") : dbObject.getInt("creatureId"));
            this.actionsBitMask = new BitMask(PetActionData.getInstance().getActionsCount());
            this.equipSkillsBitMask = new BitMask(PetEquipSkillAquireData.getInstance().getSkillsCount());
            this.actionsBitMask.setMask(dbObject.getLong("actionBits", 0L));
            this.equipSkillsBitMask.setMask(dbObject.getLong("equipSkillBits", 0L));
        }
        this.setFormIndex(dbObject.getInt("formIndex"));
    }

    public boolean hasServantSkill(final int skillId) {
        return this.servantSkillList != null && this.servantSkillList.containsSkill(skillId);
    }

    public int getSkillExp(final int skillId) {
        return (this.servantSkillList != null) ? this.servantSkillList.getSkillExp(skillId) : 0;
    }

    public boolean isCannotChange(final int skillId) {
        return this.servantSkillList != null && this.servantSkillList.isCannotChange(skillId);
    }

    public List<Servant> getLinkedServants() {
        if (this.getTemplate().getVehicleType().isCarriage() && this.getOwner() != null) {
            return this.getOwner().getServantController().getServants(EServantType.Vehicle).stream().filter(s -> s.getCarriageObjectId() == this.objectId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public EAuctionRegisterType getAuctionRegisterType() {
        return this.auctionRegisterType;
    }

    public void setAuctionRegisterType(final EAuctionRegisterType auctionRegisterType) {
        this.auctionRegisterType = auctionRegisterType;
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Creatures, this.getGameObjectId());
    }

    public void unSeal(final Location location, final boolean fromStable, final Player owner) {
        this.setServantState(EServantState.Field);
        this.setLocation(location);
        this.setOwner(owner);
        switch (this.getServantType()) {
            case Pet: {
                if (fromStable) {
                    this.getOwner().sendBroadcastItSelfPacket(new SMActivePetPublicInfo(Collections.singletonList(this), EPacketTaskType.Update));
                    this.getOwner().sendPacket(new SMActivePetPrivateInfo(Collections.singletonList(this)));
                }
                this.petHungryTask = ThreadPool.getInstance().scheduleGeneralAtFixedRate((Runnable) new PetHungryTask(this), 6L, 6L, TimeUnit.MINUTES);
                this.applyEquipSkills();
                break;
            }
            case Ship:
            case Vehicle: {
                this.recalculateBasicCacheCount();
                this.gameObjectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures);
                this.currentOwnerObjectId = this.getOwner().getObjectId();
                for (final Servant linkedServant : this.getLinkedServants()) {
                    linkedServant.setCurrentOwnerObjectId(this.getOwner().getObjectId());
                }
                this.lastSummoned = GameTimeService.getServerTimeInMillis();
                this.setSpawnPlacement(new SpawnPlacementT(location));
                if (this.getBuffList() == null) {
                    this.setBuffList(new CreatureBuffList(this));
                }
                this.actionStorage = new ActionStorage(this);
                this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
                this.aggroList = new CreatureAggroList(this);
                World.getInstance().spawn(this, true, false);
                this.getOwner().sendPacket(new SMEnterServantComplete(this));
                this.getOwner().sendPacket(new SMSetServantLevels(this));
                this.getOwner().sendPacket(new SMServantEquipItemList(this));
                this.getOwner().sendPacket(new SMSetCharacterStats(this));
                this.sendBroadcastItSelfPacket(new SMSetServantStats(this));
                this.getOwner().sendBroadcastItSelfPacket(new SMSetCharacterPublicPoints(this));
                this.getOwner().sendBroadcastItSelfPacket(new SMSetCharacterRelatedPoints(this));
                this.getOwner().sendPacket(new SMAddItemToInventory(this.getGameObjectId(), this.servantBag.getInventory().getItemMap().values(), EPacketTaskType.Add));
                this.getOwner().sendPacket(new SMServantSkillList(this));
                this.getOwner().getSummonStorage().addSummon(this);
                this.getOwner().sendPacket(new SMUnsealServant(this));
                this.getOwner().sendPacket(new SMSetMyServantPoints(this));
                if (this.getAi() != null) {
                    this.getAi().HandleCallSummon(this.getOwner(), null);
                }
                if (this.getAi() == null) {
                    break;
                }
                if (fromStable) {
                    this.getAi().HandleCallSummon(this.getOwner(), null);
                    break;
                }
                this.getAi().HandleParkingHorse(this.getOwner(), null);
                break;
            }
        }
    }

    public void seal(final EServantSealType servantSealType) {
        final Player owner = this.getOwner();
        if (owner == null) {
            Servant.log.warn("Trying seal Servant " + this.toString() + " without owner!");
            return;
        }
        switch (this.getServantType()) {
            case Pet: {
                this.setServantState(servantSealType.isLogout() ? EServantState.Field : EServantState.Stable);
                owner.sendBroadcastItSelfPacket(new SMSealPet(this.getOwnerGameObjId(), this.getObjectId()));
                owner.sendPacket(new SMDeactivePetInfo(Collections.singletonList(this), EPacketTaskType.Remove));
                if (this.petHungryTask != null) {
                    this.petHungryTask.cancel(true);
                    this.petHungryTask = null;
                }
                this.unapplyEquipSkills();
                break;
            }
            case Ship:
            case Vehicle: {
                this.recalculateBasicCacheCount();
                this.lastSummoned = 0L;
                ERemoveActorType removeActorType = null;
                switch (servantSealType) {
                    case NORMAL: {
                        this.setServantState(EServantState.Stable);
                        removeActorType = ERemoveActorType.SealServant;
                        this.currentOwnerObjectId = -1L;
                        owner.getSummonStorage().removeSummon(this);
                        for (final Servant linkedServant : this.getLinkedServants()) {
                            linkedServant.setRegionId(this.getRegionId());
                            linkedServant.setCurrentOwnerObjectId(-1L);
                        }
                        owner.sendPacket(new SMListServantInfo(this, EPacketTaskType.Update));
                        owner.sendBroadcastItSelfPacket(new SMSealServant(this));
                        this.unMountAll();
                        break;
                    }
                    case LOGOUT: {
                        this.setServantState(EServantState.Field);
                        removeActorType = ERemoveActorType.DespawnSummon;
                        this.unMountAll();
                        break;
                    }
                    case KILLED: {
                        this.setServantState(EServantState.Coma);
                        removeActorType = ERemoveActorType.DespawnMonster;
                        ++this.deathCount;
                        owner.sendPacket(new SMListServantInfo(this, EPacketTaskType.Update));
                        break;
                    }
                    case TAMING: {
                        this.setServantState(EServantState.Stable);
                        removeActorType = ERemoveActorType.DespawnTamedServant;
                        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Vehicle);
                        this.servantBag = new ServantBag(this);
                        this.servantSetTemplate = ServantSetData.getInstance().getTemplate(this.getCreatureId());
                        this.servantSkillList = new ServantSkillList(this);
                        this.formIndex = this.servantSetTemplate.getFormIndex();
                        this.matingCount = this.servantSetTemplate.getMatingCount();
                        owner.getServantController().setTameServant(null);
                        this.getGameStats().getHp().fill();
                        this.getGameStats().getMp().fill();
                        this.currentOwnerObjectId = -1L;
                        owner.getSummonStorage().removeSummon(this);
                        owner.sendPacket(new SMListServantInfo(this, EPacketTaskType.Update));
                        owner.sendPacket(new SMTamingServant(null));
                        owner.getLifeExperienceStorage().getLifeExperience(ELifeExpType.Taming).addExp(owner, ServantConfig.TAMING_LIFE_EXPERIENCE);
                        break;
                    }
                }
                if (owner.hasGuild()) {
                    owner.getGuild().sendBroadcastPacket(new SMGuildVehicleInfo(this));
                }
                World.getInstance().deSpawn(this, removeActorType);
                break;
            }
        }
    }

    public ConcurrentHashMap<ERidingSlotType, Player> getCurrentRiders() {
        return this.currentRiders;
    }

    public boolean mount(final Player rider, final ERidingSlotType ridingSlotType) {
        int vehicleSeatCount = this.getTemplate().getVehicleSeatCount();
        if (this.hasTwoSeater()) {
            ++vehicleSeatCount;
        }
        if (getAi() != null && !this.currentRiders.values().contains(rider) && this.currentRiders.size() < vehicleSeatCount && this.currentRiders.putIfAbsent(ridingSlotType, rider) == null) {
            rider.setCurrentVehicle(this, ridingSlotType);
            this.getAi().HandleOnResetAI(rider, null);
            this.getAi().HandleRideOn(rider, null);
            this.getAi().Handle_RideOn(rider, null);
            return true;
        }
        return false;
    }

    public boolean hasTwoSeater() {
        return this.servantSkillList != null && this.servantSkillList.containsSkill(30);
    }

    public void unMountAll() {
        this.currentRiders.forEach((ridingSlotType, player) -> this.unMount(player));
    }

    public void unMount(final Player rider) {
        if (this.currentRiders.values().remove(rider)) {
            rider.setCurrentVehicle(null, ERidingSlotType.None);
            if (rider == this.getOwner()) {
                this.getAi().HandleRideOff(rider, null);
                this.getAi().HandleRideOff_Lv1(rider, null);
            }
        }
    }

    public long getChargeUserEffectEndTime() {
        final Player myOwner = this.getOwner();
        if (myOwner != null) {
            return myOwner.getAccountData().getChargeUserStorage().getChargeUserEffectEndTime(EChargeUserType.DyeingPackage);
        }
        return -2L;
    }

    @Override
    public boolean isEnemy(final Creature creature) {
        if (creature == this) {
            return false;
        }
        final Creature myOwner = this.getOwner();
        if (myOwner != null) {
            return myOwner != creature && myOwner.isEnemy(creature);
        }
        switch (creature.getCharKind()) {
            case Player: {
                return true;
            }
            default: {
                return super.isEnemy(creature);
            }
        }
    }

    @Override
    public void onDie(final Creature attacker, final long actionHash) {
        if (!this.isSpawned()) {
            return;
        }
        int killerGameObjId = -1024;
        if (attacker != null) {
            killerGameObjId = attacker.getGameObjectId();
        }
        this.sendBroadcastPacket(new SMVehicleDropRider(this.gameObjectId, killerGameObjId));
        super.onDie(attacker, actionHash);
        if (this.getOwner() != null) {
            this.unMountAll();
            if (this.isTamed) {
                this.getOwner().getServantController().setTameServant(null);
                this.getOwner().sendPacket(new SMTamingServant(null));
            }
            if (this.getOwner().getServantController().getServant(this.getObjectId()) != null) {
                this.getOwner().sendPacket(new SMVehicleDropRider(this.gameObjectId, killerGameObjId));
                this.getOwner().sendPacket(new SMDieNonPlayer(this.gameObjectId, (killerGameObjId > 0) ? killerGameObjId : this.gameObjectId, actionHash));
                this.seal(EServantSealType.KILLED);
                return;
            }
        }
        ERemoveActorType removeActorType = ERemoveActorType.DespawnMonster;
        if (attacker != null && attacker.isPlayer() && ((Player) attacker).getEscort() == this) {
            removeActorType = ERemoveActorType.DespawnEscort;
        }
        World.getInstance().deSpawn(this, removeActorType);
    }

    @Override
    public void onLevelChange(boolean sendPacket) {
        switch (this.getServantType()) {
            case Pet: {
                this.addRandomActionBit();
                if (this.level == 10) {
                    final int skillIndex = PetEquipSkillAquireData.getInstance().getRandomSkillIndex(this.getPetTemplate().getEquipSkillAquireKey(), null);
                    if (skillIndex > 0) {
                        this.equipSkillsBitMask.setBit(skillIndex);
                        this.applyEquipSkills();
                    }
                    break;
                }
                break;
            }
            case Vehicle: {
                this.getGameStats().getHp().addBonus(Rnd.get(2, 4));
                this.getGameStats().getMp().addBonus(Rnd.get(4, 8));
                this.getGameStats().getAccelerationRate().addBonus(Rnd.get(2, 6) * 1000);
                this.getGameStats().getMaxMoveSpeedRate().addBonus(Rnd.get(2, 6) * 1000);
                this.getGameStats().getCorneringSpeedRate().addBonus(Rnd.get(2, 6) * 1000);
                this.getGameStats().getBrakeSpeedRate().addBonus(Rnd.get(2, 6) * 1000);
                if (sendPacket) {
                    this.sendBroadcastPacket(new SMSetCharacterWeight(this));
                    this.sendBroadcastPacket(new SMSetServantStats(this));
                    this.sendBroadcastPacket(new SMSetCharacterPublicPoints(this));
                    this.sendBroadcastPacket(new SMSetCharacterRelatedPoints(this));
                    this.sendBroadcastPacket(new SMSetCharacterPrivatePoints(this));
                }
                ServantSkill servantSkill = null;
                if (Rnd.get(1000000) <= this.getLevel() * ServantConfig.VEHICLE_LEARN_SKILL_BY_LEVEL) {
                    servantSkill = this.getServantSkillList().learnRndSkill(false);
                }
                if (servantSkill != null && sendPacket) {
                    this.getOwner().sendPacket(new SMSetServantSkillExp(this.getObjectId(), servantSkill));
                    break;
                }
                break;
            }
        }
    }

    @Override
    public boolean canRespawn() {
        final Player owner = this.getOwner();
        if (owner != null) {
            if (this.getTemplate().isTaming() && owner.getServantController().getServant(this.getObjectId()) == null) {
                return true;
            }
            if (owner.getSummonStorage().getSummons().contains(this)) {
                return false;
            }
        }
        return super.canRespawn();
    }

    @Override
    public boolean canDespawnOnDieOwner() {
        return false;
    }

    public boolean isTamingSugar() {
        return this.tamingSugar;
    }

    public void setTamingSugar(final boolean tamingSugar) {
        this.tamingSugar = tamingSugar;
    }

    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(final boolean isTamed) {
        this.isTamed = isTamed;
    }

    public int getMatingChildId() {
        return this.matingChildId;
    }

    public void setMatingChildId(final int matingChildId) {
        this.matingChildId = matingChildId;
    }

    public void recovery(final Player owner, final int townId) {
        if (this.getServantType() == EServantType.Vehicle && this.servantState == EServantState.Coma) {
            this.setServantState(EServantState.Stable);
            this.townId = townId;
        }
        this.getGameStats().getHp().fill();
        this.getGameStats().getMp().fill();
        owner.sendPacket(new SMRecoveryServant(this));
    }

    public BitMask getEquipSkillsBitMask() {
        return this.equipSkillsBitMask;
    }

    public BitMask getActionsBitMask() {
        return this.actionsBitMask;
    }

    private void addRandomActionBit() {
        final List<Integer> unusedActions = new ArrayList<Integer>();
        for (int index = 0; index < this.actionsBitMask.getMaxBitSize(); ++index) {
            if (!this.actionsBitMask.get(index)) {
                unusedActions.add(index);
            }
        }
        final Integer rndAction = Rnd.get(unusedActions);
        if (rndAction != null) {
            this.actionsBitMask.setBit(rndAction);
        }
    }

    public void applyEquipSkills() {
        this.unapplyEquipSkills();
        for (int index = 0; index < this.equipSkillsBitMask.getMaxBitSize(); ++index) {
            if (this.equipSkillsBitMask.get(index)) {
                final int skillNo = PetEquipSkillAquireData.getInstance().getSkillNoByIndex(index);
                final CreatureSkill skill = CreatureSkill.newSkill(skillNo);
                if (skill != null) {
                    this.petBuffs.addAll(SkillService.useSkill(this, skill.getSkillId(), null, Collections.singletonList(this.getOwner())));
                }
            }
        }
    }

    private void unapplyEquipSkills() {
        try {
            this.petBuffs.forEach(ActiveBuff::endEffect);
            this.petBuffs.clear();
        } catch (Exception exp) {
            System.out.println("Failed to unequip skills data: ");
            exp.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getExp() {
        return this.exp;
    }

    public void setExp(final long exp) {
        this.exp = exp;
    }

    public EServantType getServantType() {
        if (this.servantSetTemplate != null) {
            return this.servantSetTemplate.getServantType();
        }
        if (this.petTemplate != null) {
            return EServantType.Pet;
        }
        return EServantType.Vehicle;
    }

    @Override
    public int getRegionId() {
        return this.townId;
    }

    public void setRegionId(final int townId) {
        this.townId = townId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(final long accountId) {
        this.accountId = accountId;
    }

    public EServantState getServantState() {
        return this.servantState;
    }

    public void setServantState(final EServantState servantState) {
        this.servantState = servantState;
    }

    public int getHunger() {
        return this.hunger;
    }

    public void setHunger(final int hunger) {
        this.hunger = hunger;
    }

    public int getHope() {
        return this.hope;
    }

    public void setHope(final int hope) {
        this.hope = hope;
    }

    public int getMaxHunger() {
        return PetData.getInstance().getTemplate(this.getCreatureId()).getHunger();
    }

    public boolean isHungry() {
        return this.hunger < this.getMaxHunger();
    }

    public long getCarriageObjectId() {
        return this.carriageObjectId;
    }

    public void setCarriageObjectId(final long carriageObjectId) {
        this.carriageObjectId = carriageObjectId;
    }

    public int getDeathCount() {
        return this.deathCount;
    }

    public void setDeathCount(final int deathCount) {
        this.deathCount = deathCount;
    }

    public void addExp(final int addExp) {
        if (addExp <= 0) {
            return;
        }
        boolean levelUp = false;
        switch (this.getServantType()) {
            case Pet: {
                if (this.level < this.getPetTemplate().getExpTemplates().size()) {
                    long reqExp = this.getPetTemplate().getMaxExp(this.level);
                    final long finalExp = this.exp + addExp;
                    this.exp += addExp;
                    if (finalExp >= reqExp) {
                        ++this.level;
                        this.setExp(0L);
                        levelUp = true;
                        reqExp = this.getPetTemplate().getMaxExp(this.level);
                    } else {
                        this.setExp(finalExp);
                    }
                    if (levelUp) {
                        this.onLevelChange(true);
                    }
                    this.getOwner().sendBroadcastItSelfPacket(new SMSetServantLevels(this, reqExp, levelUp));
                    break;
                }
                break;
            }
            case Vehicle: {
                if (this.getServantTemplate() != null) {
                    int ratedExp = (int) (addExp * RateConfig.HORSE_RATE_EXP / 100.0f);
                    final int expStat = this.getOwner().getGameStats().getHorseEXP().getIntMaxValue() / 10000;
                    if (expStat > 0) {
                        ratedExp += (int) (ratedExp * expStat / 100.0f);
                    }
                    long reqExp2 = this.getServantTemplate().getRequireExp();
                    final long finalExp2 = this.exp + ratedExp;
                    if (ServantData.getInstance().getTemplate(this.getCreatureId(), this.level + 1) != null) {
                        if (finalExp2 >= reqExp2) {
                            ++this.level;
                            this.setExp(0L);
                            levelUp = true;
                            reqExp2 = this.getServantTemplate().getRequireExp();
                            this.getGameStats().getHp().fill();
                            this.getGameStats().getMp().fill();
                            this.getOwner().getLifeExperienceStorage().getLifeExperience(ELifeExpType.Taming).addExp(this.getOwner(), 295 + 45 * this.level);
                        } else {
                            this.setExp(finalExp2);
                        }
                        this.getOwner().sendBroadcastItSelfPacket(new SMSetServantLevels(this, reqExp2, levelUp));
                        if (levelUp) {
                            this.onLevelChange(true);
                        }
                    }
                    break;
                }
                Servant.log.error("Servant " + this.toString() + " hasn't ServantTemplate!");
                break;
            }
        }
    }

    public ServantTemplate getServantTemplate() {
        return ServantData.getInstance().getTemplate(this.getCreatureId(), this.getLevel());
    }

    public ServantSetTemplate getServantSetTemplate() {
        return this.servantSetTemplate;
    }

    public PetTemplate getPetTemplate() {
        return this.petTemplate;
    }

    public int getFormIndex() {
        return this.formIndex;
    }

    public void setFormIndex(final int formIndex) {
        this.formIndex = formIndex;
    }

    @Override
    public int getActionIndex() {
        if (this.servantSetTemplate != null) {
            if (this.formIndex == this.servantSetTemplate.getFormIndex()) {
                return this.servantSetTemplate.getActionIndex();
            }
            return FormData.getInstance().getActionIndexByFormId(this.formIndex);
        } else {
            if (this.petTemplate != null) {
                return (this.petTemplate.getDefaultActionIndex() == this.formIndex) ? 0 : this.formIndex;
            }
            return super.getActionIndex();
        }
    }

    public synchronized boolean addMatingCount(final int count) {
        if (this.matingCount + count < 0) {
            return false;
        }
        this.matingCount += count;
        return true;
    }

    public int getMatingCount() {
        return this.matingCount;
    }

    public void setMatingCount(final int matingCount) {
        this.matingCount = matingCount;
    }

    public boolean isClearedDeathCount() {
        return this.isClearedDeathCount;
    }

    public void setClearedDeathCount(final boolean clearedDeathCount) {
        this.isClearedDeathCount = clearedDeathCount;
    }

    public boolean isClearedMatingCount() {
        return this.isClearedMatingCount;
    }

    public void setClearedMatingCount(final boolean clearedMatingCount) {
        this.isClearedMatingCount = clearedMatingCount;
    }

    public boolean isImprint() {
        return this.isImprint;
    }

    public void setImprint(final boolean imprint) {
        this.isImprint = imprint;
    }

    public boolean isSeized() {
        return this.isSeized;
    }

    public void setSeized(final boolean seized) {
        this.isSeized = seized;
    }

    @Override
    public int getFunction() {
        return -1;
    }

    public boolean isOwnerMounted() {
        final Player owner = this.getOwner();
        return owner != null && this.currentRiders.values().contains(owner);
    }

    public boolean isPlayerMounted(Player player) {
        return player != null && currentRiders.values().contains(player);
    }

    @Override
    public Player getOwner() {
        return (Player) super.getOwner();
    }

    public long getSkillTrainingTime() {
        return (GameTimeService.getServerTimeInSecond() < this.skillTrainingTime) ? this.skillTrainingTime : -1L;
    }

    public void setSkillTrainingTime(final long skillTrainingTime) {
        this.skillTrainingTime = skillTrainingTime;
    }

    public long getMatingTime() {
        return (GameTimeService.getServerTimeInSecond() < this.matingTime) ? this.matingTime : -1L;
    }

    public void setMatingTime(final long matingTime) {
        this.matingTime = matingTime;
    }

    @Override
    public int getGameObjectId() {
        return (this.getServantType() == EServantType.Pet) ? this.getOwnerGameObjId() : super.getGameObjectId();
    }

    public long getLastSummoned() {
        return this.lastSummoned;
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
    public long getExistenceTime() {
        return GameTimeService.getServerTimeInSecond() - this.lastSummoned / 1000L;
    }

    @Override
    public long getPartyCache() {
        final Creature owner = this.getOwner();
        return (owner != null) ? owner.getPartyCache() : -2097153L;
    }

    @Override
    public ServantGameStats getGameStats() {
        return (ServantGameStats) super.getGameStats();
    }

    @Override
    public ServantLifeStats getLifeStats() {
        return (ServantLifeStats) super.getLifeStats();
    }

    @Override
    public AbstractAddItemPack getInventory() {
        return (this.servantBag != null) ? this.servantBag.getInventory() : null;
    }

    @Override
    public ServantEquipments getEquipments() {
        return (this.servantBag != null) ? this.servantBag.getEquipments() : null;
    }

    public ServantSkillList getServantSkillList() {
        return this.servantSkillList;
    }

    @Override
    public void sendPacket(final SendablePacket<GameClient> sp) {
    }

    public long getInteractedCoolTime() {
        final long diff = this.interactionCoolTime - GameTimeService.getServerTimeInMillis();
        return (diff < 0L) ? 0L : diff;
    }

    public void setInteractionCoolTime(final long interactionCoolTime) {
        this.interactionCoolTime = interactionCoolTime;
    }

    public long getLastInteractedDate() {
        return this.lastInteractedDate / 1000L;
    }

    public void setLastInteractedDate(final long lastInteractedDate) {
        this.lastInteractedDate = lastInteractedDate;
    }

    @Override
    public ECharKind getCharKind() {
        return ECharKind.Vehicle;
    }

    public void setCurrentOwnerObjectId(final long currentOwnerObjectId) {
        this.currentOwnerObjectId = currentOwnerObjectId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("servantType", this.getServantType().ordinal());
        builder.append("servantState", this.servantState.ordinal());
        builder.append("auctionRegisterType", this.auctionRegisterType.ordinal());
        builder.append("creatureId", this.getCreatureId());
        builder.append("name", this.name);
        builder.append("exp", this.exp);
        builder.append("level", this.level);
        builder.append("townId", this.townId);
        builder.append("carriageObjectId", this.carriageObjectId);
        if (this.getLifeStats() != null) {
            builder.append("lifeStats", this.getLifeStats().toDBObject());
        }
        if (this.getLocation() != null) {
            builder.append("location", this.getLocation().toDBObject());
        }
        if (this.getServantType() != EServantType.Pet) {
            builder.append("servantBag", this.servantBag.toDBObject());
            builder.append("servantSkillList", this.servantSkillList.toDBObject());
        } else {
            builder.append("actionBits", this.actionsBitMask.getMask());
            builder.append("equipSkillBits", this.equipSkillsBitMask.getMask());
        }
        builder.append("skillTrainingTime", this.skillTrainingTime);
        builder.append("interactionCoolTime", this.interactionCoolTime);
        builder.append("lastInteractedDate", this.lastInteractedDate);
        builder.append("matingTime", this.matingTime);
        builder.append("hope", this.hope);
        builder.append("matingChildId", this.matingChildId);
        builder.append("currentOwnerObjectId", this.currentOwnerObjectId);
        builder.append("hunger", this.hunger);
        builder.append("deathCount", this.deathCount);
        builder.append("matingCount", this.matingCount);
        builder.append("isClearedDeathCount", this.isClearedDeathCount);
        builder.append("isClearedMatingCount", this.isClearedMatingCount);
        builder.append("isImprint", this.isImprint);
        builder.append("isSeized", this.isSeized);
        builder.append("formIndex", this.formIndex);
        builder.append("equipSlotCacheCount", this.equipSlotCacheCount);
        builder.append("basicCacheCount", this.basicCacheCount);
        return builder.get();
    }
}
