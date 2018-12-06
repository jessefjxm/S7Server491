package com.bdoemu.gameserver.model.creature;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.AIConfig;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMDieNonPlayer;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.FieldBossData;
import com.bdoemu.gameserver.dataholders.KnowledgeLearningData;
import com.bdoemu.gameserver.dataholders.PartyExperienceOptionData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.ai.AIScript;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.IAggroList;
import com.bdoemu.gameserver.model.creature.agrolist.PartyAggroInfo;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Knowledge.templates.KnowledgeLearningT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.services.DespawnService;
import com.bdoemu.gameserver.model.creature.services.RespawnService;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.move.AMovementController;
import com.bdoemu.gameserver.model.move.MovementController;
import com.bdoemu.gameserver.model.skills.buffs.CreatureBuffList;
import com.bdoemu.gameserver.model.stats.containers.GameStats;
import com.bdoemu.gameserver.model.stats.containers.LifeStats;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.RegisterPartyWinnersEvent;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.model.world.region.Region;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.utils.StreamUtils;
import com.bdoemu.gameserver.worldInstance.World;
import com.bdoemu.gameserver.worldInstance.WorldMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Creature extends JSONable {
    private static final Logger log = LoggerFactory.getLogger(Creature.class);
    private AIScript _aiScript;


    protected CreatureAI ai;
    protected final SummonStorage summonStorage;
    protected long objectId;
    protected int gameObjectId;
    protected CreatureBuffList buffList;
    protected AMovementController movementController;
    protected IAggroList aggroList;
    protected ActionStorage actionStorage;
    protected IParty party;
    protected int level;
    private Location location;
    private volatile boolean spawned;
    private long spawnTime;
    private long despawnTime;
    private long respawnTime;
    private Creature owner;
    private GameStats<? extends Creature> gameStats;
    private LifeStats<? extends Creature> lifeStats;
    private long cache;
    private CreatureTemplate template;
    private SpawnPlacementT spawnPlacement;
    private boolean isCollectable;
    private boolean isDarkSpiritMonster;
    private Map<String, Object> variables;

    protected Creature(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        this.objectId       = -1L;
        this.cache          = -GameServerIDFactory.getInstance().nextId(GSIDStorageType.CACHE);
        this.gameObjectId   = gameObjectId;
        this.location       = new Location(spawnPlacement);
        this.spawnPlacement = spawnPlacement;
        this.template       = template;
        this.level          = template.getLevel();
        this.summonStorage  = new SummonStorage(this);
    }

    public static Creature newCreature(final SpawnPlacementT spawnPlacement) {
        final CreatureTemplate template = CreatureData.getInstance().getTemplate(spawnPlacement.getCreatureId());
        Creature creature = null;
        if (template != null) {
            switch (template.getCharKind()) {
                case Monster    : creature = new Monster((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures), template, spawnPlacement); break;
                case Npc        : creature = new Npc    ((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures), template, spawnPlacement); break;
                case Collect    : creature = new Collect((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Collect), template, spawnPlacement); break;
                case Vehicle    : creature = new Servant((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures), template, spawnPlacement); break;
                case Alterego   : creature = new Gate   ((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures), template, spawnPlacement); break;
            }
        }
        return creature;
    }

    public SummonStorage getSummonStorage() {
        return this.summonStorage;
    }

    public boolean isDeliver() {
        return false;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public int getGameObjectId() {
        return this.gameObjectId;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public long getSpawnTime() {
        return this.spawnTime;
    }

    public void setSpawnTime(final long spawnTime) {
        this.spawnTime = spawnTime;
    }

    public long getRespawnTime() {
        return this.respawnTime;
    }

    public void setRespawnTime(final long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public long getDespawnTime() {
        return this.despawnTime;
    }

    public void setDespawnTime(final long despawnTime) {
        this.despawnTime = despawnTime;
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public void setSpawned(final boolean spawned) {
        this.spawned = spawned;
    }

    public boolean isTimeToRespawn() {
        if (this.getRespawnTime() <= System.currentTimeMillis()) {
            SpawnService.getInstance().spawn(this.getOwner().getSpawnPlacement(), true);
            return true;
        }
        return false;
    }

    public boolean isCreatureVisible() {
        return true;
    }

    public boolean isEnemy(final Creature creature) {
        return false;
    }

    public ActionStorage getActionStorage() {
        return this.actionStorage;
    }

    public boolean setAction(final IAction action) {
        if (action != null && action.canAct()) {
            action.init();
            return true;
        }
        return false;
    }

    public void initAi() {
        if (AIConfig.USE_LEGACY_AI && this.ai != null) {
            this.ai.run();
        }
    }

    public boolean isDead() {
        return this.getGameStats() != null && this.getGameStats().getHp().isDead();
    }

    public AIScript getAiScript() {
        return _aiScript;
    }

    public void setAiScript(AIScript script) {
        _aiScript = script; // TODO: Remove me after full rewrite of AI.
    }

    public CreatureAI getAi() {
        return this.ai;
    }

    public int getLevel() {
        return this.level;
    }

    public IAggroList getAggroList() {
        return this.aggroList;
    }

    public long getCache() {
        return this.cache;
    }

    public ECharKind getCharKind() {
        return this.template.getCharKind();
    }

    public GameStats<? extends Creature> getGameStats() {
        return this.gameStats;
    }

    protected void setGameStats(final GameStats<? extends Creature> gameStats) {
        this.gameStats = gameStats;
    }

    public LifeStats<? extends Creature> getLifeStats() {
        return this.lifeStats;
    }

    protected void setLifeStats(final LifeStats<? extends Creature> lifeStats) {
        this.lifeStats = lifeStats;
    }

    public CreatureBuffList getBuffList() {
        return this.buffList;
    }

    protected void setBuffList(final CreatureBuffList buffList) {
        this.buffList = buffList;
    }

    public abstract boolean see(final Creature p0, final int p1, final int p2, final boolean p3, final boolean p4);

    public abstract boolean see(final List<? extends Creature> p0, final int p1, final int p2, final ECharKind p3);

    public boolean notSee(final Creature object, final ERemoveActorType type, final boolean outOfRange) {
        return true;
    }

    public boolean notSee(final List<? extends Creature> object, final ERemoveActorType type, final boolean outOfRange) {
        return true;
    }

    public boolean canSee(final Creature object) {
        final GameSector selfGameSector = this.getLocation().getGameSector();
        final GameSector objectGameSector = object.getLocation().getGameSector();
        for (int xx = objectGameSector.getX() - WorldMap.MAP_SUBSECTOR_SIZE; xx <= objectGameSector.getX() + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = objectGameSector.getY() - WorldMap.MAP_SUBSECTOR_SIZE; yy <= objectGameSector.getY() + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector gameSector = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (gameSector != null) {
                    if (gameSector == selfGameSector) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Region getRegion() {
        return this.getLocation().getRegion();
    }

    public void setRegion(final Region region) {
        if (region != this.getRegion()) {
            this.getLocation().setRegion(region);
        }
    }

    public int getRegionId() {
        final Region regionTemplate = this.getRegion();
        if (regionTemplate != null) {
            return regionTemplate.getTemplate().getRegionId();
        }
        return -1;
    }

    public SpawnPlacementT getSpawnPlacement() {
        return this.spawnPlacement;
    }

    protected void setSpawnPlacement(final SpawnPlacementT spawnPlacement) {
        this.spawnPlacement = spawnPlacement;
    }

    public long getExistenceTime() {
        return 0L;
    }

    public long getGuildCache() {
        final Creature owner = this.getOwner();
        return (owner != null && owner != this) ? owner.getGuildCache() : this.getCache();
    }

    public long getPartyCache() {
        return this.getCache();
    }

    public EGuildMemberRankType getGuildMemberRankType() {
        final Creature owner = this.getOwner();
        return (owner != null && owner != this) ? owner.getGuildMemberRankType() : EGuildMemberRankType.None;
    }

    public CreatureTemplate getTemplate() {
        return this.template;
    }

    public int getCreatureId() {
        return this.template.getCreatureId();
    }

    public int getCharacterGroup() {
        return this.template.getCharacterGroup();
    }

    public int getActionIndex() {
        return this.spawnPlacement.getActionIndex();
    }

    public int getFunction() {
        return this.spawnPlacement.getFunction();
    }

    public int getDialogIndex() {
        return this.spawnPlacement.getDialogIndex();
    }

    public String getName() {
        return this.template.getDisplayName();
    }

    public void setIsCollectable(final boolean isCollectable) {
        this.isCollectable = isCollectable;
    }

    public boolean isCollectable() {
        return this.isCollectable;
    }

    public IParty getParty() {
        return this.party;
    }

    public void setParty(final IParty party) {
        this.party = party;
    }

    public boolean hasParty() {
        return this.party != null;
    }

    public Creature getOwner() {
        if (this.getParty() != null) {
            return this.getParty().getLeader();
        }
        return this.owner;
    }

    public void setOwner(final Creature owner) {
        this.owner = owner;
    }

    public int getOwnerGameObjId() {
        final Creature owner = this.getOwner();
        return (owner != null && owner != this) ? owner.getGameObjectId() : -2048;
    }

    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
        final Location loc = this.getLocation();
        if (loc != null && loc.getGameSector() != null && this.isSpawned()) {
            loc.getGameSector().forEachObject(ECharKind.Player, (gameObjectId, player) -> {
                if (player != this) {
                    player.sendPacket(sp);
                }
            });
        }
    }

    public void sendBroadcastGuildAndRegionPacket(final SendablePacket<GameClient> sp, boolean isRegional) {
        if (isRegional && isPlayer() && ((Player) this).getGuild() != null) {
            ((Player) this).getGuild().sendBroadcastPacket(sp);

            final Location loc = this.getLocation();
            if (loc != null && loc.getGameSector() != null && this.isSpawned()) {
                loc.getGameSector().forEachObject(ECharKind.Player, (gameObjId, player) -> {
                    if (player != this && player.isPlayer() && ((Player) player).getGuildId() != ((Player) this).getGuildId())
                        player.sendPacket(sp);
                });
            }
        } else
            sendBroadcastItSelfPacket(sp);
    }

    public void sendBroadcastItSelfPacket(final SendablePacket<GameClient> sp) {
        this.sendBroadcastPacket(sp);
    }

    public void sendPacket(final SendablePacket<GameClient> sp) {
    }

    public void sendPacketNoFlush(final SendablePacket<GameClient> sp) {
    }

    public /*synchronized*/ void onDie(final Creature attacker, final long actionHash) {
        if (!this.isSpawned())
            return;
        if (this.getAi() != null)
            this.getAi().notifyStop();

        this.getBuffList().removeOnDeathBuffs();
        int killerGameObjId = -1024;
        if (attacker != null) {
            killerGameObjId = attacker.getGameObjectId();
            if (attacker.isPlayer()) {
                final Player playerAttacker = (Player) attacker;
                if (!isPlayer()) {
                    final KnowledgeLearningT knowledgeLearningT = KnowledgeLearningData.getInstance().getTemplate(this.getCreatureId());
                    if (knowledgeLearningT != null && !playerAttacker.getMentalCardHandler().containsCard(knowledgeLearningT.getKnowledgeIndex())) {
                        int knowledgeAcquireRate = knowledgeLearningT.getSelectRate() / 10000;
                        final int knowledgeAcquireStatValue = playerAttacker.getGameStats().getAquireKnowledgeRateStat().getIntValue() / 10000;
                        if (knowledgeAcquireStatValue > 0) {
                            knowledgeAcquireRate += knowledgeAcquireRate * knowledgeAcquireStatValue / 100;
                        }
                        if (Rnd.getChance(knowledgeAcquireRate)) {
                            ECardGradeType cardGradeType = null;
                            final long result = Rnd.get(1000000);
                            final int knowledgeHighAcquireStatValue = playerAttacker.getGameStats().getAquireHighKnowledgeRateStat().getIntValue();
                            if (result <= 25000 + knowledgeHighAcquireStatValue) {
                                cardGradeType = ECardGradeType.S;
                            } else if (result <= 75000 + knowledgeHighAcquireStatValue) {
                                cardGradeType = ECardGradeType.A_PLUS;
                            } else if (result <= 150000 + knowledgeHighAcquireStatValue) {
                                cardGradeType = ECardGradeType.A;
                            } else if (result <= 250000 + knowledgeHighAcquireStatValue) {
                                cardGradeType = ECardGradeType.B;
                            } else if (result <= 500000 + knowledgeHighAcquireStatValue) {
                                cardGradeType = ECardGradeType.C;
                            }
                            if (cardGradeType != null) {
                                playerAttacker.getMentalCardHandler().updateMentalCard(knowledgeLearningT.getKnowledgeIndex(), cardGradeType);
                            }
                        }
                    }
                    if (!this.getRegion().getRegionType().isArena()) {
                        final CreatureAggroInfo killerAggroInfo = this.getAggroList().getAggroInfo(playerAttacker);
                        if (killerAggroInfo != null && killerAggroInfo.getDmg() > 0.0) {
                            playerAttacker.addTendency(this.template.getVariedTendencyOnDead());
                        }
                    }
                    playerAttacker.getObserveController().notifyObserver(EObserveType.killMonster, this.getCreatureId(), this.getRegionId());
                    playerAttacker.getObserveController().notifyObserver(EObserveType.killMonsterGroup, this.getTemplate().getCharacterGroup(), this.getCreatureId(), this.getRegionId());
                }
            }
        }

        final AggroInfo aggroInfo = this.getAggroList().getMostDamage();
        if (aggroInfo != null) {
            if (aggroInfo instanceof CreatureAggroInfo) {
                final CreatureAggroInfo creatureAggroInfo = (CreatureAggroInfo) aggroInfo;
                final Creature creature = creatureAggroInfo.getCreature();
                if (creature.isPlayer()) {
                    final Player player = (Player) creature;
                    creatureAggroInfo.setWinners(Collections.singletonList(player.getObjectId()));
                }
                final Creature mostDamage = this.getAggroList().getMostDamageCreature();
                if (mostDamage != null) {
                    creatureAggroInfo.setKiller(mostDamage);
                }
            } else {
                final PartyAggroInfo partyAggroInfo = (PartyAggroInfo) aggroInfo;
                final IParty<Player> party = partyAggroInfo.getParty();
                party.onEvent(new RegisterPartyWinnersEvent(this, party, partyAggroInfo));
            }


            // Handle
            final List<Long> winners = aggroInfo.getWinners();
            if (winners != null) {
                for (final long id : winners) {
                    final Player winner = World.getInstance().getPlayer(id);
                    if (winner != null) {
                        if (winner != attacker) {
                            winner.getObserveController().notifyObserver(EObserveType.killMonster, this.getCreatureId(), this.getRegionId());
                            winner.getObserveController().notifyObserver(EObserveType.killMonsterGroup, this.getTemplate().getCharacterGroup(), this.getCreatureId(), this.getRegionId());
                        }
                        double exp = this.template.getExp();
                        double skillExp = this.template.getSkillPointExp();
                        final IParty<Player> party2 = winner.getParty();
                        if (party2 != null) {
                            int levelDiff = 0;
                            final int memberSize = party2.getMembers().size();
                            if (memberSize > 0) {
                                for (final Player m : party2.getMembers()) {
                                    levelDiff += m.getLevel();
                                }
                                levelDiff /= memberSize;
                            }
                            exp = PartyExperienceOptionData.getInstance().getExperience(party2, exp);
                            skillExp = PartyExperienceOptionData.getInstance().getExperience(party2, skillExp);
                            final double distance = MathUtils.getDistance(this.getLocation(), winner.getLocation());
                            if (distance >= 10000.0) {
                                exp = 0.0;
                                skillExp = 0.0;
                            } else if (distance >= 9000.0) {
                                exp *= 0.25;
                                skillExp *= 0.25;
                            } else if (distance >= 8000.0) {
                                exp *= 0.5;
                                skillExp *= 0.5;
                            } else if (distance >= 6000.0) {
                                exp *= 0.85;
                                skillExp *= 0.85;
                            }
                            if (levelDiff < EtcOptionConfig.PARTY_EXP_PENALTY_MAX_LEVEL && levelDiff - winner.getLevel() >= EtcOptionConfig.PARTY_EXP_PENALTY_LEVEL_DIFF) {
                                exp = 0.0;
                                skillExp = 0.0;
                            }
                        }
                        winner.addExp(exp, true);
                        winner.getSkillList().addSkillExp(skillExp, true);

                        if (attacker != null && !isPlayer() && winner.getGameObjectId() != attacker.getGameObjectId())
                            winner.addTendency(this.template.getVariedTendencyOnDead());
                    }
                }
            }
        }
        this.sendBroadcastItSelfPacket(new SMDieNonPlayer(this.gameObjectId, (killerGameObjId > 0) ? killerGameObjId : this.gameObjectId, actionHash));

        // Check if Creature is a monster and is an instanced monster.
        if (this.isMonster() && ((Monster) this).isInstanceSummon()) {
            try { // ConcurrentModificationException is a problem, so we wrap it so it wouldn't crash.
                // Assign loot to each player.
                for (Player instancePlayer : ((Monster) this).getInstanceSummon().getOwners()) {

                    // Create player aggro list so that he would get the drop.
                    CreatureAggroInfo aggroPlayerDrop = new CreatureAggroInfo(this);
                    aggroPlayerDrop.setKiller(instancePlayer);
                    aggroPlayerDrop.setWinners(Collections.singletonList(instancePlayer.getObjectId()));

                    // Give the drop to the player.
                    distributeDrop(aggroPlayerDrop, instancePlayer);
                }
            } catch (ConcurrentModificationException cme) {
                log.error("An error occured while distributing drops for party. (CME)", cme);
            }

            onDeathComplete(null, null, false);
        } else if (isMonster() && isFieldOrWorldBoss()) {
            try {
                log.warn("World/Field Boss has been killed. CreatureId={}, Name={}", getCreatureId(), getName());
                SortedMap<Integer, Integer> fieldBossDropGroups = FieldBossData.getInstance().getFieldBossData(getCreatureId());
                if (fieldBossDropGroups != null) {
                    // Sort creatures by damage dealt getAggroInfo
                    List<Creature> aggressiveCreatures = getAggroList().getAggroCreatures()
                            .stream()
                            .filter(Objects::nonNull)
                            .filter(Creature::isPlayer)
                            .filter(Creature::isSpawned)
                            .filter(StreamUtils.distinctByKey(p -> ((Player) p).getAccountId()))
                            .sorted((obj1, obj2) -> Double.compare(getAggroList().getAggroInfo(obj2).getDmg(), getAggroList().getAggroInfo(obj1).getDmg()))
                            .collect(Collectors.toList());

                    log.warn("[{}] Total players={}, groups={}", getCreatureId(), aggressiveCreatures.size(), fieldBossDropGroups.size());

                    int playersProcessed = 0;
                    for (Map.Entry<Integer, Integer> dropInfo : fieldBossDropGroups.entrySet()) {
                        List<Creature> rewardCreatures = aggressiveCreatures
                                .stream()
                                .skip(playersProcessed)
                                .limit((int) Math.ceil((dropInfo.getKey() / 100.0) * (aggressiveCreatures.size() - playersProcessed)))
                                .collect(Collectors.toList());

                        log.warn("[{}] Rewarding group={}, creatures={}", getCreatureId(), dropInfo.getKey(), rewardCreatures.size());

                        // No creatures? fuck off lel.
                        if (rewardCreatures.size() < 1)
                            break;

                        for (Creature rewardedCreature : rewardCreatures) {
                            playersProcessed++;
                            // Filter out non-player and non-player-summon
                            if (!rewardedCreature.isPlayer())
                                continue;

                            // If you got no hate, you got no drop
                            AggroInfo inf = getAggroList().getAggroInfo(rewardedCreature);
                            if (inf == null || inf.getHate() <= 1)
                                continue;
                            log.warn("[{}] Spawning loot for={}, damage={}, hate={}, balance={}", getCreatureId(), rewardedCreature.getName(), (long) getAggroList().getAggroInfo(rewardedCreature).getDmg(), (long) getAggroList().getAggroInfo(rewardedCreature).getHate(), (long) getAggroList().getAggroInfo(rewardedCreature).getBalancedHate());

                            if (inf.getDmg() > 0) {
                                // Create player aggro list so that he would get the drop.
                                Player toRewardCreature = (Player) rewardedCreature;
                                CreatureAggroInfo aggroBossDrop = new CreatureAggroInfo(this);
                                aggroBossDrop.setKiller(toRewardCreature);
                                aggroBossDrop.setWinners(Collections.singletonList(toRewardCreature.getObjectId()));
                                distributeDrop(aggroBossDrop, toRewardCreature, dropInfo.getValue());
                            }
                        }
                    }

                    aggressiveCreatures.clear();
                }
            } catch (Exception eh) {
                log.warn("An error occurred that prevented people from getting any of rewards.", eh);
            }

            onDeathComplete(null, null, false);
        } else { // Distribute the drop.
            onDeathComplete(aggroInfo, attacker, true);
        }

        this.getSummonStorage().getSummons().stream().filter(Creature::canDespawnOnDieOwner).forEach(summon -> World.getInstance().deSpawn(summon, ERemoveActorType.DespawnSummon));
    }

    private void onDeathComplete(AggroInfo aggroInfo, Creature attacker, boolean spawnBody) {
        final DeadBody db = new DeadBody(aggroInfo, this, attacker);
        if (spawnBody) {
            if (getTemplate().getVanishTime() > 0) {
                World.getInstance().spawn(db, false, false);
                DespawnService.getInstance().putBody(db);
            } else {
                RespawnService.getInstance().putBody(db);
            }
        } else {
            RespawnService.getInstance().putBody(db);
        }
    }

    private void distributeDrop(AggroInfo aggroInfo, Creature attacker, int dropId) {
        final DeadBody db = new DeadBody(aggroInfo, this, attacker, DeadBody.getRandomLocation(this), dropId);
        db.setOnlyLoot();
        World.getInstance().spawn(db, false, false);
        DespawnService.getInstance().putBody(db);
    }

    private void distributeDrop(AggroInfo aggroInfo, Creature attacker) {
        final DeadBody db = new DeadBody(aggroInfo, this, attacker);
        db.setOnlyLoot();
        World.getInstance().spawn(db, false, false);
        DespawnService.getInstance().putBody(db);
    }

    public boolean isFieldOrWorldBoss() {
        return FieldBossData.getInstance().hasFieldBossData(getCreatureId());
    }

    public boolean isMonster() {
        return this.getCharKind().isMonster();
    }

    public boolean isVehicle() {
        return this.getCharKind().isVehicle();
    }

    public boolean isNpc() {
        return this.getCharKind().isNpc();
    }

    public boolean isHousehold() {
        return this.getCharKind().isHousehold();
    }

    public boolean isCollect() {
        return this.getCharKind().isCollect();
    }

    public boolean isDeadbody() {
        return this.getCharKind().isDeadbody();
    }

    public boolean isCharacter() {
        switch (this.getCharKind()) {
            case Monster:
            case Npc:
            case Vehicle:
            case Alterego:
            case Player:
            case Gate: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public boolean isPlayer() {
        return this.getCharKind().isPlayer();
    }

    public boolean canForceMove() {
        return this.getAi() != null && !this.getAi().getBehavior().isKnockback() && getGameStats().getWeight().getWeightPercentage() < 300;
    }

    public boolean isGate() {
        return this.getCharKind().isAlterego();
    }

    public boolean isPlayable() {
        return this.isPlayer() || (this.isVehicle() && this.getOwner() != null && this.getOwner().isPlayer());
    }

    public boolean isAlly(final Creature creature) {
        return true;
    }

    public boolean isLordOrKing(final Creature creature) {
        return false;
    }

    public boolean isDarkSpiritMonster() {
        return this.isDarkSpiritMonster;
    }

    public void setDarkSpiritMonster(final boolean darkSpiritMonster) {
        this.isDarkSpiritMonster = darkSpiritMonster;
    }

    public AMovementController getMovementController() {
        if (this.movementController == null) {
            this.movementController = new MovementController(this);
        }
        return this.movementController;
    }

    public void onDespawn() {
        if (this.getAi() != null) {
            this.getAi().notifyStop();
        }
        final Creature owner = this.getOwner();
        if (owner != null) {
            owner.getSummonStorage().removeSummon(this);
        }
    }

    public void onSpawn() {
    }

    public boolean canRespawn() {
        return true;
    }

    public boolean canDespawnOnDieOwner() {
        return true;
    }

    public void setVar(final String name, final Object value) {
        if (this.variables == null) {
            this.variables = new HashMap<>();
        }
        this.variables.put(name, value);
    }

    private void unsetVar(final String name) {
        if (name == null) {
            return;
        }
        if (this.variables.containsKey(name)) {
            this.variables.remove(name);
        }
    }

    public <T> T getVar(final String name, final Class<T> type, final T defaultValue) {
        if (this.variables == null || !this.variables.containsKey(name)) {
            return defaultValue;
        }
        final Object valObject = this.variables.get(name);
        try {
            return type.cast(valObject);
        } catch (Exception e) {
            Creature.log.error("Failed to cast variable " + name + " (variables value is " + this.variables.get(name) + ") to " + type + ".");
            return defaultValue;
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": name=" + this.getTemplate().getDisplayName() + ", id=" + this.getCreatureId() + ", gameObjectId=" + this.getGameObjectId() + ", actionIndex=" + this.getActionIndex();
    }
}
