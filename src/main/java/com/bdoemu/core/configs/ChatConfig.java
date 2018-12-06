package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;
import com.bdoemu.gameserver.model.misc.enums.EServerCustomMessageType;

@ConfigFile(name = "configs/chat.properties")
public class ChatConfig {
    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    // +++++++~~~~~~~~~~~~~~~~~~~~ MOTD ~~~~~~~~~~~~~~~~~~~~~~~~~~~~++++++++
    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    @ConfigComments(comment = {"Enable or disable motd"})
    @ConfigProperty(name = "motd_enable", value = "true")
    public static boolean MOTD_ENABLE;

    @ConfigComments(comment = {"Text message when user logs into the server."})
    @ConfigProperty(name = "motd", value = "Welcome to OgreFest! We hope that you enjoy your stay in our server! Please visit our server Discord for maintenance announcements, donations, patch notes and etc https://discord.gg/N4B6WxB and English, Portuguese, Espanol, Russian chats.")
    public static String MOTD_TEXT;

    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    // +++++++~~~~~~~~~~~~~~~~ CHAT CHANNELS ~~~~~~~~~~~~~~~~~~~~~~~++++++++
    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    @ConfigProperty(name = "channel_enable", value = "true")
    public static boolean CHANNEL_ENABLE;

    @ConfigProperty(name = "channel_channels", value = "English,Portuguese,Espanol,Russian,German,French")
    public static String CHANNELS;

    @ConfigProperty(name = "channel_autojoin", value = "English")
    public static String CHANNEL_AUTOJOIN;

    @ConfigProperty(name = "channel_force", value = "false")
    public static boolean CHANNEL_JOIN_FORCE;

    @ConfigComments(comment = {"Server custom message language.", "Options: EN, RU, FR, DE, ES, BR"})
    @ConfigProperty(name = "server_custom_message_language", value = "EN")
    public static EServerCustomMessageType SERVER_CUSTOM_MESSAGE_LANGUAGE;

    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    // +++++++~~~~~~~~~~~!~~~ CHAT FLOOD CONTROL ~~~~~~~~~~~~~~~~~~~++++++++
    // ~~~~~~~++++++++++++++++++++++++++++++++++++++++++++++++++++++~~~~~~~~
    @ConfigProperty(name = "flood.enable", value = "true")
    public static boolean FLOOD_CONTROL_ENABLE;

    @ConfigProperty(name = "flood.increase_per_second", value = "0.5")
    public static float FLOOD_CONTROL_INCREASE_PER_SECOND;

    @ConfigProperty(name = "flood.threshold", value = "1000")
    public static int FLOOD_CONTROL_THRESHOLD;
}


