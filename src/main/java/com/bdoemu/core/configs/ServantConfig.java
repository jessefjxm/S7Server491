package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/servant.properties")
public class ServantConfig {
    @ConfigComments(comment = {"Servant mating time.", "Default: 8640000"})
    @ConfigProperty(name = "mating_time", value = "8640000")
    public static int MATING_TIME;
    @ConfigComments(comment = {"Servant mating registration time.", "Default: 21600000"})
    @ConfigProperty(name = "mating_regist_time", value = "21600000")
    public static int MATING_REGIST_TIME;
    @ConfigComments(comment = {"Servant taming rate.", "Default: 300000"})
    @ConfigProperty(name = "taming_rate", value = "300000")
    public static int TAMING_RATE;
    @ConfigComments(comment = {"Servant taming value.", "Default: 200000"})
    @ConfigProperty(name = "taming_value", value = "200000")
    public static int TAMING_VALUE;
    @ConfigComments(comment = {"Servant taming item key.", "Default: 54003"})
    @ConfigProperty(name = "taming_item_key", value = "54003")
    public static int TAMING_ITEM_KEY;
    @ConfigComments(comment = {"Elephant servant taming rate.", "Default: 50000"})
    @ConfigProperty(name = "elephant_taming_rate", value = "50000")
    public static int ELEPHANT_TAMING_RATE;
    @ConfigComments(comment = {"Elephant servant fail taming rate.", "Default: 150000"})
    @ConfigProperty(name = "elephant_taming_fail_rate", value = "150000")
    public static int ELEPHANT_TAMING_FAIL_RATE;
    @ConfigComments(comment = {"Elephant servant taming value.", "Default: 100000"})
    @ConfigProperty(name = "elephant_taming_value", value = "100000")
    public static int ELEPHANT_TAMING_VALUE;
    @ConfigComments(comment = {"Elephant servant taming item key.", "Default: 54019"})
    @ConfigProperty(name = "elephant_taming_item_key", value = "54019")
    public static int ELEPHANT_TAMING_ITEM_KEY;
    @ConfigComments(comment = {"Market registration time.", "Default: 0"})
    @ConfigProperty(name = "market_registration_time", value = "0")
    public static int MARKET_REGISTRATION_TIME;
    @ConfigComments(comment = {"Vehicle seal cost.", "Default: 1000"})
    @ConfigProperty(name = "vehicle_seal_cost", value = "1000")
    public static int VEHICLE_SEAL_COST;
    @ConfigComments(comment = {"Vehicle recovery cost.", "Default: 15"})
    @ConfigProperty(name = "vehicle_recovery_cost", value = "15")
    public static int VEHICLE_RECOVERY_COST;
    @ConfigComments(comment = {"Vehicle mating MP cost.", "Default: 1000000"})
    @ConfigProperty(name = "vehicle_mating_mp", value = "1000000")
    public static int VEHICLE_MATING_MP;
    @ConfigComments(comment = {"Vehicle grade value.", "Default: 13"})
    @ConfigProperty(name = "vehicle_grade_value", value = "13")
    public static int VEHICLE_GRADE_VALUE;
    @ConfigComments(comment = {"Vehicle sell cost.", "Default: 20"})
    @ConfigProperty(name = "vehicle_sell_cost", value = "20")
    public static int VEHICLE_SELL_COST;
    @ConfigComments(comment = {"Vehicle emission experience.", "Default: 150"})
    @ConfigProperty(name = "vehicle_emission_experience", value = "150")
    public static int VEHICLE_EMISSION_EXPERIENCE;
    @ConfigComments(comment = {"Vehicle revive cost.", "Default: 2000"})
    @ConfigProperty(name = "vehicle_revive_cost", value = "2000")
    public static int VEHICLE_REVIVE_COST;
    @ConfigComments(comment = {"Stable slot limit.", "Default: 3"})
    @ConfigProperty(name = "stable_slot_limit", value = "3")
    public static int STABLE_SLOT_LIMIT;
    @ConfigComments(comment = {"Vehicle level up experience.", "Default: 10"})
    @ConfigProperty(name = "vehicle_lvlup_lifeexperience", value = "10")
    public static int VEHICLE_LVLUP_LIFE_EXPERIENCE;
    @ConfigComments(comment = {"Taming life experience.", "Default: 4500"})
    @ConfigProperty(name = "taming_life_experience", value = "4500")
    public static int TAMING_LIFE_EXPERIENCE;
    @ConfigComments(comment = {"Discount percent for servant mating place.", "Default: 700000"})
    @ConfigProperty(name = "discount_percent_for_servant_mating_place", value = "700000")
    public static int DISCOUNT_PERCENT_FOR_SERVANT_MATING_PLACE;
    @ConfigComments(comment = {"Register delay from mating.", "Default: 21600"})
    @ConfigProperty(name = "register_delay_from_mating", value = "21600")
    public static int REGISTER_DELAY_FROM_MATING;
    @ConfigComments(comment = {"Register delay from market.", "Default: 1"})
    @ConfigProperty(name = "register_delay_from_market", value = "1")
    public static int REGISTER_DELAY_FROM_MARKET;
    @ConfigComments(comment = {"Discount percent for imprint servant.", "Default: 300000"})
    @ConfigProperty(name = "discount_percent_for_servant_imprint", value = "300000")
    public static int DISCOUNT_PERCENT_FOR_SERVANT_IMPRINT;
    @ConfigComments(comment = {"Discount percent for imprint servant.", "Default: 1000"})
    @ConfigProperty(name = "revive_price_for_servant_imprint", value = "1000")
    public static int REVIVE_PRICE_FOR_SERVANT_IMPRINT;
    @ConfigComments(comment = {"Vehicle self mating price.", "Default: 35000"})
    @ConfigProperty(name = "vehicle_self_mating_price", value = "35000")
    public static int VEHICLE_SELF_MATING_PRICE;
    @ConfigComments(comment = {"Vehicle add exp by training.", "Default: 1.500000"})
    @ConfigProperty(name = "vehicle_add_exp_by_training", value = "1.500000")
    public static float VEHICLE_ADD_EXP_BY_TRAINING;
    @ConfigComments(comment = {"Vehicle add taming by training.", "Default: 3000"})
    @ConfigProperty(name = "vehicle_add_taming_by_training", value = "3000")
    public static int VEHICLE_ADD_TAMING_BY_TRAINING;
    @ConfigComments(comment = {"Vehicle seal distance.", "Default: 1500"})
    @ConfigProperty(name = "vehicle_seal_distance", value = "1500")
    public static int VEHICLE_SEAL_DISTANCE;
    @ConfigComments(comment = {"Vehicle decrease cost by mating count.", "Default: 250000"})
    @ConfigProperty(name = "vehicle_decrease_cost_by_mating_count", value = "250000")
    public static int VEHICLE_DECREASE_COST_BY_MATING_COUNT;
    @ConfigComments(comment = {"Vehicle decrease cost by mating clear.", "Default: 150000"})
    @ConfigProperty(name = "vehicle_decrease_cost_by_mating_clear", value = "150000")
    public static int VEHICLE_DECREASE_COST_BY_MATING_CLEAR;
    @ConfigComments(comment = {"Vehicle decrease cost by dead clear.", "Default: 50000"})
    @ConfigProperty(name = "vehicle_decrease_cost_by_dead_clear", value = "50000")
    public static int VEHICLE_DECREASE_COST_BY_DEAD_CLEAR;
    @ConfigComments(comment = {"Vehicle learn skill rate.", "Default: 1000000"})
    @ConfigProperty(name = "vehicle_learn_skill_rate", value = "1000000")
    public static int VEHICLE_LEARN_SKILL_RATE;
    @ConfigComments(comment = {"Vehicle learn skill by level.", "Default: 50000"})
    @ConfigProperty(name = "vehicle_learn_skill_by_level", value = "50000")
    public static int VEHICLE_LEARN_SKILL_BY_LEVEL;
    @ConfigComments(comment = {"Vehicle skill change value.", "Default: 250000"})
    @ConfigProperty(name = "vehicle_skill_change_value", value = "250000")
    public static int VEHICLE_SKILL_CHANGE_VALUE;
    @ConfigComments(comment = {"Vehicle learn skill add rate from impirit.", "Default: 100000"})
    @ConfigProperty(name = "vehicle_learn_skill_add_rate_from_impirit", value = "100000")
    public static int VEHICLE_LEARN_SKILL_ADD_RATE_FROM_IMPIRIT;
    @ConfigComments(comment = {"Vehicle min price.", "Default: 500000"})
    @ConfigProperty(name = "vehicle_min_price", value = "500000")
    public static int VEHICLE_MIN_PRICE;
    @ConfigComments(comment = {"Vehicle max price.", "Default: 150000"})
    @ConfigProperty(name = "vehicle_max_price", value = "150000")
    public static int VEHICLE_MAX_PRICE;
    @ConfigComments(comment = {"Vehicle change form with skill rate.", "Default: 300000"})
    @ConfigProperty(name = "vehicle_change_form_with_skill_rate", value = "300000")
    public static int VEHICLE_CHANGE_FORM_WITH_SKILL_RATE;
    @ConfigComments(comment = {"Vehicle change form with skill vary experience.", "Default: 200000"})
    @ConfigProperty(name = "vehicle_change_form_with_skill_vary_experience", value = "200000")
    public static int VEHICLE_CHANGE_FORM_WITH_SKILL_VARY_EXPERIENCE;
    @ConfigComments(comment = {"Vehicle register price rate.", "Default: 300000"})
    @ConfigProperty(name = "vehicle_register_price_rate", value = "300000")
    public static int VEHICLE_REGISTER_PRICE_RATE;
    @ConfigComments(comment = {"Skill experience training time.", "Default: 3600000"})
    @ConfigProperty(name = "skill_exp_training_time", value = "3600000")
    public static int SKILL_EXP_TRAINING_TIME;
    @ConfigComments(comment = {"Exchange elephant item.", "Default: 9916"})
    @ConfigProperty(name = "exchange_elephant_item", value = "9916")
    public static int EXCHANGE_ELEPHANT_ITEM;
    @ConfigComments(comment = {"Revive cost.", "Default: 10000,2000,4000,7000,10000,15000,20000,25000,35000"})
    @ConfigProperty(name = "revive_cost", value = "10000,2000,4000,7000,10000,15000,20000,25000,35000")
    public static int[] REVIVE_COST;
    @ConfigComments(comment = {"Downgrade dead count.", "Default: 5000"})
    @ConfigProperty(name = "downgrade_from_dead_count", value = "5000")
    public static int DOWNGRADE_FROM_DEAD_COUNT;
    @ConfigComments(comment = {"Unseal delay time.", "Default: 5000"})
    @ConfigProperty(name = "unseal_delay_time", value = "5000")
    public static int UNSEAL_DELAY_TIME;
    @ConfigComments(comment = {"Success skill exp.", "Default: 10"})
    @ConfigProperty(name = "success_skill_exp", value = "10")
    public static int SUCCESS_SKILL_EXP;
    @ConfigComments(comment = {"Fail skill exp.", "Default: 100"})
    @ConfigProperty(name = "fail_skill_exp", value = "100")
    public static int FAIL_SKILL_EXP;
    @ConfigComments(comment = {"Hope rate 10000 = 1%.", "Default: 10000"})
    @ConfigProperty(name = "hope_rate", value = "10000")
    public static int HOPE_RATE;
    @ConfigComments(comment = {"Mating by level rate 9.", "Default: 9"})
    @ConfigProperty(name = "mating_by_level_rate", value = "9")
    public static int MATING_BY_LEVEL_RATE;
    @ConfigComments(comment = {"Mating by dead count rate 5000 = 0.5%.", "Default: 5000"})
    @ConfigProperty(name = "mating_by_dead_count_rate", value = "5000")
    public static int MATING_BY_DEAD_COUNT_RATE;
}
