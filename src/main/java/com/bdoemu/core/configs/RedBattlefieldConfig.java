package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

/**
 * @author H1X4
 */
@ConfigFile(name = "configs/red_battlefield.properties")
public class RedBattlefieldConfig {
    @ConfigProperty(name = "pvp.red_battlefield.seals_reward.winner", value = "25")
    public static int REWARD_SEALS_COUNT_WINNER;

    @ConfigProperty(name = "pvp.red_battlefield.seals_reward.loser", value = "15")
    public static int REWARD_SEALS_COUNT_LOSER;

    @ConfigProperty(name = "pvp.red_battlefield.silver_reward.winner", value = "2000000")
    public static int REWARD_SILVER_COUNT_WINNER;

    @ConfigProperty(name = "pvp.red_battlefield.silver_reward.winner", value = "1000000")
    public static int REWARD_SILVER_COUNT_LOSER;
}