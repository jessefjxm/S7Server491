package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;
import com.bdoemu.commons.model.enums.EAccessLevel;

import java.util.HashMap;
import java.util.Map;

@ConfigFile(name = "configs/commands.properties")
public class CommandsConfig {
    @ConfigComments(comment = {"Config for overriding command handler accesses."})
    @ConfigProperty(name = "CommandAccess", isMap = true, values = {"CommandAccess.bag.add = MODERATOR", "CommandAccess.bag.addtoplayer = ADMIN"})
    public static Map<String, EAccessLevel> COMMAND_ACCESS;

    static {
        CommandsConfig.COMMAND_ACCESS = new HashMap<String, EAccessLevel>();
    }
}
