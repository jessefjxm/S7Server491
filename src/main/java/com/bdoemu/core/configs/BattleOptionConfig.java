package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/battleoption.properties")
public class BattleOptionConfig {
    @ConfigProperty(name = "AttackDamagePvPScaling", value = "150")
    public static int ATTACK_DAMAGE_PVP_SCALE;
    @ConfigComments(comment = {"Multiplier percentage of critical damage dealt with character attacks", "Default: 1500000"})
    @ConfigProperty(name = "CriticalDamageRate", value = "1500000")
    public static int CRITICAL_DAMAGE_RATE;
    @ConfigProperty(name = "AttackHitRate", value = "0.033f")
    public static float ATTACK_HIT_RATE;
    @ConfigProperty(name = "BlockGaugeRate", value = "0.3f")
    public static float BLOCK_GAUGE_RATE;
    @ConfigProperty(name = "GaugeRegenMultiplier", value = "1.3f")
    public static float GAUGE_REGEN_MULTIPLIER;
    @ConfigComments(comment = {"Multiplier percentage of damage dealt with back attacks", "Default: 1500000"})
    @ConfigProperty(name = "BackAttackDamageRate", value = "1500000")
    public static int BACK_ATTACK_DAMAGE_RATE;
    @ConfigComments(comment = {"Multiplier percentage of damage dealt with counter attacks", "Default: 2000000"})
    @ConfigProperty(name = "CounterAttackDamageRate", value = "2000000")
    public static int COUNTER_ATTACK_DAMAGE_RATE;
    @ConfigComments(comment = {"Multiplier percentage of damage dealt with down attacks", "Default: 1500000"})
    @ConfigProperty(name = "DownAttackDamageRate", value = "1500000")
    public static int DOWN_ATTACK_DAMAGE_RATE;
    @ConfigComments(comment = {"Multiplier percentage of damage dealt with air attacks", "Default: 2500000"})
    @ConfigProperty(name = "AirAttackDamageRate", value = "2500000")
    public static int AIR_ATTACK_DAMAGE_RATE;
    @ConfigComments(comment = {"Multiplier percentage of damage dealt with speed attacks", "Default: 1500000"})
    @ConfigProperty(name = "SpeedAttackDamageRate", value = "1500000")
    public static int SPEED_ATTACK_DAMAGE_RATE;
    @ConfigComments(comment = {"Time limit to use a room at inn", "Default: 3600"})
    @ConfigProperty(name = "InnRentalTime", value = "3600")
    public static int INN_RENTAL_TIME;
    @ConfigComments(comment = {"Time limit to use a room service at inn", "Default: 1"})
    @ConfigProperty(name = "InnRoomServiceTime", value = "1")
    public static int INN_ROOM_SERVICE_TIME;
    @ConfigComments(comment = {"Decreased drop chances for targets above or below 8 level difference", "Default: 8Lv:60%, 7Lv:50%, 6Lv:40%, 5Lv:30%, 4Lv:20%, 3Lv:10%, 2Lv:5%, 1Lv:1%"})
    @ConfigProperty(name = "DecreaseDropRate", value = "600000, 500000, 400000, 300000, 200000, 100000, 50000, 10000")
    public static int[] DECREASE_DROP_RATE;
    @ConfigComments(comment = {"Multiplier percentage of monster stats during night", "Default: 1300000"})
    @ConfigProperty(name = "MonsterPowerInNight", value = "1300000")
    public static int MONSTER_POWER_AT_NIGHT;
    @ConfigComments(comment = {"Multiplier percentage of monster exp at night", "Default: 1500000"})
    @ConfigProperty(name = "MonsterExperienceInNight", value = "1500000")
    public static int MONSTER_EXPERIENCE_AT_NIGHT;
    @ConfigComments(comment = {"Time to store character buffs", "Default: 10000"})
    @ConfigProperty(name = "SavingBuffTime", value = "10000")
    public static int SAVE_BUFF_TIME;
    @ConfigComments(comment = {"Life Exp Related", "Trade & Collect"})
    @ConfigProperty(name = "TradeDistanceVehicle", value = "1000")
    public static int TRADE_DISTANCE_IN_VEHICLE;
    @ConfigComments(comment = {"Life Exp Related", "Trade & Collect"})
    @ConfigProperty(name = "TradeSummaryInterval", value = "300000")
    public static int TRADE_SUMMARY_INTERVAL;
    @ConfigComments(comment = {"Life Exp Related", "Trade & Collect"})
    @ConfigProperty(name = "CollectSpawnPercentInServerBeginning", value = "300000")
    public static int COLLECT_SPAWN_PERCENT_AT_SERVER_START;
    @ConfigComments(comment = {"Life Exp Related", "Trade & Collect"})
    @ConfigProperty(name = "CollectAwakeyDelay", value = "180000")
    public static int COLLECT_AWAKE_DELAY;
    @ConfigComments(comment = {"Life Exp Related", "Trade & Collect"})
    @ConfigProperty(name = "CollcetSpawnPercentForDelay", value = "50000")
    public static int COLLECT_SPAWN_PERCENT_FOR_DELAY;
    @ConfigComments(comment = {"Item id needed for fixed revival", "Default: 5017"})
    @ConfigProperty(name = "RevivalProductNo", value = "5017")
    public static int FIXED_REVIVAL_ITEM_ID;
    @ConfigComments(comment = {"Time delay to reuse ingame escape/rescue option", "Default: 1800000"})
    @ConfigProperty(name = "RescueCoolTime", value = "1800000")
    public static int RESCUE_REUSE_DELAY;
    @ConfigComments(comment = {"Minimun level to enable PvP", "Default: 50"})
    @ConfigProperty(name = "PvPCondition", value = "50")
    public static int PVP_MIN_LEVEL;
    @ConfigComments(comment = {"Quest needed to get further exp to level 50", "Default: 677,1"})
    @ConfigProperty(name = "FinalLevelUpCondition", value = "677,1")
    public static String EXP_FINAL_LEVEL_CONDITION_QUEST;
    @ConfigComments(comment = {"Time to remove mails from mailbox", "Default: 864000"})
    @ConfigProperty(name = "MailRemainTime", value = "864000")
    public static int MAIL_REMOVAL_TIME;
    @ConfigComments(comment = {"PIN wrong type limit before block", "Default: 3"})
    @ConfigProperty(name = "AuthenticPasswordLimit", value = "3")
    public static int PIN_WRONG_CHANCE;
    @ConfigComments(comment = {"PIN minimum length", "Default: 6"})
    @ConfigProperty(name = "PasswordLengthMin", value = "6")
    public static int PIN_MIN_LENGTH;
    @ConfigComments(comment = {"PIN maximum length", "Default: 12"})
    @ConfigProperty(name = "PasswordLengthMax", value = "12")
    public static int PIN_MAX_LENGTH;
    @ConfigComments(comment = {"Awakening minimum level", "Default: 47"})
    @ConfigProperty(name = "AwakeningLevel", value = "47")
    public static int AWAKENING_MIN_LEVEL;
    @ConfigComments(comment = {"Re-Awakening minimum level", "Default: 49"})
    @ConfigProperty(name = "ResetAwakeningLevel", value = "49")
    public static int AWAKENING_RESET_MIN_LEVEL;
    @ConfigComments(comment = {"Awakening needed exp", "Default: 31000000"})
    @ConfigProperty(name = "ResetAwakeningNeedExp", value = "31000000")
    public static int AWAKENING_RESET_NEED_EXP;
    @ConfigComments(comment = {"Adrenalin  super skill effect minimum level", "Default: 35"})
    @ConfigProperty(name = "AdrenalinCondition", value = "35")
    public static int ADRENALIN_SUPER_SKILL_MIN_LEVEL;
    @ConfigComments(comment = {"Rate of exp need to trigger adrenalin super skill effect", "Default: 50000"})
    @ConfigProperty(name = "SuperSkillBaseRate", value = "50000")
    public static int ADRENALIN_SUPER_SKILL_BASE_RATE;
    @ConfigComments(comment = {"Time to remove farms/tents from world", "Default: 7"})
    @ConfigProperty(name = "ExpiredTentCheckDay", value = "7")
    public static int TENT_REMOVAL_TIME;
}
