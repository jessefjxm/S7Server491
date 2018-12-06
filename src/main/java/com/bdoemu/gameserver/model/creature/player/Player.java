package com.bdoemu.gameserver.model.creature.player;

import com.bdoemu.MainServer;
import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.xmlrpc.XMLRPCable;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcPlayer;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcPlayerClassId;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcPlayerZodiac;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.*;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.PCData;
import com.bdoemu.gameserver.dataholders.PCSetData;
import com.bdoemu.gameserver.dataholders.PcActionPackageData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.ai.deprecated.AIService;
import com.bdoemu.gameserver.model.alchemy.AlchemyRecordStorage;
import com.bdoemu.gameserver.model.chat.ChatChannelController;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.*;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.PlayerAggroList;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.observers.ObserveController;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.answer.AnswerStorage;
import com.bdoemu.gameserver.model.creature.player.appearance.PlayerAppearanceStorage;
import com.bdoemu.gameserver.model.creature.player.appearance.PlayerRenderStorage;
import com.bdoemu.gameserver.model.creature.player.challenge.ChallengeHandler;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePointHandler;
import com.bdoemu.gameserver.model.creature.player.controllers.ActionRestrictionController;
import com.bdoemu.gameserver.model.creature.player.controllers.PlayerBanController;
import com.bdoemu.gameserver.model.creature.player.cooltimes.CoolTime;
import com.bdoemu.gameserver.model.creature.player.cooltimes.CoolTimeList;
import com.bdoemu.gameserver.model.creature.player.duel.PVPController;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;
import com.bdoemu.gameserver.model.creature.player.duel.services.PvPBattleService;
import com.bdoemu.gameserver.model.creature.player.dye.DyeStorage;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.EncyclopediaStorage;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.enums.EZodiacType;
import com.bdoemu.gameserver.model.creature.player.exploration.Exploration;
import com.bdoemu.gameserver.model.creature.player.fishing.services.FishingService;
import com.bdoemu.gameserver.model.creature.player.fitness.FitnessHandler;
import com.bdoemu.gameserver.model.creature.player.fitness.enums.EFitnessType;
import com.bdoemu.gameserver.model.creature.player.intimacy.IntimacyHandler;
import com.bdoemu.gameserver.model.creature.player.itemPack.AbstractAddItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerEquipments;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DropItemPositionEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.SystemAddItemEvent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.LifeExperienceInformation;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.PlayerLifeExperienceStorage;
import com.bdoemu.gameserver.model.creature.player.mail.MailBox;
import com.bdoemu.gameserver.model.creature.player.mentalcard.MentalCardHandler;
import com.bdoemu.gameserver.model.creature.player.quests.PlayerQuestHandler;
import com.bdoemu.gameserver.model.creature.player.social.actions.SocialActionStorage;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.templates.PCSetTemplate;
import com.bdoemu.gameserver.model.creature.player.templates.PCTemplate;
import com.bdoemu.gameserver.model.creature.player.titles.TitleHandler;
import com.bdoemu.gameserver.model.creature.player.trade.Bargain;
import com.bdoemu.gameserver.model.creature.player.ui.QuickSlot;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantController;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantSealType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.creature.services.DespawnService;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.creature.services.RespawnService;
import com.bdoemu.gameserver.model.events.EventStorage;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.HouseVisit;
import com.bdoemu.gameserver.model.houses.HouseholdController;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.items.CashProductBuyCount;
import com.bdoemu.gameserver.model.items.services.CashItemService;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.items.templates.CashItemT;
import com.bdoemu.gameserver.model.journal.Journal;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.move.PlayerMovementController;
import com.bdoemu.gameserver.model.pvp.LocalWarMember;
import com.bdoemu.gameserver.model.skills.PlayerSkillList;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.CreatureBuffList;
import com.bdoemu.gameserver.model.skills.buffs.enums.ApplyBuffType;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.stats.containers.PlayerGameStats;
import com.bdoemu.gameserver.model.stats.containers.PlayerLifeStats;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildMemberRankType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.events.LoginGuildEvent;
import com.bdoemu.gameserver.model.team.guild.events.LogoutGuildEvent;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.LogoutPartyEvent;
import com.bdoemu.gameserver.model.team.party.service.PartyService;
import com.bdoemu.gameserver.model.trade.Trade;
import com.bdoemu.gameserver.model.trade.events.CancelItemExchangeEvent;
import com.bdoemu.gameserver.model.weather.services.WeatherService;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Player extends Playable implements XMLRPCable {
    private static final Logger log = LoggerFactory.getLogger(Player.class);
    private final PCSetTemplate playerTemplate;
    private final ObserveController observeController;
    private final AccountData accountData;
    private final PlayerBag playerBag;
    private final PlayerQuestHandler playerQuestHandler;
    private final QuickSlot quickSlot;
    private final PlayerLifeExperienceStorage lifeExperienceStorage;
    private final PlayerSkillList skillList;
    private final AnswerStorage answerStorage;
    private final CoolTimeList coolTimeList;
    private final FitnessHandler fitnessHandler;
    private final HouseVisit houseVisit;
    private String name;
    private String nonSavedComment;
    private long preemptiveStrikeTime;
    private long rescueCoolTime;
    private long exp;
    private int tendency;
    private int avatarEquip;
    private long lastLogin;
    private long lastLogout;
    private long playedTime;
    private long creationDate;
    private long deletionDate;
    private long blockDate;
    private int pcNonSavedCacheCount;
    private int pcCustomizationCacheCount;
    private int slot;
    private EZodiacType zodiac;
    private int currentWp;
    private int enchantFailCount;
    private int enchantSuccessCount;
    private int creationIndex;
    private int learnSkillCacheCount;
    private Memo memo;
    private byte[] customizedKeys;
    private double oz;
    private PlayerAppearanceStorage playerAppearance;
    private PlayerRenderStorage playerRenderStorage;
    private Journal playerJournal;
    private GameClient client;
    private Guild guild;
    private Trade trade;
    private Collection<ActiveBuff> installationActiveBuffs;
    private double distanceTraveled;
    private double distanceBackpackTraveled;
    private double distanceVehicleTraveled;
    private double fallDistance;
    private Creature escort;
    private boolean isReadyToPlay;
    private boolean isDeadInDuel;
    private boolean isVisible;
    private MailBox mailBox;
    private PlayerBanController banController;
    private ServantController servantController;
    private NpcWorkerController npcWorkerController;
    private HouseholdController householdController;
    private PVPController pvpController;
    private ActionRestrictionController actionRestrictionController;
    private int battlePoints;
    private int lifePoints;
    private int specialPoints;
    private long lostExperienceOnDeath;
    private int localWarPoints;
    private int lastAlchemyStoneUsedTime;
    private Bargain _bargain;
    private boolean _weather;
    private boolean _damageDebug;

    public Player(final BasicDBObject dbObject) {
        super(-1, CreatureData.getInstance().getTemplate(PCSetData.getInstance().getTemplate(EClassType.valueOf(dbObject.getInt("classType")).getId()).getCharacterKey()), new SpawnPlacementT(new Location((BasicDBObject) dbObject.get("location"))));
        this.nonSavedComment = "";
        this.deletionDate = -1L;
        this.blockDate = -1L;
        this.pcNonSavedCacheCount = 1;
        this.pcCustomizationCacheCount = 1;
        this.learnSkillCacheCount = Rnd.get(1, 65535);
        this.observeController = new ObserveController();
        this.answerStorage = new AnswerStorage();
        this.isReadyToPlay = false;
        this.isDeadInDuel = false;
        this.isVisible = true;
        this.battlePoints = 0;
        this.lifePoints = 0;
        this.specialPoints = 0;
        this.objectId = dbObject.getLong("_id");
        this.name = dbObject.getString("name");
        this.creationDate = dbObject.getLong("creationDate");
        this.zodiac = EZodiacType.values()[dbObject.getInt("zodiac")];
        this.level = dbObject.getInt("level");
        this.tendency = dbObject.getInt("tendency");
        this.currentWp = dbObject.getInt("currentWp");
        this.playerTemplate = PCSetData.getInstance().getTemplate(EClassType.valueOf(dbObject.getInt("classType")).getId());
        this.accountData = null;
        this.playerBag = null;
        this.playerQuestHandler = null;
        this.quickSlot = null;
        this.lifeExperienceStorage = null;
        this.skillList = null;
        this.coolTimeList = null;
        this.fitnessHandler = null;
        this.houseVisit = null;
        this._bargain = null;
        this.lostExperienceOnDeath = 0;
        this.localWarPoints = dbObject.getInt("localWarPoints", 0);
        this.lastAlchemyStoneUsedTime = 0;
        _weather = false;
    }

    public Player(final String name, final int slot, final EZodiacType zodiac, final PCSetTemplate playerTemplate, final PlayerAppearanceStorage playerAppearance, final GameClient client) {
        super(client.getObjectId(), CreatureData.getInstance().getTemplate(playerTemplate.getCharacterKey()), playerTemplate.getSpawnPlacement());
        this.nonSavedComment = "";
        this.deletionDate = -1L;
        this.blockDate = -1L;
        this.pcNonSavedCacheCount = 1;
        this.pcCustomizationCacheCount = 1;
        this.learnSkillCacheCount = Rnd.get(1, 65535);
        this.observeController = new ObserveController();
        this.answerStorage = new AnswerStorage();
        this.isReadyToPlay = false;
        this.isDeadInDuel = false;
        this.isVisible = true;
        this.battlePoints = 0;
        this.lifePoints = 0;
        this.specialPoints = 0;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.PLAYER);
        this.actionStorage = new ActionStorage(this);
        this.name = name;
        this.slot = slot;
        this.zodiac = zodiac;
        this.playerTemplate = playerTemplate;
        this.playerAppearance = playerAppearance;
        this.playerRenderStorage = new PlayerRenderStorage();
        this.playerJournal = new Journal(EJournalType.Character);
        this.lastLogin = GameTimeService.getServerTimeInMillis();
        this.creationDate = GameTimeService.getServerTimeInMillis();
        this.lifeExperienceStorage = new PlayerLifeExperienceStorage();
        this.playerQuestHandler = new PlayerQuestHandler(this);
        this.accountData = new AccountData(this, client.getLoginAccountInfo());
        this.creationIndex = AccountsDBCollection.getInstance().getUpdateCreationCount(this.getAccountId());
        this.setGameStats(new PlayerGameStats(this));
        this.skillList = new PlayerSkillList(this);
        this.setLifeStats(new PlayerLifeStats(this));
        this.buffList = new CreatureBuffList(this);
        this.coolTimeList = new CoolTimeList();
        this.playerBag = new PlayerBag(this, this.accountData.getAccountBag());
        this.quickSlot = new QuickSlot();
        this.fitnessHandler = new FitnessHandler(this);
        this.houseVisit = new HouseVisit();
        for (final Integer itemId : playerTemplate.getDefaultItemList()) {
            this.playerBag.onEvent(new SystemAddItemEvent(this.playerBag.getInventory(), itemId, 1L));
        }
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.CreationDate);
        this.addJournalEntryAndNotify(journalEntry);
        this.getExploration().addDiscovery(playerTemplate.getStartWayPointNo());
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new PlayerAggroList(this);
        this.oz = this.getLocation().getZ();
        this.lostExperienceOnDeath = 0;
        this.localWarPoints = 0;
        this.lastAlchemyStoneUsedTime = 0;
        this._bargain = null;
        _weather = false;
    }

    public Player(final BasicDBObject playerDbObject, final GameClient gameClient) {
        super(gameClient.getObjectId(), CreatureData.getInstance().getTemplate(PCSetData.getInstance().getTemplate(EClassType.valueOf(playerDbObject.getInt("classType")).getId()).getCharacterKey()), new SpawnPlacementT(new Location((BasicDBObject) playerDbObject.get("location"))));
        this.nonSavedComment = "";
        this.deletionDate = -1L;
        this.blockDate = -1L;
        this.pcNonSavedCacheCount = 1;
        this.pcCustomizationCacheCount = 1;
        this.learnSkillCacheCount = Rnd.get(1, 65535);
        this.observeController = new ObserveController();
        this.answerStorage = new AnswerStorage();
        this.isReadyToPlay = false;
        this.isDeadInDuel = false;
        this.isVisible = true;
        this.battlePoints = 0;
        this.lifePoints = 0;
        this.specialPoints = 0;
        this.objectId = playerDbObject.getLong("_id");
        this.actionStorage = new ActionStorage(this);
        this.name = playerDbObject.getString("name");
        this.slot = (byte) playerDbObject.getInt("slot");
        this.zodiac = EZodiacType.values()[playerDbObject.getInt("zodiac")];
        this.playerTemplate = PCSetData.getInstance().getTemplate(EClassType.valueOf(playerDbObject.getInt("classType")).getId());
        this.level = playerDbObject.getInt("level");
        this.exp = playerDbObject.getLong("exp");
        this.tendency = playerDbObject.getInt("tendency");
        this.lastLogin = GameTimeService.getServerTimeInMillis();
        this.lastLogout = playerDbObject.getLong("lastLogout");
        this.deletionDate = playerDbObject.getLong("deletionDate");
        this.creationDate = playerDbObject.getLong("creationDate");
        this.blockDate = playerDbObject.getLong("blockDate");
        this.playedTime = playerDbObject.getLong("playedTime");
        this.rescueCoolTime = playerDbObject.getLong("rescueCoolTime");
        this.currentWp = playerDbObject.getInt("currentWp");
        this.enchantFailCount = playerDbObject.getInt("enchantFailCount");
        this.enchantSuccessCount = playerDbObject.getInt("enchantSuccessCount");
        this.equipSlotCacheCount = playerDbObject.getInt("equipSlotCacheCount");
        this.basicCacheCount = playerDbObject.getInt("basicCacheCount");
        this.pcNonSavedCacheCount = playerDbObject.getInt("pcNonSavedCacheCount");
        this.pcCustomizationCacheCount = playerDbObject.getInt("pcCustomizationCacheCount");
        this.creationIndex = playerDbObject.getInt("creationIndex");
        if (playerDbObject.containsField("memo")) {
            this.memo = new Memo((BasicDBObject) playerDbObject.get("memo"));
        }
        this.playerAppearance = new PlayerAppearanceStorage((BasicDBObject) playerDbObject.get("appearance"));
        this.playerRenderStorage = new PlayerRenderStorage();
        this.playerJournal = new Journal((BasicDBObject) playerDbObject.get("journal"));
        if (playerDbObject.containsField("customizedKeys")) {
            this.customizedKeys = (byte[]) playerDbObject.get("customizedKeys");
        }
        this.lifeExperienceStorage = new PlayerLifeExperienceStorage((BasicDBObject) playerDbObject.get("lifeExperience"));
        this.playerQuestHandler = new PlayerQuestHandler(this, (BasicDBObject) playerDbObject.get("quests"));
        this.accountData = AccountsDBCollection.getInstance().load(playerDbObject.getLong("accountId"), this, gameClient.getLoginAccountInfo());
        this.setGameStats(new PlayerGameStats(this));
        this.setLifeStats(new PlayerLifeStats(this, (BasicDBObject) playerDbObject.get("lifeStats")));
        this.skillList = new PlayerSkillList((BasicDBObject) playerDbObject.get("skillList"), this);
        this.buffList = new CreatureBuffList((BasicDBList) playerDbObject.get("buffList"), this);
        this.coolTimeList = new CoolTimeList((BasicDBObject) playerDbObject.get("coolTimeList"));
        this.playerBag = new PlayerBag((BasicDBObject) playerDbObject.get("playerBag"), this, this.accountData.getAccountBag());
        this.playerBag.getEquipments().equipItems();
        this.skillList.applyPassiveBuffs();
        this.quickSlot = new QuickSlot((BasicDBObject) playerDbObject.get("quickSlot"));
        this.fitnessHandler = new FitnessHandler(this, (BasicDBObject) playerDbObject.get("fitness"));
        this.houseVisit = new HouseVisit();
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new PlayerAggroList(this);
        final BasicDBList cashByCountDB = (BasicDBList) playerDbObject.get("cashByCount");
        for (final Object aCashByCountDB : cashByCountDB) {
            final BasicDBObject productDB = (BasicDBObject) aCashByCountDB;
            final CashProductBuyCount cashProductBuyCount = new CashProductBuyCount(productDB);
            if (cashProductBuyCount.getCashItemT() != null) {
                this.accountData.getProductByCount().put(cashProductBuyCount.getProductNr(), cashProductBuyCount);
            }
        }
        this.oz = this.getLocation().getZ();
        this.lostExperienceOnDeath = 0;
        this.lastAlchemyStoneUsedTime = 0;
        this.localWarPoints = playerDbObject.getInt("localWarPoints", 0);
        this._bargain = null;
        _weather = false;
    }

    public Bargain getTradeShopBargain() {
        return _bargain;
    }

    public void setTradeShopBargain(Bargain bargain) {
        _bargain = bargain;
    }

    public long getLostExperienceOnDeath() {
        return this.lostExperienceOnDeath;
    }

    public void setLostExperienceOnDeath(long exp) {
        this.lostExperienceOnDeath = exp;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public boolean isWeatherEnabled() {
        return _weather;
    }

    public void setWeather(boolean state) {
        _weather = state;
    }

    public boolean isDamageDebugEnabled() {
        return _damageDebug;
    }

    public void setDamageDebug() {
        _damageDebug = !_damageDebug;
    }

    public void setVisible(final boolean visible) {
        this.isVisible = visible;
    }

    public AnswerStorage getAnswerStorage() {
        return this.answerStorage;
    }

    public double getOz() {
        return this.oz;
    }

    public void setOz(final double oz) {
        this.oz = oz;
    }

    public EventStorage getEventStorage() {
        return this.accountData.getEventStorage();
    }

    public boolean isDeliver() {
        return this.getPlayerBag().getInventory().getBackpackSize() > 0;
    }

    public boolean isReadyToPlay() {
        return this.isReadyToPlay;
    }

    public void setReadyToPlay(final boolean result) {
        this.isReadyToPlay = result;

        if (this.getAi() != null) {
            if (this.isReadyToPlay)
                this.getAi().notifyStart();
            else
                this.getAi().notifyStop();
        }
    }

    public Creature getEscort() {
        return this.escort;
    }

    public void setEscort(final Creature escort) {
        this.escort = escort;
    }

    public Collection<ActiveBuff> getInstallationActiveBuffs() {
        return this.installationActiveBuffs;
    }

    public void setInstallationActiveBuffs(final Collection<ActiveBuff> installationActiveBuffs) {
        this.installationActiveBuffs = installationActiveBuffs;
    }

    public int getAvatarEquipOnOff() {
        return this.avatarEquip;
    }

    public void initAi() {
        this.recalculateActionStorage();
    }

    public void recalculateActionStorage() {
        final String packageName = PcActionPackageData.getInstance().getActionPackageName(this);
        this.actionStorage.setActionChartPackage(packageName);
        if (this.ai != null) {
            this.ai.setVariables(this.actionStorage.getAiParameters());
        } else {
            this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
            if (this.ai != null) {
                ThreadPool.getInstance().executeAi(this.ai);
            }
        }
    }

    public void addFallDistance(final double fallDistance) {
        this.fallDistance += fallDistance;
    }

    public void doFallDamage() {
        if (this.fallDistance > 1000.0) {
            this.fallDistance -= 1000.0;
            final double damageRate = this.fallDistance / 50.0;
            final Servant servant = this.getCurrentVehicle();
            double hp = ((servant != null) ? servant.getGameStats().getHp().getMaxHp() : this.getGameStats().getHp().getMaxHp()) * damageRate / 100.0;
            final int fallDamageReduction = this.getGameStats().getFallDamage().getIntMaxValue();
            if (fallDamageReduction > 0) {
                hp -= hp * (fallDamageReduction / 10000) / 100.0;
            }
            if (servant != null) {
                servant.getGameStats().getHp().addHP(-hp, servant);
            } else {
                this.getGameStats().getHp().addHP(-hp, this);
            }
        }
        this.fallDistance = 0.0;
    }

    public int getLastAlchemyStoneUsedTime() {
        return lastAlchemyStoneUsedTime;
    }

    public void setLastUsedAlchemyStoneTime(int newTime) {
        lastAlchemyStoneUsedTime = newTime;
    }

    public void addDistanceTraveled(final double distanceTraveled) {
        final Servant currentVehicle = this.getCurrentVehicle();
        if (currentVehicle != null && this.getServantController().getServant(currentVehicle.getObjectId()) != null && currentVehicle.getServantTemplate() != null) {
            this.distanceVehicleTraveled += distanceTraveled;
            if (this.distanceVehicleTraveled > 10000.0) {
                this.distanceVehicleTraveled = 0.0;
                currentVehicle.addExp(LocalizingOptionConfig.VEHICLE_MOVEMENT_EXPERIENCE);
                for (final Servant linkedServant : currentVehicle.getLinkedServants()) {
                    linkedServant.addExp(LocalizingOptionConfig.VEHICLE_MOVEMENT_EXPERIENCE);
                }
            }
        } else if (this.getPlayerBag().getInventory().getBackpackSize() > 0) {
            this.distanceBackpackTraveled += distanceTraveled;
            if (this.distanceBackpackTraveled > 10000.0) {
                this.distanceBackpackTraveled = 0.0;
                this.getFitnessHandler().addExp(this, EFitnessType.Strength, 10);
            }
        } else {
            this.distanceTraveled += distanceTraveled;
            if (this.distanceTraveled > 10000.0) {
                this.distanceTraveled = 0.0;
                this.getFitnessHandler().addExp(this, EFitnessType.Stamina, 10);
            }
        }
    }

    public void setCurrentVehicle(final Servant servant, final ERidingSlotType ridingSlotType) {
        this.servantController.setCurrentVehicle(servant, ridingSlotType);
        this.recalculateActionStorage();
    }

    public Servant getCurrentVehicle() {
        return this.servantController.getCurrentVehicle();
    }

    public ERidingSlotType getRidingSlotType() {
        return this.servantController.getRidingSlotType();
    }

    public HouseVisit getHouseVisit() {
        return this.houseVisit;
    }

    public AlchemyRecordStorage getAlchemyRecordStorage() {
        return this.accountData.getAlchemyRecordStorage();
    }

    public EncyclopediaStorage getEncyclopediaStorage() {
        return this.accountData.getEncyclopediaStorage();
    }

    public String getNonSavedComment() {
        return this.nonSavedComment;
    }

    public void setNonSavedComment(final String nonSavedComment) {
        this.nonSavedComment = nonSavedComment;
        this.recalculatePcNonSavedCacheCount();
    }

    public void revive(final int hpPercentage, final int mpPercentage) {
        if (this.isDead()) {
            this.getAggroList().clear(true);
            this.getGameStats().getHp().fill(hpPercentage);
            this.getGameStats().getMp().fill(mpPercentage);
            this.sendBroadcastItSelfPacket(new SMRevivePlayer(this.getGameObjectId()));

            if (this.getAi() != null)
                this.getAi().notifyStart();
        }
    }

    public int getActivityPoints() {
        return this.accountData.getActivityPoints();
    }

    public void addActivityPoints(final int points) {
        final GuildMember guildMember = this.getGuildMember();
        if (guildMember != null && points > 0) {
            guildMember.addActivityPoints(points);
            this.accountData.addActivityPoints(points);
        }
    }

    public int getPcCustomizationCacheCount() {
        return this.pcCustomizationCacheCount;
    }

    public void recalculatePcCustomizationCacheCount() {
        ++this.pcCustomizationCacheCount;
    }

    public void recalculatePcNonSavedCacheCount() {
        ++this.pcNonSavedCacheCount;
    }

    public int getJoinedChatChannelId() {
        return this.accountData != null ? this.accountData.getChatChannelId() : -1;
    }

    public void setJoinedChannelId(int newId) {
        if (this.accountData != null)
            this.accountData.setChatChannelId(newId);
    }

    public boolean canPvP() {
        return this.getLevel() >= BattleOptionConfig.PVP_MIN_LEVEL;
    }

    public boolean isPvPEnable() {
        return this.getPVPController().isPvpEnable();
    }

    public void setPvPEnable(final boolean pvpEnable) {
        if (this.canPvP()) {
            this.getPVPController().setPvpEnable(pvpEnable);
            this.sendBroadcastItSelfPacket(new SMEnablePvP(this));
        }
    }

    public boolean isEnemy(final Creature creature) {
        if (creature == this)
            return false;

        if (creature.getTemplate().isAttackedPvPModeOnly() && !this.isPvPEnable())
            return false;

        if (this.getSummonStorage().containSummon(creature))
            return false;

        switch (creature.getCharKind()) {
            case Player: {
                final Player player = (Player) creature;
                if (!this.canPvP() || !player.canPvP()) {
                    return false;
                }
                final IParty party = this.getParty();
                if (party != null && party == player.getParty())
                    return false;

                if (creature.getRegion().getTemplate().isSafe() && !creature.getRegion().getTemplate().getRegionType().isArena() && player.getTendency() < 0)
                    return true;

                if (player.getPreemptiveStrikeTime() > 0L || player.getTendency() < 0) {
                    return true;
                }

                if (!((Player) creature).isReadyToPlay())
                    return false;
                final PvpMatch pvpMatch = player.getPvpMatch();
                return (pvpMatch != null && pvpMatch == this.getPvpMatch()) || this.isPvPEnable() || this.getPreemptiveStrikeTime() > 0L;
            }
            case Monster: {
                final Monster monster = (Monster) creature;
                final InstanceSummon instanceSummon = monster.getInstanceSummon();
                return instanceSummon == null || instanceSummon.getOwners().contains(this);
            }
            case Vehicle    : return true;
            case Alterego   : return true;
            default         : return super.isEnemy(creature);
        }
    }

    public void onDie(final Creature attacker, final long actionHash) {
        if (this.getAi() != null) {
            this.getAi().notifyStop();
        }
        this.getBuffList().removeOnDeathBuffs();
        int killerGameObjId = -1024;
        if (attacker != null && attacker != this) {
            boolean isAttackerPlayerOrSummon = attacker.isPlayer() || attacker.getOwner() != null && attacker.getOwner().isPlayer();
            Creature owner = !attacker.isPlayer() && attacker.getOwner() != null && attacker.getOwner().isPlayer() ? attacker.getOwner() : attacker;
            killerGameObjId = owner.getGameObjectId();
            if (isAttackerPlayerOrSummon) {
                final Player playerAttacker = (Player) owner;
                final PvpMatch pvpMatch = this.getPvpMatch();
                if (LocalWarService.getInstance().hasParticipant(this)) {
                    LocalWarService.getInstance().getLocalWarStatus().onDie(playerAttacker, this);
                    this.sendBroadcastItSelfPacket(new SMDiePlayer(killerGameObjId, this.getGameObjectId(), false, false, actionHash));
                    return;
                }
                final boolean isDuelOrArena = (pvpMatch != null && pvpMatch.hasParticipant(owner.getGameObjectId())) || this.getRegion().getRegionType().isArena();
                if (isDuelOrArena) {
                    this.setDeadInDuel(true);
                    this.sendBroadcastItSelfPacket(new SMDiePlayer(killerGameObjId, this.getGameObjectId(), false, true, actionHash));
                    return;
                }
                if (owner != this) {
                    playerAttacker.getObserveController().notifyObserver(EObserveType.killPlayer, this);
                    if (this.getTendency() < 0 && this.getLocation().getRegion().getTemplate().getTerritoryKey() == ETerritoryKeyType.VALENCIA) {
                        World.getInstance().broadcastWorldPacket(new SMBroadcastArrest(owner.getName(), this.getName()));
                        for (int index = 0; index < LocalizingOptionConfig.PRISON_BUFF_TENDENCY.length; ++index) {
                            final int prisonTendency = LocalizingOptionConfig.PRISON_BUFF_TENDENCY[index];
                            if (this.tendency >= prisonTendency) {
                                SkillService.useSkill(this, LocalizingOptionConfig.PRISON_BUFF[index], null, Collections.singletonList(this));
                                break;
                            }
                        }
                    }
                }
                if (this.getTendency() > 0) {
                    playerAttacker.addTendency(LocalizingOptionConfig.PK_TENDENCY_PENALTY);
                }
            }

            if (!isAttackerPlayerOrSummon || this.getTendency() < -100000) {
                double loseExpPercent = 0.01;
                final int deathPenaltyStat = this.getGameStats().getExpRate().getIntMaxValue();
                if (deathPenaltyStat > 0)
                    loseExpPercent -= deathPenaltyStat / 100000000;
                setLostExperienceOnDeath((long) (this.exp * Math.min(1.0, loseExpPercent)));
                this.exp -= getLostExperienceOnDeath();
                this.sendPacket(new SMSetCharacterLevels(this, false));
            }

            // Reduce aggro from the player that was killed
            if (!isAttackerPlayerOrSummon) {
                AggroInfo info = attacker.getAggroList().getAggroInfo(this);
                if (info != null) {
                    if (info.getHate() >= 50)
                        info.addHate(-50);
                    else
                        info.setHate(0);
                }
            }

            final AggroInfo aggroInfo = this.getAggroList().getMostDamage();
            final DeadBody db = new DeadBody(aggroInfo, this, owner);
            this.getPlayerBag().onEvent(new DropItemPositionEvent(this, db));
            if (this.getTemplate().getVanishTime() > 0) {
                World.getInstance().spawn(db, false, false);
                DespawnService.getInstance().putBody(db);
            } else {
                RespawnService.getInstance().putBody(db);
            }
            for (final Creature summon : this.getSummonStorage().getSummons()) {
                if (summon.canDespawnOnDieOwner()) {
                    World.getInstance().deSpawn(summon, ERemoveActorType.DespawnSummon);
                }
            }
            for (final Creature summon : this.getSummonStorage().getSummons()) {
                summon.getAi().HandleOnOwnerDead(this, null);
            }
            this.sendBroadcastItSelfPacket(new SMPlayerKill(owner.isPlayer() ? 6 : 5, this.getName(), owner.getName(), owner.getCreatureId(), this.getLocation()));
            if (isAttackerPlayerOrSummon) {
                final JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDate(GameTimeService.getServerTimeInMillis());
                final Guild guild = ((Player) owner).getGuild();
                journalEntry.setType(EJournalEntryType.GotKilled);
                journalEntry.setParam6(owner.getName() + ((guild == null) ? "" : ("(" + guild.getName() + ")")));
                this.addJournalEntryAndNotify(journalEntry);
            }
        } else {
            final JournalEntry journalEntry2 = new JournalEntry();
            journalEntry2.setDate(GameTimeService.getServerTimeInMillis());
            journalEntry2.setType(EJournalEntryType.Died);
            this.addJournalEntryAndNotify(journalEntry2);
        }
        final Servant currentVehicle = this.getCurrentVehicle();
        if (currentVehicle != null) {
            currentVehicle.unMount(this);
        }

        this.sendBroadcastGuildAndRegionPacket(new SMDiePlayer(killerGameObjId, this.getGameObjectId(), false, false, actionHash), attacker != null && (attacker.isPlayer() || attacker.getOwner() != null && attacker.getOwner().isPlayer()));
    }

    public SocialActionStorage getSocialActionStorage() {
        return this.getAccountData().getSocialActionStorage();
    }

    public int getPcNonSavedCacheCount() {
        return this.pcNonSavedCacheCount;
    }

    public LoginAccountInfo getLoginAccount() {
        return this.getAccountData().getLoginAccountInfo();
    }

    public HouseStorage getHouseStorage() {
        return this.accountData.getHouseStorage();
    }

    public DyeStorage getDyeStorage() {
        return this.accountData.getDyeStorage();
    }

    public AccountData getAccountData() {
        return this.accountData;
    }

    public FriendHandler getFriendHandler() {
        return this.accountData.getFriendHandler();
    }

    public ChallengeHandler getChallengeHandler() {
        return this.accountData.getChallengeHandler();
    }

    public int getGearScore() { // Evasion is included, because it is part of visual DP.
        int gearScore = 0;

        Function<Element, Integer> sumElementScore = elementToSum -> (elementToSum.getDValue()[1] + elementToSum.getDValue().length > 2 ? elementToSum.getDValue()[2] : 0);
        Function<List<Element>, Integer> sumMultipleElementsScore = elements -> {
            int score = 0;
            for (Element el : elements)
                score += (el.getDValue()[1] + el.getDValue().length > 2 ? el.getDValue()[2] : 0);
            return score;
        };

        // AP Character
        gearScore += sumElementScore.apply(getTemplate().getGameStatsTemplate().getBaseElement(StatEnum.DDD));
        gearScore += sumElementScore.apply(getTemplate().getGameStatsTemplate().getBaseElement(StatEnum.RDD));
        gearScore += sumElementScore.apply(getTemplate().getGameStatsTemplate().getBaseElement(StatEnum.MDD));

        // AP Gear
        gearScore += sumMultipleElementsScore.apply(getGameStats().getStat(StatEnum.DDD).getElements());
        gearScore += sumMultipleElementsScore.apply(getGameStats().getStat(StatEnum.RDD).getElements());
        gearScore += sumMultipleElementsScore.apply(getGameStats().getStat(StatEnum.MDD).getElements());

        // DP Normal
        gearScore += getGameStats().getStat(StatEnum.DDV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.RDV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.MDV).getIntMaxValue();

        // DP Awakening
        gearScore += getGameStats().getStat(StatEnum.HDDV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.HRDV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.HMDV).getIntMaxValue();

        // Evasion Normal
        gearScore += getGameStats().getStat(StatEnum.DPV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.RPV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.MPV).getIntMaxValue();

        // Evasion Awakening
        gearScore += getGameStats().getStat(StatEnum.HDPV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.HRPV).getIntMaxValue();
        gearScore += getGameStats().getStat(StatEnum.HMPV).getIntMaxValue();

        return gearScore;
    }

    public MailBox getMailBox() {
        if (this.mailBox == null) {
            this.mailBox = new MailBox(this);
        }
        return this.mailBox;
    }

    public ExplorePointHandler getExplorePointHandler() {
        return this.accountData.getExplorePointHandler();
    }

    public FitnessHandler getFitnessHandler() {
        return this.fitnessHandler;
    }

    public Exploration getExploration() {
        return this.accountData.getExploration();
    }

    public QuickSlot getQuickSlot() {
        return this.quickSlot;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public PlayerAppearanceStorage getPlayerAppearance() {
        return this.playerAppearance;
    }

    public void setPlayerAppearance(final PlayerAppearanceStorage playerAppearance) {
        this.playerAppearance = playerAppearance;
        this.recalculateBasicCacheCount();
        this.recalculatePcCustomizationCacheCount();
    }

    public PlayerRenderStorage getPlayerRenderStorage() {
        return this.playerRenderStorage;
    }

    public PlayerAggroList getAggroList() {
        return (PlayerAggroList) super.getAggroList();
    }

    public void onLogin() {
        this.sendPacket(new SMCancelFieldEnterWaiting());
        GuildService.getInstance().onLogin(this);
        this.sendPacket(new SMSetGameTime());
        FamilyService.getInstance().onPlayerLogin(this);
        this.sendPacket(new SMEnterPlayerCharacterToField(this));
        this.sendPacket(new SMLoadField());
        sendPacketNoFlush(new SMVolunteerInfo());
        this.sendPacketNoFlush(new SMVariExtendSlot());
        this.sendPacketNoFlush(new SMSkillList(this));
        this.sendPacketNoFlush(new SMSkillAwakenList(this.getSkillList().getAwakenings()));
        this.getPlayerBag().onLogin();
        for (final LifeExperienceInformation lifeExperience : this.getLifeExperienceStorage().getLifeExperiences()) {
            this.sendPacketNoFlush(new SMLifeExperienceInformation(lifeExperience));
        }
        this.sendPacketNoFlush(new SMPaymentPassword(this));
        this.sendPacketNoFlush(new SMListFitnessExperience(this.getFitnessHandler()));
        final ListSplitter<CashItemT> cashItems = new ListSplitter<>(CashItemService.getInstance().getCashItems(), 214);
        while (!cashItems.isLast()) {
            this.sendPacketNoFlush(new SMListCashItem(cashItems.getNext(), CashItemService.getInstance().getShopTime(), (cashItems.isLast() ? 1 : 0)));
        }
        this.sendPacketNoFlush(new SMSetCharacterLevels(this));
        this.sendPacketNoFlush(new SMSetCharacterPrivatePoints(this));
        this.sendPacketNoFlush(new SMSetCharacterStats(this));
        this.sendPacketNoFlush(new SMSetCharacterSpeeds(this.getGameStats()));
        this.sendPacketNoFlush(new SMSetCharacterSkillPoints(this.getSkillList()));
        this.sendPacketNoFlush(new SMSetCharacterProductSkillPoints());
        this.sendPacketNoFlush(new SMSetCharacterStatPoint(this));
        this.sendPacketNoFlush(new SMQuickSlotList(this));
        this.getPlayerQuestHandler().onLogin();
        if (this.getMailBox().hasNewMails()) {
            this.sendPacket(new SMNewMail());
        }
        if (this.getFriendHandler().hasNewReq()) {
            this.sendPacket(new SMNewRequestFriend());
        }
        if (!this.getTitleHandler().getTitles().isEmpty()) {
            this.sendPacket(new SMAcquiredTitles(this.getTitleHandler().getTitles()));
        }
        final int titleId = this.getTitleHandler().getTitleId();
        if (titleId > 0) {
            this.sendPacket(new SMUpdateTitleKey(titleId, this.getGameObjectId()));
        }
        this.getEventStorage().onLogin();
        this.sendPacketNoFlush(new SMListIntimacy(this.getIntimacyHandler().getIntimacyMap()));
        this.sendPacketNoFlush(new SMSetMurdererState(this));
        this.sendPacketNoFlush(new SMExplorationInfo(this.getExploration().getDiscoveryList()));
        this.sendPacketNoFlush(new SMExplorePointList(this.getExplorePointHandler().getTerritories()));
        this.sendPacketNoFlush(new SMReservedLearningSkill());
        this.getHouseStorage().onLogin();
        if (!this.getMentalCardHandler().getThemes().isEmpty()) {
            final ListSplitter<Card> splitter = new ListSplitter<>(this.getMentalCardHandler().getCards(), SMListMentalCard.getMaximum());
            while (splitter.hasNext()) {
                this.sendPacketNoFlush(new SMListMentalCard(splitter.getNext()));
            }
        }
        this.sendPacketNoFlush(new SMSetWp(this.getCurrentWp(), this.getBaseWp()));
        this.getChallengeHandler().onLogin();
        final HashMap<Integer, CashProductBuyCount> productByCount = this.accountData.getProductByCount();
        if (!productByCount.isEmpty()) {
            this.sendPacketNoFlush(new SMListCashProductBuyCount(productByCount.values()));
        }
        this.getEncyclopediaStorage().onLogin();
        this.getDyeStorage().onLogin();
        this.sendPacketNoFlush(new SMNotifySkillAwakeningInfo(this.getSkillList().getNormalAwakenings().size(), this.getSkillList().getWeaponAwakenings().size()));
        final Guild guild = this.getGuild();
        if (guild != null) {
            guild.onEvent(new LoginGuildEvent(guild, this, false));
        }
        InstanceSummonService.getInstance().onLogin(this);
        if (World.getInstance().spawn(this, true, false)) {
            this.getServantController().onLogin();
            this.getSocialActionStorage().onLogin();
            FixedHouseService.getInstance().onLogin(this);
            final SMPlayerLogOnOff logOnOff = new SMPlayerLogOnOff(this, true);
            for (final Player p : World.getInstance().getPlayers()) {
                p.getFriendHandler().onLogInFriend(this);
                this.getFriendHandler().onLogInFriend(p);
                p.sendPacketNoFlush(logOnOff);
            }
            this.getBuffList().getBuffs().stream().filter(buff -> buff.getTemplate().getValidityTime() > 0L).forEach(buff -> buff.startEffect(ApplyBuffType.EnterWorld));
            final Collection<CoolTime> coolTimes = this.getCoolTimeList().getAllCoolTimes();
            if (!coolTimes.isEmpty()) {
                this.sendPacketNoFlush(new SMCoolTimeList(coolTimes));
            }
            this.accountData.getChargeUserStorage().initPackageEffects(this);
            if (this.getCustomizedKeys() != null) {
                this.sendPacketNoFlush(new SMLoadCustomizedKeys(this));
            }
            this.sendPacketNoFlush(new SMSetCharacterPublicPoints(this, 0.0f));
            this.sendPacketNoFlush(new SMSetCharacterRelatedPoints(this, 0));
            // Trading
            this.sendPacketNoFlush(new SMBidTerritoryTradeAuthority());
            this.sendPacketNoFlush(new SMUpdateRandomShopInfo());
            this.sendPacketNoFlush(new SMSupplyTerritoryStart());
            // End trading
            this.sendPacket(new SMLoadFieldComplete());
            this.sendPacket(new SMEnterPlayerCharacterToFieldComplete(this));
            if (this.canPvP()) {
                this.sendBroadcastItSelfPacket(new SMEnablePvP(this));
            }
            if (this.getLevel() >= BattleOptionConfig.ADRENALIN_SUPER_SKILL_MIN_LEVEL) {
                this.sendBroadcastItSelfPacket(new SMEnableAdrenalin());
            }
            PartyService.getInstance().onLogin(this);
            this.getNpcWorkerController().onLogin();
            this.getTitleHandler().onLogin();
            ItemMarketService.getInstance().onLogin(this);
            FishingService.getInstance().onLogin(this);
            if (this.isDead()) {
                this.sendPacket(new SMDiePlayer(this.getGameObjectId(), this.getGameObjectId(), true, false));
            }
            if (this.getLastLogout() > 0L) {
                final int logOutHours = (int) ((this.getLastLogin() - this.getLastLogout()) / 1000L / 60L / 60L);
                if (logOutHours > 0) {
                    this.addWp(logOutHours);
                }
            }
            this.sendPacketNoFlush(new SMChangeServerExpAndItemDropPercent());
            this.sendPacketNoFlush(new SMSetRegionProductivity());
            WeatherService.getInstance().onLogin(this);
            this.getBanController().onLogin();
            ChatService.getInstance().onLogin(this);

            if (this.getAi() != null)
                this.getAi().notifyStart();
        }
    }

    public void onDisconnect() {
        Player.log.debug("onDespawn: " + this.getName());
        ChatChannelController.disposePlayer(this, true);
        if (this.getAi() != null)
            this.getAi().notifyStop();
        final Trade trade = this.getTrade();
        if (trade != null) {
            trade.onEvent(new CancelItemExchangeEvent(this, trade));
        }
        this.getBuffList().stopBuffTasks();
        final Guild guild = this.getGuild();
        if (guild != null) {
            guild.onEvent(new LogoutGuildEvent(guild, this));
        }
        final IParty<Player> party = this.getParty();
        if (party != null) {
            party.onEvent(new LogoutPartyEvent(party, this));
        }
        GuildService.getInstance().onLogout(this);
        PvPBattleService.getInstance().cancelDuel(this);
        final Collection<Servant> servants = this.getServantController().getServants(EServantState.Field);
        for (final Servant servant : servants) {
            servant.seal(EServantSealType.LOGOUT);
        }
        final Servant servant2 = this.getCurrentVehicle();
        if (servant2 != null && (servant2.getOwner() == null || servant2.getOwner() != this)) {
            servant2.unMount(this);
        }
        for (final Creature summon : this.getSummonStorage().getSummons()) {
            World.getInstance().deSpawn(summon, ERemoveActorType.DespawnSummon);
        }
        final Servant tameServant = this.getServantController().getTameServant();
        if (tameServant != null) {
            World.getInstance().deSpawn(tameServant, ERemoveActorType.DespawnTamedServant);
        }
        FixedHouseService.getInstance().onLogout(this);
        if (this.escort != null) {
            this.escort.getAggroList().clear(true);
            this.escort.getAi().HandlerEscort_Reset(this, null);
        }
        if (World.getInstance().deSpawn(this, ERemoveActorType.Logout)) {
            final LocalWarMember localWarMember = LocalWarService.getInstance().getLocalWarStatus().removeFromLocalWar(this);
            if (localWarMember != null)
                this.getLocation().setLocation(localWarMember.getReturnLoc());
            if (getPVPController().getReturnPosition() != null)
                getLocation().setLocation(getPVPController().getReturnPosition());
            Player.log.info("savePlayer: " + this.getName());
            this.accountData.setLastLogout(GameTimeService.getServerTimeInMillis());
            this.setLastLogout(GameTimeService.getServerTimeInMillis());
            this.getBuffList().getBuffs().forEach(ActiveBuff::stopTask);
            this.getGameStats().getHp().stopHpRegenTask();
            this.getGameStats().getMp().stopMpRegenTask();
            this.save();
            final SMPlayerLogOnOff logOnOff = new SMPlayerLogOnOff(this, false);
            for (final Player player : World.getInstance().getPlayers()) {
                player.getFriendHandler().onLogOutFriend(this);
                player.sendPacketNoFlush(logOnOff);
            }
            GameServerIDFactory.getInstance().releaseId(GSIDStorageType.ONLINE, (long) this.getGameObjectId());
        }
    }

    public void save() {
        MainServer.getRmi().saveAccountData(this.accountData);
        AccountsDBCollection.getInstance().update(this.accountData);
        PlayersDBCollection.getInstance().update(this);
        this.getServantController().save();
        this.getNpcWorkerController().save();
        this.getHouseholdController().save();
    }

    public boolean canRespawn() {
        return false;
    }

    @SuppressWarnings("unchecked")
    public IParty<Player> getParty() {
        return this.party;
    }

    public GameClient getClient() {
        return this.client;
    }

    public void setClient(final GameClient client) {
        this.client = client;
    }

    public long getAccountId() {
        return this.accountData.getObjectId();
    }

    public EAccessLevel getAccessLevel() {
        return this.getAccountData().getAccessLevel();
    }

    public int getLearnSkillCacheCount() {
        return this.learnSkillCacheCount;
    }

    public void setLearnSkillCacheCount(final int learnSkillCacheCount) {
        this.learnSkillCacheCount = learnSkillCacheCount;
    }

    public long getPreemptiveStrikeTime() {
        return (this.preemptiveStrikeTime > GameTimeService.getServerTimeInMillis()) ? this.preemptiveStrikeTime : 0L;
    }

    public void setPreemptiveStrike() {
        if (this.getPreemptiveStrikeTime() == 0L) {
            this.addTendency(LocalizingOptionConfig.PREEMPTIVE_ATTACK_TENDENCY);
        }
        this.preemptiveStrikeTime = GameTimeService.getServerTimeInMillis() + LocalizingOptionConfig.PREEMPTIVE_STRIKE_KEEPING_TIME;
        this.sendBroadcastItSelfPacket(new SMSetPreemptiveStrike(this.getGameObjectId(), this.preemptiveStrikeTime));
    }

    public void sendPacket(final SendablePacket<GameClient> sp) {
        if (this.client != null) {
            this.client.sendPacket(sp);
        }
    }

    public void sendPacketNoFlush(final SendablePacket<GameClient> sp) {
        if (this.client != null) {
            this.client.sendPacketNoFlush(sp);
        }
    }

    public void sendBroadcastItSelfPacket(final SendablePacket<GameClient> sp) {
        this.sendPacket(sp);
        this.sendBroadcastPacket(sp);
    }

    public void dispatchSystemMessage(final String message, EChatType type, EChatNoticeType notice) {
        sendPacket(new SMChat("OgreFest", "OgreFest", -1024, type, notice, message));
    }

    public void sendMessage(final String message, final boolean chatMessage) {
        this.sendPacket(new SMChat(this.getName(), this.getFamily(), this.getGameObjectId(), chatMessage ? EChatType.System : EChatType.Notice, chatMessage ? EChatNoticeType.GuildBoss : EChatNoticeType.None, message));
    }

    public PlayerBag getPlayerBag() {
        return this.playerBag;
    }

    public PlayerQuestHandler getPlayerQuestHandler() {
        return this.playerQuestHandler;
    }

    @Override
    public void onLevelChange(boolean sendPacket) {
        this.skillList.updateSkills(this.getClassType().getId(), this.level);
        this.observeController.notifyObserver(EObserveType.levelUp, this.level);
        this.getGameStats().getHp().fill();
        this.getGameStats().getMp().fill();
        if (sendPacket) {
            this.sendPacket(new SMSetCharacterPrivatePoints(this));
            this.sendPacket(new SMSetStunPoints(this));
            if (this.canPvP()) {
                this.sendBroadcastItSelfPacket(new SMEnablePvP(this));
            }
            if (this.getLevel() >= BattleOptionConfig.ADRENALIN_SUPER_SKILL_MIN_LEVEL) {
                this.sendBroadcastItSelfPacket(new SMEnableAdrenalin());
            }
        }
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setType(EJournalEntryType.LevelAchieved);
        journalEntry.setParam0((short) this.level);
        this.addJournalEntryAndNotify(journalEntry);
        final Guild guild = this.getGuild();
        if (guild != null) {
            final int activityPoints = GuildConfig.INCREASE_ACTIVITY_MEMBER_LEVEL_UP * this.level;
            this.addActivityPoints(activityPoints);
            if (sendPacket) {
                guild.sendBroadcastPacket(new SMGuildMemberLevelUp(guild.getObjectId(), this.getAccountId(), this.level));
                guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.MEMBER_LEVEL_UP, guild, getAccountId(), activityPoints));
            }
            guild.getGuildSkillList().addSkillExp(GuildConfig.INCREASE_EXP_MEMBER_LEVEL_UP * this.level);
        }
    }

    public ObserveController getObserveController() {
        return this.observeController;
    }

    public int getLocalWarPoints() {
        return this.localWarPoints;
    }

    public void addLocalWarPoints(int amount) {
        this.localWarPoints += amount;
    }

    public long getExp() {
        return this.exp;
    }

    public void setExp(final long exp) {
        this.exp = exp;
    }

    public synchronized boolean addTendency(double exp) {
        if (exp > 0.0) {
            double ratedExp = exp * RateConfig.TENDENCY_RATE_EXP / 100.0;
            final int expStat = this.getGameStats().getTendencyEXP().getIntMaxValue() / 10000;
            if (expStat > 0) {
                ratedExp += ratedExp * expStat / 100.0;
            }
            exp = ratedExp;
        }
        if (exp < 0.0 && this.tendency == PlayerConfig.MIN_TENDENCY) {
            return false;
        }
        if (exp > 0.0 && this.tendency == PlayerConfig.MAX_TENDENCY) {
            return false;
        }
        if (this.tendency + exp < PlayerConfig.MIN_TENDENCY) {
            this.tendency = PlayerConfig.MIN_TENDENCY;
        } else if (this.tendency + exp > PlayerConfig.MAX_TENDENCY) {
            this.tendency = PlayerConfig.MAX_TENDENCY;
        } else {
            this.tendency += (int) exp;
        }
        this.sendBroadcastItSelfPacket(new SMSetTendency(this.getGameObjectId(), this.tendency));
        return true;
    }

    public synchronized boolean addWp(final int wp) {
        final int maxWp = this.getMaxWp();
        if (wp > 0 && this.getCurrentWp() >= maxWp) {
            return false;
        }
        if (this.getCurrentWp() + wp < 0) {
            return false;
        }
        this.setCurrentWp(this.getCurrentWp() + wp);
        if (this.getCurrentWp() > maxWp) {
            this.setCurrentWp(maxWp);
        }
        this.sendPacket(new SMSetWp(this.getCurrentWp(), this.getBaseWp()));
        return true;
    }

    public int getBaseWp() {
        return EtcOptionConfig.BASE_WP_AMOUNT;
    }

    public int getMaxWp() {
        return this.getBaseWp() + this.getMentalCardHandler().getWpBonus();
    }

    public void addExpRaw(final long exp) {
        this.exp += exp;
        this.sendPacket(new SMSetCharacterLevels(this, false));
    }

    public void addExp(final double exp) {
        this.addExp(exp, true);
    }

    public synchronized void addExp(double exp, final boolean combat) {
        if (this.isDead() || exp <= 0.0 || this.level == PlayerConfig.MAX_LEVEL) {
            return;
        }
        final PCTemplate template = this.getPCTemplate();
        final double limit = combat ? template.getRequireExpLimit() : template.getRequireExp();
        if (exp > limit) {
            exp = limit;
        }
        boolean levelUp = false;
        double ratedExp = exp * RateConfig.RATE_EXP / 100.0;
        final int expStat = this.getGameStats().getExpRate().getIntMaxValue() / 10000;
        if (expStat > 0) {
            ratedExp += ratedExp * expStat / 100.0;
        }
        final double reqExp = template.getRequireExp();
        if (this.level == 49 && this.exp + ratedExp >= reqExp) {
            final String[] values = BattleOptionConfig.EXP_FINAL_LEVEL_CONDITION_QUEST.split(",");
            final int groupId = Integer.parseInt(values[0]);
            final int questId = Integer.parseInt(values[1]);
            if (!this.getPlayerQuestHandler().isClearedQuest(groupId, questId)) {
                this.setExp((int) (reqExp - 1.0));
            } else {
                this.setExp(this.exp + (long) ratedExp);
            }
        } else {
            this.setExp(this.exp + (long) ratedExp);
        }
        if (this.exp >= reqExp) {
            ++this.level;
            this.setExp(this.exp - (long) (combat ? ((this.level < 99) ? reqExp : this.exp) : this.exp));
            levelUp = true;
        }
        this.sendPacket(new SMSetCharacterLevels(this, levelUp));
        if (levelUp) {
            this.onLevelChange(true);
            while (this.exp > this.getPCTemplate().getRequireExp() && this.level < PlayerConfig.MAX_LEVEL) {
                this.exp -= (long) this.getPCTemplate().getRequireExp();
                ++this.level;
                this.onLevelChange(true);
                this.sendPacket(new SMSetCharacterLevels(this, levelUp));
            }
        }
    }

    public boolean isCreatureVisible() {
        return this.isVisible;
    }

    public EClassType getClassType() {
        return this.playerTemplate.getPlayerClass();
    }

    public PCTemplate getPCTemplate() {
        return PCData.getInstance().getTemplate(this.getClassType().getId(), this.getLevel());
    }

    public long getLastLogin() {
        return this.lastLogin;
    }

    public long getLastLogout() {
        return this.lastLogout;
    }

    public void setLastLogout(final long lastLogout) {
        this.lastLogout = lastLogout;
    }

    public long getCreationDate() {
        return this.creationDate;
    }

    public long getDeletionDate() {
        return this.deletionDate;
    }

    public long getBlockDate() {
        return this.blockDate;
    }

    public long getPlayedTime() {
        return GameTimeService.getServerTimeInMillis() - this.lastLogin + this.playedTime;
    }

    public long getExistenceTime() {
        return GameTimeService.getServerTimeInSecond() - this.getLastLogin() / 1000L;
    }

    public PCSetTemplate getPlayerTemplate() {
        return this.playerTemplate;
    }

    public int getSlot() {
        return this.slot;
    }

    public EZodiacType getZodiac() {
        return this.zodiac;
    }

    public PlayerLifeExperienceStorage getLifeExperienceStorage() {
        return this.lifeExperienceStorage;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public void setGuild(final Guild guild) {
        this.guild = guild;
    }

    public EGuildMemberRankType getGuildMemberRankType() {
        final GuildMember guildMember = this.getGuildMember();
        if (guildMember != null) {
            return guildMember.getRank();
        }
        return EGuildMemberRankType.None;
    }

    public GuildMember getGuildMember() {
        final Guild guild = this.getGuild();
        GuildMember guildMember = null;
        if (guild != null) {
            guildMember = guild.getMember(this.getAccountId());
        }
        return guildMember;
    }

    public long getGuildId() {
        final Guild guild = this.getGuild();
        return (guild != null) ? guild.getObjectId() : 0L;
    }

    public long getGuildCache() {
        final Guild guild = this.getGuild();
        return (guild != null) ? guild.getObjectId() : this.getCache();
    }

    public boolean hasGuild() {
        return this.getGuild() != null;
    }

    public long getPartyCache() {
        final IParty<Player> party = this.getParty();
        return (party != null) ? party.getPartyId() : this.getCache();
    }

    public int getCreationIndex() {
        return this.creationIndex;
    }

    public long getRescueCoolTime() {
        return this.rescueCoolTime;
    }

    public void setRescueCoolTime(final long rescueCoolTime) {
        this.rescueCoolTime = rescueCoolTime;
    }

    public boolean hasTrade() {
        return this.trade != null;
    }

    public Trade getTrade() {
        return this.trade;
    }

    public void setTrade(final Trade trade) {
        this.trade = trade;
    }

    public void setAvatarEquip(final int avatarEquip) {
        this.avatarEquip = avatarEquip;
    }

    public int getTendency() {
        return this.tendency;
    }

    public int getEnchantFailCount() {
        return this.enchantFailCount;
    }

    public void setEnchantFailCount(final int enchantFailCount) {
        this.enchantFailCount = enchantFailCount;
    }

    public int getEnchantSuccessCount() {
        return this.enchantSuccessCount;
    }

    public void setEnchantSuccessCount(final int enchantSuccessCount) {
        this.enchantSuccessCount = enchantSuccessCount;
    }

    public int getCurrentWp() {
        return this.currentWp;
    }

    public void setCurrentWp(final int currentWp) {
        this.currentWp = currentWp;
    }

    public Memo getMemo() {
        return this.memo;
    }

    public void setMemo(final Memo memo) {
        this.memo = memo;
    }

    public byte[] getCustomizedKeys() {
        return this.customizedKeys;
    }

    public void setCustomizedKeys(final byte[] customizedKeys) {
        this.customizedKeys = customizedKeys;
    }

    public TitleHandler getTitleHandler() {
        return this.accountData.getTitleHandler();
    }

    public String getFamily() {
        return this.accountData.getFamilyName();
    }

    public void addJournalEntryAndNotify(final JournalEntry journalEntry) {
        this.playerJournal.addJournalEntry(journalEntry);
        if (this.client != null) {
            this.sendPacket(new SMAcquiredJournal(EJournalType.Character, journalEntry));
        }
    }

    public Journal getJournal(final EJournalType journalType) {
        if (journalType == EJournalType.Character) {
            return this.playerJournal;
        }
        if (journalType != EJournalType.Guild) {
            return null;
        }
        if (this.hasGuild()) {
            return this.getGuild().getJournal();
        }
        return null;
    }

    public boolean isDeadInDuel() {
        return this.isDeadInDuel;
    }

    public void setDeadInDuel(final boolean deadInDuel) {
        this.isDeadInDuel = deadInDuel;
    }

    public PlayerSkillList getSkillList() {
        return this.skillList;
    }

    public IntimacyHandler getIntimacyHandler() {
        return this.accountData.getIntimacyHandler();
    }

    public MentalCardHandler getMentalCardHandler() {
        return this.accountData.getMentalCardHandler();
    }

    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        if (object.isPlayer()) {
            final Player p = (Player) object;
            this.sendPacket(new SMAddPlayers(Collections.singletonList((Player) object), this, isNewSpawn, isRespawn));
            final Collection<Servant> activePets = p.getServantController().getServants(EServantState.Field, EServantType.Pet);
            if (!activePets.isEmpty()) {
                this.sendPacket(new SMActivePetPublicInfo(activePets, EPacketTaskType.Add));
            }
            final Collection<ActiveBuff> buffs = p.getBuffList().getBuffs();
            if (!buffs.isEmpty())
                this.sendPacket(new SMAddAppliedActiveBuffs(buffs));
        } else if (object.isMonster())
            this.sendPacket(new SMAddMonsters(Collections.singletonList((Monster) object)));
        else if (object.isNpc())
            this.sendPacket(new SMAddNpcs(Collections.singletonList((Npc) object)));
        else if (object.isDeadbody()) {
            if (object instanceof HouseHold) { // TODO: FIXME!
                log.error("Player tried to enter with {} deadbody but counted as household.", object.getCreatureId());
                return true;
            }
            this.sendPacket(new SMAddDeadBodys(Collections.singletonList((DeadBody) object)));
        }
        else if (object.isVehicle())
            this.sendPacket(new SMAddVehicles(Collections.singletonList((Servant) object)));
        else if (object.isCollect())
            this.sendPacket(new SMAddCollectInfo(EPacketTaskType.Add, Collections.singletonList((Collect) object), subSectorX, subSectorY));
        else if (object.isGate())
            this.sendPacket(new SMAddGate(Collections.singletonList((Gate) object)));
        else if (object.isHousehold())
            this.sendPacket(new SMAddTents(Collections.singletonList((HouseHold) object)));
        return true;
    }

    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        if (objects.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty!");
        }
        switch (type) {
            case Player: {
                @SuppressWarnings("unchecked") final ListSplitter<Player> splitter = (ListSplitter<Player>) new ListSplitter(objects, SMAddPlayers.getMaximum());
                while (splitter.hasNext()) {
                    sendPacketNoFlush(new SMAddPlayers(splitter.getNext(), this, false, false));
                }
                for (final Creature creature : objects) {
                    final Player p = (Player) creature;
                    final Collection<Servant> activePets = p.getServantController().getServants(EServantState.Field, EServantType.Pet);
                    if (!activePets.isEmpty()) {
                        sendPacketNoFlush(new SMActivePetPublicInfo(activePets, EPacketTaskType.Add));
                    }
                    final Collection<ActiveBuff> buffs = p.getBuffList().getBuffs();
                    if (!buffs.isEmpty()) {
                        sendPacketNoFlush(new SMAddAppliedActiveBuffs(buffs));
                    }
                }
                break;
            }
            case Monster: {
                @SuppressWarnings("unchecked") final ListSplitter<Monster> splitter2 = (ListSplitter<Monster>) new ListSplitter(objects, SMAddMonsters.getMaximum());
                while (splitter2.hasNext()) {
                    sendPacketNoFlush(new SMAddMonsters(splitter2.getNext()));
                }
                break;
            }
            case Npc: {
                @SuppressWarnings("unchecked") final ListSplitter<Npc> splitter3 = (ListSplitter<Npc>) new ListSplitter(objects, 15);
                while (splitter3.hasNext()) {
                    sendPacketNoFlush(new SMAddNpcs(splitter3.getNext()));
                }
                break;
            }
            case Deadbody: {
                @SuppressWarnings("unchecked") final ListSplitter<DeadBody> splitter4 = (ListSplitter<DeadBody>) new ListSplitter(objects, SMAddDeadBodys.getMaximum());
                while (splitter4.hasNext()) {
                    sendPacketNoFlush(new SMAddDeadBodys(splitter4.getNext()));
                }
                break;
            }
            case Vehicle: {
                @SuppressWarnings("unchecked") final ListSplitter<Servant> splitter5 = (ListSplitter<Servant>) new ListSplitter(objects, SMAddVehicles.getMaximum());
                while (splitter5.hasNext()) {
                    sendPacketNoFlush(new SMAddVehicles(splitter5.getNext()));
                }
                break;
            }
            case Collect: {
                @SuppressWarnings("unchecked") final ListSplitter<Collect> splitter6 = (ListSplitter<Collect>) new ListSplitter(objects, SMAddCollectInfo.getMaximum());
                while (splitter6.hasNext()) {
                    sendPacketNoFlush(new SMAddCollectInfo(EPacketTaskType.Add, splitter6.getNext(), subSectorX, subSectorY));
                }
                break;
            }
            case Alterego: {
                @SuppressWarnings("unchecked") final ListSplitter<Gate> splitter7 = (ListSplitter<Gate>) new ListSplitter(objects, SMAddGate.getMaximum());
                while (splitter7.hasNext()) {
                    sendPacketNoFlush(new SMAddGate(splitter7.getNext()));
                }
                break;
            }
            case Household: {
                @SuppressWarnings("unchecked") final ListSplitter<HouseHold> splitter8 = (ListSplitter<HouseHold>) new ListSplitter(objects, SMAddTents.getMaximum());
                while (splitter8.hasNext()) {
                    sendPacketNoFlush(new SMAddTents(splitter8.getNext()));
                }
                break;
            }
        }
        return true;
    }

    public boolean notSee(final Creature object, final ERemoveActorType type, final boolean outOfRange) {
        if (outOfRange && !type.isNone()) {
            if (type.isCollect()) {
                this.sendPacket(new SMRemoveCollectInfo((Collect) object));
            } else {
                this.sendPacket(new SMRemoveActor(object, type));
            }
        }
        return super.notSee(object, type, outOfRange);
    }

    public boolean notSee(final List<? extends Creature> objects, final ERemoveActorType type, final boolean outOfRange) {
        if (outOfRange && !type.isNone()) {
            objects.forEach(object -> {
                if (type.isCollect())
                    sendPacketNoFlush(new SMRemoveCollectInfo((Collect) object));
                else
                    sendPacketNoFlush(new SMRemoveActor(object, type));
            });
        }
        return super.notSee(objects, type, outOfRange);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder object = BasicDBObjectBuilder.start();
        object.append("_id", this.objectId);
        object.append("name", this.name);
        object.append("slot", this.slot);
        object.append("zodiac", this.zodiac.getId());
        object.append("classType", this.getClassType().getId());
        object.append("level", this.level);
        object.append("exp", this.exp);
        object.append("tendency", this.tendency);
        object.append("lastLogin", this.lastLogin);
        object.append("lastLogout", this.lastLogout);
        object.append("creationDate", this.creationDate);
        object.append("deletionDate", this.deletionDate);
        object.append("blockDate", this.blockDate);
        object.append("playedTime", this.getPlayedTime());
        object.append("rescueCoolTime", this.rescueCoolTime);
        object.append("currentWp", this.currentWp);
        object.append("enchantFailCount", this.enchantFailCount);
        object.append("enchantSuccessCount", this.enchantSuccessCount);
        object.append("equipSlotCacheCount", this.equipSlotCacheCount);
        object.append("basicCacheCount", this.basicCacheCount);
        object.append("pcNonSavedCacheCount", this.pcNonSavedCacheCount);
        object.append("pcCustomizationCacheCount", this.pcCustomizationCacheCount);
        object.append("localWarPoints", this.localWarPoints);
        object.append("creationIndex", this.creationIndex);
        object.append("accountId", this.getAccountId());
        object.append("appearance", this.playerAppearance.toDBObject());
        object.append("journal", this.playerJournal.toDBObject());
        object.append("location", this.getLocation().toDBObject());
        object.append("playerBag", this.playerBag.toDBObject());
        object.append("skillList", this.skillList.toDBObject());
        object.append("buffList", this.buffList.toDBObject());
        object.append("quickSlot", this.quickSlot.toDBObject());
        object.append("quests", this.playerQuestHandler.toDBObject());
        object.append("lifeExperience", this.lifeExperienceStorage.toDBObject());
        object.append("lifeStats", this.getLifeStats().toDBObject());
        object.append("coolTimeList", this.coolTimeList.toDBObject());
        object.append("fitness", this.fitnessHandler.toDBObject());
        final BasicDBList cashByCountDB = this.accountData.getProductByCount().values().stream().filter(cashProductBuyCount -> cashProductBuyCount.getCashItemT().getPurchaseSubject().isCharacter()).map(CashProductBuyCount::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        object.append("cashByCount", cashByCountDB);
        if (this.memo != null) {
            object.append("memo", this.memo.toDBObject());
        }
        if (this.customizedKeys != null) {
            object.append("customizedKeys", this.customizedKeys);
        }
        return object.get();
    }

    public XmlRpcPlayer toXMLRpcObject(final String message) {
        final XmlRpcPlayer rpcPlayer = new XmlRpcPlayer();
        rpcPlayer.setName(this.getName());
        rpcPlayer.setLevel(this.getLevel());
        rpcPlayer.setTendency(this.getTendency());
        rpcPlayer.setObjectId(this.getObjectId());
        rpcPlayer.setGuildId(this.getGuildId());
        rpcPlayer.setClassId(XmlRpcPlayerClassId.valueOf(this.getClassType().getId()));
        rpcPlayer.setZodiac(XmlRpcPlayerZodiac.valueOf(this.getZodiac().getId()));
        rpcPlayer.setWp(this.getCurrentWp());
        rpcPlayer.setCreationDate(this.getCreationDate());
        return rpcPlayer;
    }

    public PlayerGameStats getGameStats() {
        return (PlayerGameStats) super.getGameStats();
    }

    public PlayerLifeStats getLifeStats() {
        return (PlayerLifeStats) super.getLifeStats();
    }

    @Override
    public AbstractAddItemPack getInventory() {
        return this.playerBag.getInventory();
    }

    @Override
    public PlayerEquipments getEquipments() {
        return this.playerBag.getEquipments();
    }

    public CoolTimeList getCoolTimeList() {
        return this.coolTimeList;
    }

    public ECharKind getCharKind() {
        return ECharKind.Player;
    }

    public int getBattlePoints() {
        return this.battlePoints;
    }

    public void setBattlePoints(final int battlePoints) {
        this.battlePoints = battlePoints;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public void setLifePoints(final int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getSpecialPoints() {
        return this.specialPoints;
    }

    public void setSpecialPoints(final int specialPoints) {
        this.specialPoints = specialPoints;
    }

    public PlayerBanController getBanController() {
        if (this.banController == null) {
            this.banController = new PlayerBanController(this);
        }
        return this.banController;
    }

    public HouseholdController getHouseholdController() {
        if (this.householdController == null) {
            this.householdController = new HouseholdController(this);
        }
        return this.householdController;
    }

    public ServantController getServantController() {
        if (this.servantController == null) {
            this.servantController = new ServantController(this);
        }
        return this.servantController;
    }

    public NpcWorkerController getNpcWorkerController() {
        if (this.npcWorkerController == null) {
            this.npcWorkerController = new NpcWorkerController(this);
        }
        return this.npcWorkerController;
    }

    public ActionRestrictionController getActionRestrictionController() {
        if (this.actionRestrictionController == null)
            this.actionRestrictionController = new ActionRestrictionController(this);
        return this.actionRestrictionController;
    }

    public PVPController getPVPController() {
        if (this.pvpController == null) {
            this.pvpController = new PVPController(this);
        }
        return this.pvpController;
    }

    public PvpMatch getPvpMatch() {
        return this.getPVPController().getPvpMatch();
    }

    public boolean hasPvpMatch() {
        return this.getPvpMatch() != null;
    }

    public PlayerMovementController getMovementController() {
        if (this.movementController == null) {
            this.movementController = new PlayerMovementController(this);
        }
        return (PlayerMovementController) super.getMovementController();
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": name=" + this.getName() + ", account=" + this.getAccountData().getFamilyName() + ", gameObjectId=" + this.getGameObjectId();
    }
}
