package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/rate.properties")
public class RateConfig {
    @ConfigComments(comment = {"Monster drop rate:", "100%"})
    @ConfigProperty(name = "rate.monster.drop", value = "100")
    public static int MONSTER_DROP_RATE;
    @ConfigComments(comment = {"Fishing drop rate:", "100%"})
    @ConfigProperty(name = "rate.fishing.drop", value = "100")
    public static int FISHING_DROP_RATE;
    @ConfigComments(comment = {"Collection drop rate:", "100%"})
    @ConfigProperty(name = "rate.collection.drop", value = "100")
    public static int COLLECTION_DROP_RATE;
    @ConfigComments(comment = {"Steal drop rate:", "100%"})
    @ConfigProperty(name = "rate.steal.drop", value = "100")
    public static int STEAL_DROP_RATE;
    @ConfigComments(comment = {"Tendency Exp rate:", "100%"})
    @ConfigProperty(name = "rate.tendency.exp", value = "100")
    public static int TENDENCY_RATE_EXP;
    @ConfigComments(comment = {"Horse Exp rate:", "100%"})
    @ConfigProperty(name = "rate.horse.exp", value = "100")
    public static int HORSE_RATE_EXP;
    @ConfigComments(comment = {"Exp rate:", "100%"})
    @ConfigProperty(name = "rate.exp", value = "100")
    public static int RATE_EXP;
    @ConfigComments(comment = {"Skillexp rate:", "100%"})
    @ConfigProperty(name = "rate.skillexp", value = "100")
    public static int RATE_SKILL_EXP;
    @ConfigComments(comment = {"GuildSkillexp rate:", "100%"})
    @ConfigProperty(name = "rate.guild.skillexp", value = "100")
    public static int RATE_GUILD_SKILL_EXP;
    @ConfigComments(comment = {"Fitness exp rate:", "100%"})
    @ConfigProperty(name = "rate.fitness.exp", value = "100")
    public static int RATE_FITNESS_EXP;
    @ConfigComments(comment = {"Life exp rate:", "100%"})
    @ConfigProperty(name = "rate.life.exp", value = "100")
    public static int RATE_LIFE_EXP;
    @ConfigComments(comment = {"Explore exp rate:", "100%"})
    @ConfigProperty(name = "rate.explore.exp", value = "100")
    public static int RATE_EXPLORE_EXP;
    @ConfigComments(comment = {"Enchant armor rates", "Default: 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 357142, 256410, 172413, 117647, 76923, 62500, 50000, 40000, 28571, 20000, 20000, 20000, 20000, 20000, 20000"})
    @ConfigProperty(name = "rate.enchant.armor.level", value = "1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 357142, 256410, 172413, 117647, 76923, 62500, 50000, 40000, 28571, 20000, 20000, 20000, 20000, 20000, 20000")
    public static int[] ENCHANT_ARMOR_RATES;
    @ConfigComments(comment = {"Enchant weapon rates", "Default: 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 303030, 204081, 142857, 100000, 66666, 40000, 25000, 20000, 20000, 20000, 20000, 20000, 20000"})
    @ConfigProperty(name = "rate.enchant.weapon.level", value = "1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 303030, 204081, 142857, 100000, 66666, 40000, 25000, 20000, 20000, 20000, 20000, 20000, 20000")
    public static int[] ENCHANT_WEAPON_RATES;
    @ConfigComments(comment = {"Enchant clothes rates", "Default: 1000000, 300000, 150000, 70000, 40000, 20000"})
    @ConfigProperty(name = "rate.enchant.clothes.level", value = "1000000, 300000, 150000, 70000, 40000, 20000")
    public static int[] ENCHANT_CLOTHES_RATES;
    @ConfigComments(comment = {"Enchant accessory rates", "Default: 1000000, 300000, 150000, 70000, 40000, 20000"})
    @ConfigProperty(name = "rate.enchant.accessory.level", value = "1000000, 300000, 150000, 70000, 40000, 20000")
    public static int[] ENCHANT_ACCESSORY_RATES;
    @ConfigComments(comment = {"Enchant fail rates", "Default: 10000"})
    @ConfigProperty(name = "rate.enchant.fail", value = "10000")
    public static int ENCHANT_FAIL_RATE;
    @ConfigComments(comment = {"Worker luck rate:", "100%"})
    @ConfigProperty(name = "worker.luck.rate", value = "500")
    public static int WORKER_LUCK_RATE;
}
