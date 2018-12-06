package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;
import com.bdoemu.gameserver.model.chat.enums.EChatType;

import java.util.ArrayList;
import java.util.List;

@ConfigFile(name = "configs/discord.properties")
public class DiscordConfig {
    @ConfigComments(comment = {"Enable discord service", "Default: false"})
    @ConfigProperty(name = "discord.enable", value = "false")
    public static boolean ENABLE;
    @ConfigComments(comment = {"Discord access bot token", "MjMyMjQ1NDEwOTkzNzk5MTY5.CtP7-A.5temzHx_m7B3iVunVGNkrDXiaYA"})
    @ConfigProperty(name = "discord.bot.token", value = "MjMyMjQ1NDEwOTkzNzk5MTY5.CtP7-A.5temzHx_m7B3iVunVGNkrDXiaYA")
    public static String BOT_TOKEN;
    @ConfigComments(comment = {"Discord bot status", "Default: ogrefest.org"})
    @ConfigProperty(name = "discord.bot.status", value = "ogrefest.org")
    public static String BOT_STATUS;
    @ConfigComments(comment = {"Available chat types for broadcasting to discord chat channel", "Default: World,Public"})
    @ConfigProperty(name = "available.chat.types", value = "World,Public")
    public static List<EChatType> AVAILABLE_CHAT_TYPES;
    @ConfigComments(comment = {"Discord channel for chat broadcasting", "Default: 232507424655802368"})
    @ConfigProperty(name = "discord.chat.channel.id", value = "232507424655802368")
    public static String CHAT_CHANNEL_ID;

    static {
        DiscordConfig.AVAILABLE_CHAT_TYPES = new ArrayList<EChatType>();
    }
}
