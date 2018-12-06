package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/guild.properties")
public class GuildConfig {
    @ConfigComments(comment = {"Tent attack message delay.", "Default: 60000"})
    @ConfigProperty(name = "KingOrLordTentAttackMessageCycle", value = "60000")
    public static int KING_OR_LORD_TENT_ATTACK_MESSAGE_CYCLE;
    @ConfigComments(comment = {"Tent attack percent.", "Default: 300000, 600000"})
    @ConfigProperty(name = "KingOrLordTentAttackPercent", value = "300000, 600000")
    public static int[] KING_OR_LORD_TENT_ATTACK_PERCENT;
    @ConfigComments(comment = {"Guild first level comporate tax value.", "Default: 0"})
    @ConfigProperty(name = "GuildFirstLevelComporateTax", value = "0")
    public static int GUILD_FIRST_LEVEL_COMPORATE_TAX;
    @ConfigComments(comment = {"Guild second level comporate tax value.", "Default: 100000"})
    @ConfigProperty(name = "GuildSecondLevelComporateTax", value = "100000")
    public static int GUILD_SECOND_LEVEL_COMPORATE_TAX;
    @ConfigComments(comment = {"Guild third level comporate tax value.", "Default: 250000"})
    @ConfigProperty(name = "GuildThirdLevelComporateTax", value = "250000")
    public static int GUILD_THIRD_LEVEL_COMPORATE_TAX;
    @ConfigComments(comment = {"Guild fourth level comporate tax value.", "Default: 500000"})
    @ConfigProperty(name = "GuildFourthLevelComporateTax", value = "500000")
    public static int GUILD_FOURTH_LEVEL_COMPORATE_TAX;
    @ConfigComments(comment = {"Next guild joinable time value.", "Default: 86400"})
    @ConfigProperty(name = "NextGuildJoinableTime", value = "86400")
    public static int NEXT_GUILD_JOINABLE_TIME;
    @ConfigComments(comment = {"Guild tendency convert percent for explore value.", "Default: 100000"})
    @ConfigProperty(name = "GuildTendencyConvertPercentForExplore", value = "100000")
    public static int GUILD_TENDENCY_CONVERT_PERCENT_FOR_EXPLORE;
    @ConfigComments(comment = {"Guild tendency convert percent for player value.", "Default: 0"})
    @ConfigProperty(name = "GuildTendencyConvertPercentForPlayer", value = "0")
    public static int GUILD_TENDENCY_CONVERT_PERCENT_FOR_PLAYER;
    @ConfigComments(comment = {"Guild tendency convert percent for guild war value.", "Default: 0"})
    @ConfigProperty(name = "GuildTendencyConvertPercentForGuildWar", value = "0")
    public static int GUILD_TENDENCY_CONVERT_PERCENT_FOR_GUILD_WAR;
    @ConfigComments(comment = {"Consume guild tendency for guild war value.", "Default: 100000"})
    @ConfigProperty(name = "ConsumeGuildTendencyForGuildWar", value = "100000")
    public static int CONSUME_GUILD_TENDENCY_FOR_GUILD_WAR;
    @ConfigComments(comment = {"Consume guild money for guild war value.", "Default: 300000"})
    @ConfigProperty(name = "ConsumeGuildMoneyForGuildWar", value = "300000")
    public static int CONSUME_GUILD_MONEY_FOR_GUILD_WAR;
    @ConfigComments(comment = {"Penalty time for guild war value.", "Default: 7600"})
    @ConfigProperty(name = "PenaltyTimeForGuildWar", value = "7600")
    public static int PENALTY_TIME_FOR_GUILD_WAR;
    @ConfigComments(comment = {"Increase exp member level up value.", "Default: 1"})
    @ConfigProperty(name = "IncreaseExpMemberLevelUp", value = "1")
    public static int INCREASE_EXP_MEMBER_LEVEL_UP;
    @ConfigComments(comment = {"Increase activity member level up value.", "Default: 3"})
    @ConfigProperty(name = "IncreaseActivityMemberLevelUp", value = "3")
    public static int INCREASE_ACTIVITY_MEMBER_LEVEL_UP;
    @ConfigComments(comment = {"Cheer guild buff level 1 buff id.", "Default: 65037"})
    @ConfigProperty(name = "CheerGuildBuff1", value = "65037")
    public static int CHEER_GUILD_BUFF_1;
    @ConfigComments(comment = {"Cheer guild buff level 2 buff id.", "Default: 65038"})
    @ConfigProperty(name = "CheerGuildBuff2", value = "65038")
    public static int CHEER_GUILD_BUFF_2;
    @ConfigComments(comment = {"Cheer guild buff level 3 buff id.", "Default: 65039"})
    @ConfigProperty(name = "CheerGuildBuff3", value = "65039")
    public static int CHEER_GUILD_BUFF_3;
    @ConfigComments(comment = {"Cheer guild buff level 4 buff id.", "Default: 65040"})
    @ConfigProperty(name = "CheerGuildBuff4", value = "65040")
    public static int CHEER_GUILD_BUFF_4;
    @ConfigComments(comment = {"Cheer guild buff level 5 buff id.", "Default: 65041"})
    @ConfigProperty(name = "CheerGuildBuff5", value = "65041")
    public static int CHEER_GUILD_BUFF_5;
}
