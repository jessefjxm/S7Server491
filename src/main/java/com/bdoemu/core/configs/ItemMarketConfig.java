package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/itemmarket.properties")
public class ItemMarketConfig {
    @ConfigComments(comment = {"Fee for register item on market", "Default: 10"})
    @ConfigProperty(name = "ItemMarketRegisterFeePrice", value = "10")
    public static long REGISTER_FEE_PRICE;
    @ConfigComments(comment = {"Fee for register item on market", "Default: 900000 (15 min)"})
    @ConfigProperty(name = "ItemMarketRegisterWaitingTick", value = "900000")
    public static int REGISTER_WAITING_TICK;
    @ConfigComments(comment = {"Fee for register party item on market", "Default: 120000 (2 min)"})
    @ConfigProperty(name = "PartyItemMarketRegisterWaitingTick", value = "120000")
    public static int REGISTER_PARTY_ITEM_WAITING_TICK;
    @ConfigComments(comment = {"Time for restricted items for unregister", "Default: 300000 (5 min)"})
    @ConfigProperty(name = "ItemMarketUnregisterRestrictedTick", value = "300000")
    public static long UNREGISTER_RESTRICTED_TICK;
    @ConfigComments(comment = {"Limit of registered items on auction for each user", "Default: 30"})
    @ConfigProperty(name = "ItemMarketRegisterCountPerUser", value = "30")
    public static int REGISTER_COUNT_PER_USER;
    @ConfigComments(comment = {"Enable item market registration", "Default: true"})
    @ConfigProperty(name = "ItemMarketRegistrationEnabled", value = "true")
    public static boolean ITEM_MARKET_REGISTRATION_ENABLED;
    @ConfigComments(comment = {"Enable item market buy", "Default: true"})
    @ConfigProperty(name = "ItemMarketBuyEnabled", value = "true")
    public static boolean ITEM_MARKET_BUY_ENABLED;
}
