package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/player.properties")
public class PlayerConfig {
    @ConfigComments(comment = {"Player maximum level."})
    @ConfigProperty(name = "player.max_level", value = "100")
    public static int MAX_LEVEL;
    @ConfigComments(comment = {"Player tendency minimum."})
    @ConfigProperty(name = "player.min_tendency", value = "-1000000")
    public static int MIN_TENDENCY;
    @ConfigComments(comment = {"Player tendency maximum."})
    @ConfigProperty(name = "player.max_tendency", value = "300000")
    public static int MAX_TENDENCY;
}
