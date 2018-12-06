package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@ConfigFile(name = "configs/filtering.properties")
public class FilteringConfig {
    @ConfigComments(comment = {"Filtering chat enable:", "true or false"})
    @ConfigProperty(name = "filtering.chat.enable", value = "true")
    public static boolean CHAT_ENABLE;
    @ConfigComments(comment = {"Filtering username enable:", "true or false"})
    @ConfigProperty(name = "filtering.username.enable", value = "true")
    public static boolean USERNAME_ENABLE;
    @ConfigComments(comment = {"List of restricted user names (in LOWER case)"})
    @ConfigProperty(name = "filtering.restricted.user.names", value = "admin,administrator,gm,gamemaster")
    public static List<String> RESTRICTED_USER_NAMES;

    static {
        FilteringConfig.RESTRICTED_USER_NAMES = new ArrayList<String>();
    }
}
