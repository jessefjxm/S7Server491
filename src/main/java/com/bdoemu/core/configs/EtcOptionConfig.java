package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/etcoption.properties")
public class EtcOptionConfig {
    @ConfigComments(comment = {"House auction sale time.", "Default: 86400000"})
    @ConfigProperty(name = "HouseAuctionSaleTime", value = "86400000")
    public static int HOUSE_AUCTION_SALE_TIME;
    @ConfigComments(comment = {"Is calculate interior all points.", "Default: false"})
    @ConfigProperty(name = "isCalculateInteriorPointAll", value = "false")
    public static boolean IS_CALCULATE_INTERIOR_POINT_ALL;
    @ConfigComments(comment = {"Tutorial condition.", "Default: getLevel()<5"})
    @ConfigProperty(name = "TutorialCondition", value = "getLevel()<5")
    public static String TUTORIAL_CONDITION;
    @ConfigComments(comment = {"Trade experience per distance.", "Default: 200"})
    @ConfigProperty(name = "TradeExperiencePerDistance", value = "200")
    public static int TRADE_EXPERIENCE_PER_DISTANCE;
    @ConfigComments(comment = {"Delivery price per distance.", "Default: 100"})
    @ConfigProperty(name = "DeliveryPricePerDistance", value = "100")
    public static int DELIVERY_PRICE_PER_DISTANCE;
    @ConfigComments(comment = {"Delivery link node penalty.", "Default: 3000000"})
    @ConfigProperty(name = "DeliveryLinkNodePenalty", value = "3000000")
    public static int DELIVERY_LINK_NODE_PENALTY;
    @ConfigComments(comment = {"Delivery trade penalty.", "Default: 3"})
    @ConfigProperty(name = "DeliveryTradePenalty", value = "3")
    public static int DELIVERY_TRADE_PENALTY;
    @ConfigComments(comment = {"Delivery packing weight.", "Default: 500000"})
    @ConfigProperty(name = "DeliveryPackingWeight", value = "500000")
    public static int DELIVERY_PACKING_WEIGHT;
    @ConfigComments(comment = {"Trade distance vehicle.", "Default: 3000"})
    @ConfigProperty(name = "TradeDistanceVehicle", value = "3000")
    public static int TRADE_DISTANCE_VEHICLE;
    @ConfigComments(comment = {"Working base work time.", "Default: 600000"})
    @ConfigProperty(name = "WorkingBaseWorkTime", value = "600000")
    public static int WORKING_BASE_WORK_TIME;
    @ConfigComments(comment = {"Npc upgrade time.", "Default: 86400000"})
    @ConfigProperty(name = "NpcUpgradeTime", value = "86400000")
    public static int NPC_UPGRADE_TIME;
    @ConfigComments(comment = {"Npc house party time.", "Default: 600000"})
    @ConfigProperty(name = "NpcHousePartyTime", value = "600000")
    public static int NPC_HOUSE_PARTY_TIME;
    @ConfigComments(comment = {"Npc working exp.", "Default: 0"})
    @ConfigProperty(name = "NpcWorkingExp", value = "0")
    public static int NPC_WORKING_EXP;
    @ConfigComments(comment = {"Npc working time exp.", "Default: 0"})
    @ConfigProperty(name = "NpcWorkingTimeExp", value = "0")
    public static int NPC_WORKING_TIME_EXP;
    @ConfigComments(comment = {"Npc working time rate.", "Default: 0"})
    @ConfigProperty(name = "NpcWorkingTimeRate", value = "0")
    public static int NPC_WORKING_TIME_RATE;
    @ConfigComments(comment = {"Npc working harvesting exp.", "Default: 0"})
    @ConfigProperty(name = "NpcWorkingHarvestingExp", value = "0")
    public static int NPC_WORKING_HARVESTING_EXP;
    @ConfigComments(comment = {"Npc worker min max price precent.", "Default: 0"})
    @ConfigProperty(name = "NpcWorkerMinMaxPricePrecent", value = "0")
    public static int NPC_WORKER_MIN_MAX_PRICE_PRECENT;
    @ConfigComments(comment = {"Npc worker upgrade point price.", "Default: 300000"})
    @ConfigProperty(name = "NpcWorkerUpgradePointPrice", value = "300000")
    public static int NPC_WORKER_UPGRADE_POINT_PRICE;
    @ConfigComments(comment = {"Npc worker auction expire time.", "Default: 86400000"})
    @ConfigProperty(name = "NpcWorkerAuctionExpireTime", value = "86400000")
    public static int NPC_WORKER_AUCTION_EXPIRE_TIME;
    @ConfigComments(comment = {"Working trash exchange key list.", "Default: "})
    @ConfigProperty(name = "WorkingTrashExchangeKeyList", value = "")
    public static String WORKING_TRASH_EXCHANGE_KEY_LIST;
    @ConfigComments(comment = {"Npc worker skill change rate.", "Default: 500000"})
    @ConfigProperty(name = "NpcWorkerSkillChangeRate", value = "500000")
    public static int NPC_WORKER_SKILL_CHANGE_RATE;
    @ConfigComments(comment = {"Weaken item rate in siege.", "Default: 0"})
    @ConfigProperty(name = "WeakenItemRateInSiege", value = "0")
    public static int WEAKEN_ITEM_RATE_IN_SIEGE;
    @ConfigComments(comment = {"Detect player price.", "Default: 10000"})
    @ConfigProperty(name = "DetectPlayerPrice", value = "10000")
    public static int DETECT_PLAYER_PRICE;
    @ConfigComments(comment = {"Tendency from seal.", "Default: -2000"})
    @ConfigProperty(name = "TendencyFromSeal", value = "-2000")
    public static int TENDENCY_FROM_SEAL;
    @ConfigComments(comment = {"Guild tendency convert percent for explore.", "Default: 100000"})
    @ConfigProperty(name = "GuildTendencyConvertPercentForExplore", value = "100000")
    public static int GUILD_TENDENCY_CONVERT_PERCENT_FOR_EXPLORE;
    @ConfigComments(comment = {"Guild tendency convert percent for player value.", "Default: 50000"})
    @ConfigProperty(name = "GuildTendencyConvertPercentForPlayer", value = "50000")
    public static int GUILD_TENDENCY_CONVERT_PERCENT_FOR_PLAYER;
    @ConfigComments(comment = {"Territory trade authority auction sale time.", "Default: 86400000"})
    @ConfigProperty(name = "TerritoryTradeAuthorityAuctionSaleTime", value = "86400000")
    public static int TERRITORY_TRADE_AUTHORITY_AUCTION_SALE_TIME;
    @ConfigComments(comment = {"Add hit by knowledge.", "Default: 0"})
    @ConfigProperty(name = "AddHitByKnowledge", value = "0")
    public static int ADD_HIT_BY_KNOWLEDGE;
    @ConfigComments(comment = {"Guard pvp rate.", "Default: 0"})
    @ConfigProperty(name = "GuardPvpRate", value = "0")
    public static int GUARD_PVP_RATE;
    @ConfigComments(comment = {"Guard rate.", "Default: 0"})
    @ConfigProperty(name = "GuardRate", value = "0")
    public static int GUARD_RATE;
    @ConfigComments(comment = {"Normal busy state percent.", "Default: 120000"})
    @ConfigProperty(name = "NormalBusyStatePercent", value = "120000")
    public static int NORMAL_BUSY_STATE_PERCENT;
    @ConfigComments(comment = {"Very busy state percent.", "Default: 300000"})
    @ConfigProperty(name = "VeryBusyStatePercent", value = "300000")
    public static int VERY_BUSY_STATE_PERCENT;
    @ConfigComments(comment = {"Channel move waiting time.", "Default: 900000"})
    @ConfigProperty(name = "ChannelMoveWaitingTime", value = "900000")
    public static int CHANNEL_MOVE_WAITING_TIME;
    @ConfigComments(comment = {"Channel move playerable user count.", "Default: 10"})
    @ConfigProperty(name = "ChannelMovePlayerableUserCount", value = "10")
    public static int CHANNEL_MOVE_PLAYERABLE_USER_COUNT;
    @ConfigComments(comment = {"Collect consume wp.", "Default: 1"})
    @ConfigProperty(name = "CollectConsumeWp", value = "1")
    public static int COLLECT_CONSUME_WP;
    @ConfigComments(comment = {"Quote view consume wp.", "Default: 1500000"})
    @ConfigProperty(name = "QuoteViewConsumeWp", value = "1500000")
    public static int QUOTE_VIEW_CONSUME_WP;
    @ConfigComments(comment = {"Chat block by user point.", "Default: 0"})
    @ConfigProperty(name = "ChatBlockByUserPoint", value = "0")
    public static int CHAT_BLOCK_BY_USER_POINT;
    @ConfigComments(comment = {"Chat block by user second.", "Default: 0"})
    @ConfigProperty(name = "ChatBlockByUserSecond", value = "0")
    public static int CHAT_BLOCK_BY_USER_SECOND;
    @ConfigComments(comment = {"Normal trade drop rate.", "Default: 0"})
    @ConfigProperty(name = "NormalTradeDropRate", value = "0")
    public static int NORMAL_TRADE_DROP_RATE;
    @ConfigComments(comment = {"Smuggling trade drop rate.", "Default: 0"})
    @ConfigProperty(name = "SmugglingTradeDropRate", value = "0")
    public static int SMUGGLING_TRADE_DROP_RATE;
    @ConfigComments(comment = {"Territory trade drop rate.", "Default: 0"})
    @ConfigProperty(name = "TerritoryTradeDropRate", value = "0")
    public static int TERRITORY_TRADE_DROP_RATE;
    @ConfigComments(comment = {"Territory trade authority bidder count.", "Default: 0"})
    @ConfigProperty(name = "TerritoryTradeAuthorityBidderCount", value = "0")
    public static int TERRITORY_TRADE_AUTHORITY_BIDDER_COUNT;
    @ConfigComments(comment = {"World chat condition.", "Default: 99"})
    @ConfigProperty(name = "WorldChatCondition", value = "99")
    public static int WORLD_CHAT_CONDITION;
    @ConfigComments(comment = {"Max endurance increase rate.", "Default: 0"})
    @ConfigProperty(name = "MaxEnduranceIncreaseRate", value = "0")
    public static int MAX_ENDURANCE_INCREASE_RATE;
    @ConfigComments(comment = {"Trade bonus exp percent.", "Default: 200"})
    @ConfigProperty(name = "TradeBonusExpPercent", value = "200")
    public static int TRADE_BONUS_EXP_PERCENT;
    @ConfigComments(comment = {"Trade bonus gold percent.", "Default: 50"})
    @ConfigProperty(name = "TradeBonusGoldPercent", value = "50")
    public static int TRADE_BONUS_GOLD_PERCENT;
    @ConfigComments(comment = {"Reduce repair price for seal.", "Default: 670000"})
    @ConfigProperty(name = "ReduceRepairPriceForSeal", value = "670000")
    public static int REDUCE_REPAIR_PRICE_FOR_SEAL;
    @ConfigComments(comment = {"Endurance decrease percent equip item for seal.", "Default: 500000"})
    @ConfigProperty(name = "EnduranceDecreasePercentEquipItemForSeal", value = "500000")
    public static int ENDURANCE_DECREASE_PERCENT_EQUIP_ITEM_FOR_SEAL;
    @ConfigComments(comment = {"Worker shop consume wp.", "Default: 5"})
    @ConfigProperty(name = "WorkerShopConsumeWp", value = "5")
    public static int WORKER_SHOP_CONSUME_WP;
    @ConfigComments(comment = {"World item market consume wp.", "Default: 1"})
    @ConfigProperty(name = "WorldItemMarketConsumeWp", value = "1")
    public static int WORLD_ITEM_MARKET_CONSUME_WP;
    @ConfigComments(comment = {"Trade game bonus percent.", "Default: 50000"})
    @ConfigProperty(name = "TradeGameBonusPercent", value = "50000")
    public static int TRADE_GAME_BONUS_PERCENT;
    @ConfigComments(comment = {"Trade game consume wp.", "Default: 5"})
    @ConfigProperty(name = "TradeGameConsumeWp", value = "5")
    public static int TRADE_GAME_CONSUME_WP;
    @ConfigComments(comment = {"Trade game try count.", "Default: 3"})
    @ConfigProperty(name = "TradeGameTryCount", value = "3")
    public static int TRADE_GAME_TRY_COUNT;
    @ConfigComments(comment = {"Trade game goal interval.", "Default: 3"})
    @ConfigProperty(name = "TradeGameGoalInterval", value = "3")
    public static int TRADE_GAME_GOAL_INTERVAL;
    @ConfigComments(comment = {"Other region trade graph show wp.", "Default: 1"})
    @ConfigProperty(name = "OtherRegionTradeGraphShowWp", value = "1")
    public static int OTHER_REGION_TRADE_GRAPH_SHOW_WP;
    @ConfigComments(comment = {"Territory supply max sell rate.", "Default: 2500000"})
    @ConfigProperty(name = "TerritorySupplyMaxSellRate", value = "2500000")
    public static int TERRITORY_SUPPLY_MAX_SELL_RATE;
    @ConfigComments(comment = {"Trade supply max sell rate.", "Default: 2500000"})
    @ConfigProperty(name = "TradeSupplyMaxSellRate", value = "2500000")
    public static int TRADE_SUPPLY_MAX_SELL_RATE;
    @ConfigComments(comment = {"Dyeing combine item.", "Default: 17030"})
    @ConfigProperty(name = "DyeingCombineItem", value = "17030")
    public static int DYEING_COMBINE_ITEM;
    @ConfigComments(comment = {"Day random shop consume wp.", "Default: 10"})
    @ConfigProperty(name = "DayRandomShopConsumeWp", value = "10")
    public static int DAY_RANDOM_SHOP_CONSUME_WP;
    @ConfigComments(comment = {"Random shop consume wp.", "Default: 50"})
    @ConfigProperty(name = "RandomShopConsumeWp", value = "50")
    public static int RANDOM_SHOP_CONSUME_WP;
    @ConfigComments(comment = {"Free revival level.", "Default: 10"})
    @ConfigProperty(name = "FreeRevivalLevel", value = "10")
    public static int FREE_REVIVAL_LEVEL;
    @ConfigComments(comment = {"Fishing add rate.", "Default: 0.5"})
    @ConfigProperty(name = "FishingAddRate", value = "0.5")
    public static float FISHING_ADD_RATE;
    @ConfigComments(comment = {"Alchemy residue item key.", "Default: 9725"})
    @ConfigProperty(name = "AlchemyResidueItemKey", value = "9725")
    public static float ALCHEMY_RESIDUE_ITEM_KEY;
    @ConfigComments(comment = {"Alchemy residue error count.", "Default: 3"})
    @ConfigProperty(name = "AlchemyResidueErrorCount", value = "3")
    public static int ALCHEMY_RESIDUE_ERROR_COUNT;
    @ConfigComments(comment = {"Cook residue item key.", "Default: 9724"})
    @ConfigProperty(name = "CookResidueItemKey", value = "9724")
    public static int COOK_RESIDUE_ITEM_KEY;
    @ConfigComments(comment = {"Cook residue error count.", "Default: 3"})
    @ConfigProperty(name = "CookResidueErrorCount", value = "3")
    public static int COOK_RESIDUE_ERROR_COUNT;
    @ConfigComments(comment = {"Node increase item drop percent.", "Default: 10000"})
    @ConfigProperty(name = "NodeIncreaseItemDropPercent", value = "10000")
    public static int NODE_INCREASE_ITEM_DROP_PERCENT;
    @ConfigComments(comment = {"Supply shop interval.", "Default: 3"})
    @ConfigProperty(name = "SupplyShopInterval", value = "3")
    public static int SUPPLY_SHOP_INTERVAL;
    @ConfigComments(comment = {"Supply shop refresh check time.", "Default: 0"})
    @ConfigProperty(name = "SupplyShopRefreshCheckTime", value = "0")
    public static int SUPPLY_SHOP_REFRESH_CHECK_TIME;
    @ConfigComments(comment = {"Supply shop refresh percent.", "Default: 50"})
    @ConfigProperty(name = "SupplyShopRefreshPercent", value = "50")
    public static int SUPPLY_SHOP_REFRESH_PERCENT;
    @ConfigComments(comment = {"Territory supply shop interval.", "Default: 4"})
    @ConfigProperty(name = "TerritorySupplyShopInterval", value = "4")
    public static int TERRITORY_SUPPLY_SHOP_INTERVAL;
    @ConfigComments(comment = {"Alchemy stone upgrade rate 1.", "Default: 3000,1000,750,250,65,13,2"})
    @ConfigProperty(name = "AlchemyStoneUpGradeRate_1", value = "3000,1000,750,250,65,13,2")
    public static int[] ALCHEMY_STONE_UPGRADE_RATE_1;
    @ConfigComments(comment = {"Pruning.", "Default: 57601"})
    @ConfigProperty(name = "Pruning", value = "57601")
    public static int PRUNING;
    @ConfigComments(comment = {"Catchbug.", "Default: 57602"})
    @ConfigProperty(name = "Catchbug", value = "57602")
    public static int CATCHBUG;
    @ConfigComments(comment = {"Pruning rate.", "Default : 10000"})
    @ConfigProperty(name = "PruningRate", value = "10000")
    public static int PRUNING_RATE;
    @ConfigComments(comment = {"Catchbug rate.", "Default: 10000"})
    @ConfigProperty(name = "CatchbugRate", value = "10000")
    public static int CATCHBUG_RATE;
    @ConfigComments(comment = {"Localwar min PV.", "Default: 65"})
    @ConfigProperty(name = "LocalwarMinPV", value = "65")
    public static int LOCALWAR_MIN_PV;
    @ConfigComments(comment = {"Min random price.", "Default: 1000000"})
    @ConfigProperty(name = "MinRandomPrice", value = "1000000")
    public static int MIN_RANDOM_PRICE;
    @ConfigComments(comment = {"Max random price.", "Default: 3000000"})
    @ConfigProperty(name = "MaxRandomPrice", value = "3000000")
    public static int MAX_RANDOM_PRICE;
    @ConfigComments(comment = {"World chat item key.", "Default: 17645"})
    @ConfigProperty(name = "WorldChatItemKey", value = "17645")
    public static int WORLD_CHAT_ITEM_KEY;
    @ConfigComments(comment = {"Desert storm shielder character key.", "Default: 910"})
    @ConfigProperty(name = "DesertStormShielderCharacterKey", value = "910")
    public static int DESERT_STORM_SHIELDER_CHARACTER_KEY;
    @ConfigComments(comment = {"Drop event condition.", "Default: checkPeriod(2015/10/30-16:00, 2015/11/1-23:59);"})
    @ConfigProperty(name = "DropEventCondition", value = "checkPeriod(2015/10/30-16:00, 2015/11/1-23:59);")
    public static String DROP_EVENT_CONDITION;
    @ConfigComments(comment = {"Drop event main group key.", "Default: 20999"})
    @ConfigProperty(name = "DropEventMainGroupKey", value = "20999")
    public static int DROP_EVENT_MAIN_GROUP_KEY;
    @ConfigComments(comment = {"Guild house craft max working count.", "Default: 200"})
    @ConfigProperty(name = "GuildHouseCraftMaxWorkingCount", value = "200")
    public static int GUILD_HOUSE_CRAFT_MAX_WORKING_COUNT;
    @ConfigComments(comment = {"Horse race item key.", "Default: 1001"})
    @ConfigProperty(name = "HorseraceItemKey", value = "1001")
    public static int HORSE_RACE_ITEM_KEY;
    @ConfigComments(comment = {"Prevent downgrade item key.", "Default: 16080"})
    @ConfigProperty(name = "PreventDowngradeItemKey", value = "16080")
    public static int PREVENT_DOWNGRADE_ITEM_KEY;
    @ConfigComments(comment = {"Black spirit training max exp percent.", "Default: 800000"})
    @ConfigProperty(name = "BlackSpritTrainingMaxExpPercent", value = "800000")
    public static int BLACK_SPRIT_TRAINING_MAX_EXP_PERCENT;
    @ConfigComments(comment = {"Resist desert.", "Default: 16012"})
    @ConfigProperty(name = "ResistDesert", value = "16012")
    public static int RESIST_DESERT;
    @ConfigComments(comment = {"Added pet collect drop rate.", "Default: -700000"})
    @ConfigProperty(name = "AddedPetCollectDropRate", value = "-700000")
    public static int ADDED_PET_COLLECT_DROP_RATE;
    @ConfigComments(comment = {"Commerce type for trade game.", "Default: 7"})
    @ConfigProperty(name = "CommerceTypeForTradeGame", value = "7")
    public static int COMMERCE_TYPE_FOR_TRADE_GAME;
    @ConfigComments(comment = {"Commerce type for trade game percent.", "Default: 2000000"})
    @ConfigProperty(name = "CommerceTypeForTradeGamePercent", value = "2000000")
    public static int COMMERCE_TYPE_FOR_TRADE_GAME_PERCENT;
    @ConfigComments(comment = {"Limited bid count for house auction.", "Default: 3"})
    @ConfigProperty(name = "LimitedBidCountForHouseAuction", value = "3")
    public static int LIMITED_BID_COUNT_FOR_HOUSE_AUCTION;
    @ConfigComments(comment = {"Manufacture cook validity time.", "Default: 20"})
    @ConfigProperty(name = "ManufactureCookValidityTime", value = "20")
    public static int MANUFACTURE_COOK_VALIDITY_TIME;
    @ConfigComments(comment = {"Manufacture cook add exp.", "Default: 20"})
    @ConfigProperty(name = "ManufactureCookAddExp", value = "20")
    public static int MANUFACTURE_COOK_ADD_EXP;
    @ConfigComments(comment = {"BaseWPAmount.", "Default: 30"})
    @ConfigProperty(name = "BaseWPAmount", value = "30")
    public static int BASE_WP_AMOUNT;
    @ConfigProperty(name = "PartyExpPenaltyMaxLevel", value = "50")
    public static int PARTY_EXP_PENALTY_MAX_LEVEL;
    @ConfigProperty(name = "PartyExpPenaltyLevelDiff", value = "6")
    public static int PARTY_EXP_PENALTY_LEVEL_DIFF;
    @ConfigProperty(name = "MaxEnchantLevel", value = "20")
    public static int MAX_ENCHANT_LEVEL;
    @ConfigProperty(name = "EnableEnchantCroneStone", value = "true")
    public static boolean ENABLE_ENCHANT_CRONE_STONE;
}
