package com.bdoemu.gameserver.model.creature.player;

import com.bdoemu.MainServer;
import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.commons.rmi.model.Macros;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.sendable.SMVaryCharacterSlotCount;
import com.bdoemu.gameserver.model.account.boardgame.BoardGameData;
import com.bdoemu.gameserver.model.alchemy.AlchemyRecordStorage;
import com.bdoemu.gameserver.model.creature.player.challenge.ChallengeHandler;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePointHandler;
import com.bdoemu.gameserver.model.creature.player.dye.DyeStorage;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.EncyclopediaStorage;
import com.bdoemu.gameserver.model.creature.player.exploration.Exploration;
import com.bdoemu.gameserver.model.creature.player.intimacy.IntimacyHandler;
import com.bdoemu.gameserver.model.creature.player.itemPack.AccountBag;
import com.bdoemu.gameserver.model.creature.player.mentalcard.MentalCardHandler;
import com.bdoemu.gameserver.model.creature.player.quests.ClearedQuest;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;
import com.bdoemu.gameserver.model.creature.player.social.actions.SocialActionStorage;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.titles.TitleHandler;
import com.bdoemu.gameserver.model.creature.servant.AccountServantStorage;
import com.bdoemu.gameserver.model.events.EventStorage;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.items.CashProductBuyCount;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class AccountData extends JSONable {
    private String comment;
    private int userBasicCacheCount;
    private int creationCharacterCount;
    private int activityPoints;
    private Exploration exploration;
    private AccountServantStorage servantStorage;
    private MentalCardHandler mentalCardHandler;
    private IntimacyHandler intimacyHandler;
    private TitleHandler titleHandler;
    private ExplorePointHandler explorePointHandler;
    private ChallengeHandler challengeHandler;
    private FriendHandler friendHandler;
    private AccountBag accountBag;
    private HouseStorage houseStorage;
    private DyeStorage dyeStorage;
    private SocialActionStorage socialActionStorage;
    private String familyName;
    private String paymentPin;
    private String paymentPinTable;
    private long paymentPinUpdatedTime;
    private long familyRewardCoolTime;
    private long lastLogin;
    private long lastLogout;
    private long firstLogin;
    private long playedTime;
    private long guildCoolTime;
    private long matingAuctionCoolTime;
    private long buyServantAuctionCoolTime;
    private String saveGameOptions;
    private String uiData;
    private Macros[] macroses;
    private LoginAccountInfo loginAccountInfo;
    private HashMap<Integer, CashProductBuyCount> productByCount;
    private AlchemyRecordStorage alchemyRecordStorage;
    private EncyclopediaStorage encyclopediaStorage;
    private Player player;
    private EventStorage eventStorage;
    private ChargeUserStorage chargeUserStorage;
    private BoardGameData boardGameData;
    private long accountId;
    private int chatChannelId;

    public AccountData(final Player player, final LoginAccountInfo loginAccountInfo) {
        this.comment = "";
        this.userBasicCacheCount = 1;
        this.paymentPin = "";
        this.macroses = new Macros[10];
        this.productByCount = new HashMap<Integer, CashProductBuyCount>();
        this.player = player;
        this.accountId = loginAccountInfo.getAccountId();
        this.lastLogin = GameTimeService.getServerTimeInMillis();
        this.firstLogin = GameTimeService.getServerTimeInMillis();
        this.lastLogout = GameTimeService.getServerTimeInMillis();
        this.loginAccountInfo = loginAccountInfo;
        this.familyName = loginAccountInfo.getFamily();
        this.exploration = new Exploration(player);
        this.servantStorage = new AccountServantStorage(player);
        this.mentalCardHandler = new MentalCardHandler(player);
        this.intimacyHandler = new IntimacyHandler(player);
        this.titleHandler = new TitleHandler(player);
        this.explorePointHandler = new ExplorePointHandler(player);
        this.challengeHandler = new ChallengeHandler(player, this);
        this.friendHandler = new FriendHandler(this.getLastLogout(), loginAccountInfo.getAccountId());
        this.accountBag = new AccountBag(player);
        this.houseStorage = new HouseStorage(player);
        this.dyeStorage = new DyeStorage(player);
        this.alchemyRecordStorage = new AlchemyRecordStorage();
        this.socialActionStorage = new SocialActionStorage(player);
        this.encyclopediaStorage = new EncyclopediaStorage(player);
        this.boardGameData = new BoardGameData();
        this.eventStorage = new EventStorage(player);
        this.chargeUserStorage = new ChargeUserStorage();
        this.macroses = loginAccountInfo.getMacroses();
        this.uiData = loginAccountInfo.getUiData();
        this.saveGameOptions = loginAccountInfo.getSaveGameOptions();
    }

    public AccountData(final BasicDBObject accountDbObject) {
        this.comment = "";
        this.userBasicCacheCount = 1;
        this.paymentPin = "";
        this.macroses = new Macros[10];
        this.productByCount = new HashMap<>();
        this.accountId = accountDbObject.getLong("_id");
        this.chargeUserStorage = new ChargeUserStorage((BasicDBObject) accountDbObject.get("chargeUserStorage"));
        this.mentalCardHandler = new MentalCardHandler(null);
        this.chatChannelId = accountDbObject.getInt("chatChannelId", -1);
    }

    public AccountData(final BasicDBObject accountDbObject, final Player player, final LoginAccountInfo loginAccountInfo) {
        this.comment = "";
        this.userBasicCacheCount = 1;
        this.paymentPin = "";
        this.macroses = new Macros[10];
        this.productByCount = new HashMap<>();
        this.player = player;
        this.lastLogin = GameTimeService.getServerTimeInMillis();
        this.accountId = accountDbObject.getLong("_id");
        this.lastLogout = accountDbObject.getLong("lastLogout");
        this.firstLogin = accountDbObject.getLong("firstLogin");
        this.playedTime = accountDbObject.getLong("playedTime");
        this.familyRewardCoolTime = accountDbObject.getLong("familyRewardCoolTime", 0L);
        this.guildCoolTime = accountDbObject.getLong("guildCoolTime");
        this.matingAuctionCoolTime = accountDbObject.getLong("matingAuctionCoolTime", 0L);
        this.buyServantAuctionCoolTime = accountDbObject.getLong("buyServantAuctionCoolTime", 0L);
        this.familyName = loginAccountInfo.getFamily();
        this.userBasicCacheCount = accountDbObject.getInt("userBasicCacheCount");
        this.comment = accountDbObject.getString("comment");
        this.chatChannelId = accountDbObject.getInt("chatChannelId", -1);
        this.paymentPin = accountDbObject.getString("paymentPin");
        this.paymentPinUpdatedTime = accountDbObject.getLong("paymentPinUpdatedTime");
        this.creationCharacterCount = accountDbObject.getInt("creationCharacterCount");
        this.activityPoints = accountDbObject.getInt("activityPoints");
        this.exploration = new Exploration(player, (BasicDBObject) accountDbObject.get("exploration"));
        this.mentalCardHandler = new MentalCardHandler(player, (BasicDBObject) accountDbObject.get("mentalCard"));
        this.intimacyHandler = new IntimacyHandler(player, (BasicDBObject) accountDbObject.get("intimacy"));
        this.titleHandler = new TitleHandler(player, (BasicDBObject) accountDbObject.get("titles"));
        this.explorePointHandler = new ExplorePointHandler(player, (BasicDBObject) accountDbObject.get("explorePoint"));
        this.challengeHandler = new ChallengeHandler(player, this, (BasicDBObject) accountDbObject.get("challenge"));
        this.friendHandler = new FriendHandler(this.getLastLogout(), this.accountId, (BasicDBObject) accountDbObject.get("friendInfo"));
        this.houseStorage = new HouseStorage(player, (BasicDBObject) accountDbObject.get("houseStorage"));
        this.accountBag = new AccountBag(player, (BasicDBObject) accountDbObject.get("accountBag"));
        this.alchemyRecordStorage = new AlchemyRecordStorage((BasicDBObject) accountDbObject.get("alchemyRecordStorage"));
        this.socialActionStorage = new SocialActionStorage(player, (BasicDBObject) accountDbObject.get("socialActionStorage"));
        this.encyclopediaStorage = new EncyclopediaStorage(player, (BasicDBObject) accountDbObject.get("encyclopediaStorage"));
        this.loginAccountInfo = loginAccountInfo;
        this.boardGameData = new BoardGameData((BasicDBObject) accountDbObject.get("boardGameData"));
        this.eventStorage = new EventStorage(player, (BasicDBObject) accountDbObject.get("eventStorage"));
        this.chargeUserStorage = new ChargeUserStorage((BasicDBObject) accountDbObject.get("chargeUserStorage"));
        final BasicDBList cashByCountDB = (BasicDBList) accountDbObject.get("cashByCount");
        for (final Object aCashByCountDB : cashByCountDB) {
            final BasicDBObject productDB = (BasicDBObject) aCashByCountDB;
            final CashProductBuyCount cashProductBuyCount = new CashProductBuyCount(productDB);
            if (cashProductBuyCount.getCashItemT() != null) {
                this.productByCount.put(cashProductBuyCount.getProductNr(), cashProductBuyCount);
            }
        }
        this.dyeStorage = new DyeStorage(player, (BasicDBObject) accountDbObject.get("dyeStorage"));
        final BasicDBList progressUserBasicQuestsDB = (BasicDBList) accountDbObject.get("progressUserBasicQuests");
        for (final Object progressUserBasicQuestDB : progressUserBasicQuestsDB) {
            final Quest quest = Quest.newQuest(player, (BasicDBObject) progressUserBasicQuestDB);
            if (quest != null) {
                player.getPlayerQuestHandler().putToProgressList(quest);
            }
        }
        final BasicDBList clearedUserBasicQuestsDB = (BasicDBList) accountDbObject.get("clearedUserBasicQuests");
        for (final Object clearedUserBasicQuestDB : clearedUserBasicQuestsDB) {
            final ClearedQuest clearedQuest = new ClearedQuest((BasicDBObject) clearedUserBasicQuestDB);
            if (clearedQuest.getTemplate() != null) {
                player.getPlayerQuestHandler().putToClearedList(clearedQuest);
            }
        }
        this.macroses = loginAccountInfo.getMacroses();
        this.uiData = loginAccountInfo.getUiData();
        this.saveGameOptions = loginAccountInfo.getSaveGameOptions();
    }

    public long getObjectId() {
        return this.accountId;
    }

    public int getChatChannelId() {
        return this.chatChannelId;
    }

    public void setChatChannelId(int newId) {
        this.chatChannelId = newId;
    }

    public EventStorage getEventStorage() {
        return this.eventStorage;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
        this.recalculateUserBasicCacheCount();
    }

    public synchronized void recalculateUserBasicCacheCount() {
        ++this.userBasicCacheCount;
    }

    public int getCreationCharacterCount() {
        return this.creationCharacterCount;
    }

    public int getUserBasicCacheCount() {
        return this.userBasicCacheCount;
    }

    public EncyclopediaStorage getEncyclopediaStorage() {
        return this.encyclopediaStorage;
    }

    public SocialActionStorage getSocialActionStorage() {
        return this.socialActionStorage;
    }

    public AlchemyRecordStorage getAlchemyRecordStorage() {
        return this.alchemyRecordStorage;
    }

    public DyeStorage getDyeStorage() {
        return this.dyeStorage;
    }

    public HouseStorage getHouseStorage() {
        return this.houseStorage;
    }

    public HashMap<Integer, CashProductBuyCount> getProductByCount() {
        return this.productByCount;
    }

    public CashProductBuyCount putProductByCount(final int productNr, final int count, final long buyDate) {
        final CashProductBuyCount cashProductBuyCount = this.productByCount.computeIfAbsent(productNr, integer -> new CashProductBuyCount(productNr, count, buyDate));
        cashProductBuyCount.set(count);
        return cashProductBuyCount;
    }

    public long getMatingAuctionCoolTime() {
        return this.matingAuctionCoolTime;
    }

    public void setMatingAuctionCoolTime(final long matingAuctionCoolTime) {
        this.matingAuctionCoolTime = matingAuctionCoolTime;
    }

    public long getBuyServantAuctionCoolTime() {
        return this.buyServantAuctionCoolTime;
    }

    public void setBuyServantAuctionCoolTime(final long buyServantAuctionCoolTime) {
        this.buyServantAuctionCoolTime = buyServantAuctionCoolTime;
    }

    public ChargeUserStorage getChargeUserStorage() {
        return this.chargeUserStorage;
    }

    public int getActivityPoints() {
        return this.activityPoints;
    }

    public void addActivityPoints(final int points) {
        this.activityPoints += points;
    }

    public CashProductBuyCount getProductByCount(final int productNr) {
        return this.productByCount.get(productNr);
    }

    public LoginAccountInfo getLoginAccountInfo() {
        return this.loginAccountInfo;
    }

    public AccountBag getAccountBag() {
        return this.accountBag;
    }

    public long getPlayedTime() {
        return GameTimeService.getServerTimeInMillis() - this.lastLogin + this.playedTime;
    }

    public long getLastLogIn() {
        return this.lastLogin;
    }

    public long getFirstLogin() {
        return this.firstLogin;
    }

    public long getLastLogout() {
        return this.lastLogout;
    }

    public void setLastLogout(final long lastLogout) {
        this.lastLogout = lastLogout;
    }

    public long getGuildCoolTime() {
        return this.guildCoolTime;
    }

    public void setGuildCoolTime(final long guildCoolTime) {
        this.guildCoolTime = guildCoolTime;
    }

    public long getFamilyRewardCoolTime() {
        return this.familyRewardCoolTime;
    }

    public void setFamilyRewardCoolTime(final long familyRewardCoolTime) {
        this.familyRewardCoolTime = familyRewardCoolTime;
    }

    public synchronized boolean setPaymentPin(final String paymentPin) {
        if (!this.paymentPin.isEmpty() || paymentPin.isEmpty()) {
            return false;
        }
        this.paymentPin = paymentPin;
        this.paymentPinUpdatedTime = System.currentTimeMillis();
        return true;
    }

    public String getPaymentPinTable() {
        return this.paymentPinTable;
    }

    public void setPaymentPinTable(final String paymentPinTable) {
        this.paymentPinTable = paymentPinTable;
    }

    public TitleHandler getTitleHandler() {
        return this.titleHandler;
    }

    public String getPaymentPin() {
        return this.paymentPin;
    }

    public long getPaymentPinUpdatedTime() {
        return this.paymentPinUpdatedTime;
    }

    public AccountServantStorage getServantStorage() {
        return this.servantStorage;
    }

    public MentalCardHandler getMentalCardHandler() {
        return this.mentalCardHandler;
    }

    public IntimacyHandler getIntimacyHandler() {
        return this.intimacyHandler;
    }

    public FriendHandler getFriendHandler() {
        return this.friendHandler;
    }

    public ExplorePointHandler getExplorePointHandler() {
        return this.explorePointHandler;
    }

    public Exploration getExploration() {
        return this.exploration;
    }

    public ChallengeHandler getChallengeHandler() {
        return this.challengeHandler;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public synchronized boolean addCharacterSlot(final Player player, int count) {
        final int characterSlots = this.loginAccountInfo.getCharacterSlots();
        if (characterSlots + LocalizingOptionConfig.DEFAULT_CHARACTER_SLOT >= LocalizingOptionConfig.CHARACTER_SLOT_LIMIT) {
            return false;
        }
        if (characterSlots + count + LocalizingOptionConfig.DEFAULT_CHARACTER_SLOT > LocalizingOptionConfig.CHARACTER_SLOT_LIMIT) {
            count = LocalizingOptionConfig.CHARACTER_SLOT_LIMIT - LocalizingOptionConfig.DEFAULT_CHARACTER_SLOT - characterSlots;
        }
        this.loginAccountInfo.addCharacterSlots(count);
        player.sendPacket(new SMVaryCharacterSlotCount(count));
        MainServer.getRmi().updateCharacterSlots(this.accountId, count);
        return true;
    }

    public EAccessLevel getAccessLevel() {
        return this.getLoginAccountInfo().getAccessLevel();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("_id", this.accountId);
        builder.append("lastLogout", this.lastLogout);
        builder.append("lastLogin", this.lastLogin);
        builder.append("firstLogin", this.firstLogin);
        builder.append("playedTime", this.getPlayedTime());
        builder.append("familyRewardCoolTime", this.familyRewardCoolTime);
        builder.append("guildCoolTime", this.guildCoolTime);
        builder.append("matingAuctionCoolTime", this.matingAuctionCoolTime);
        builder.append("buyServantAuctionCoolTime", this.buyServantAuctionCoolTime);
        builder.append("family", this.familyName);
        builder.append("comment", this.comment);
        builder.append("creationCharacterCount", this.creationCharacterCount);
        builder.append("activityPoints", this.activityPoints);
        builder.append("chatChannelId", this.chatChannelId);
        builder.append("userBasicCacheCount", this.userBasicCacheCount);
        builder.append("paymentPin", this.paymentPin);
        builder.append("paymentPinUpdatedTime", this.paymentPinUpdatedTime);
        builder.append("exploration", this.exploration.toDBObject());
        builder.append("mentalCard", this.mentalCardHandler.toDBObject());
        builder.append("intimacy", this.intimacyHandler.toDBObject());
        builder.append("titles", this.titleHandler.toDBObject());
        builder.append("explorePoint", this.explorePointHandler.toDBObject());
        builder.append("challenge", this.challengeHandler.toDBObject());
        builder.append("friendInfo", this.friendHandler.toDBObject());
        builder.append("houseStorage", this.houseStorage.toDBObject());
        builder.append("accountBag", this.getAccountBag().toDBObject());
        builder.append("alchemyRecordStorage", this.alchemyRecordStorage.toDBObject());
        builder.append("socialActionStorage", this.socialActionStorage.toDBObject());
        builder.append("encyclopediaStorage", this.encyclopediaStorage.toDBObject());
        builder.append("eventStorage", this.eventStorage.toDBObject());
        builder.append("chargeUserStorage", this.chargeUserStorage.toDBObject());
        final BasicDBList cashByCountDB = this.productByCount.values().stream().filter(cashProductBuyCount -> cashProductBuyCount.getCashItemT().getPurchaseSubject().isAccount()).map(CashProductBuyCount::toDBObject).collect(Collectors.toCollection(BasicDBList::new));
        builder.append("cashByCount", cashByCountDB);
        builder.append("boardGameData", this.boardGameData.toDBObject());
        builder.append("dyeStorage", this.dyeStorage.toDBObject());
        final BasicDBList progressQuestListDB = new BasicDBList();
        progressQuestListDB.addAll(this.player.getPlayerQuestHandler().getProgressQuestList().stream().filter(progressQuest -> progressQuest.getTemplate().isUserBaseQuest()).map(Quest::toDBObject).collect(Collectors.toList()));
        builder.append("progressUserBasicQuests", progressQuestListDB);
        final BasicDBList clearedQuestListDB = new BasicDBList();
        clearedQuestListDB.addAll(this.player.getPlayerQuestHandler().getClearedQuestList().stream().filter(clearedQuest -> clearedQuest.getTemplate().isUserBaseQuest()).map(ClearedQuest::toDBObject).collect(Collectors.toList()));
        builder.append("clearedUserBasicQuests", clearedQuestListDB);
        return builder.get();
    }

    public String getSaveGameOptions() {
        return this.saveGameOptions;
    }

    public void setSaveGameOptions(final String saveGameOptions) {
        this.saveGameOptions = saveGameOptions;
    }

    public String getUiData() {
        return this.uiData;
    }

    public void setUiData(final String uiData) {
        this.uiData = uiData;
    }

    public Macros[] getMacroses() {
        return this.macroses;
    }

    public void setMacroses(final Macros[] macroses) {
        this.macroses = macroses;
    }
}
