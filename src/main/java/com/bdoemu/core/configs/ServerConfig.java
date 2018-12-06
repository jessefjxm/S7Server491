package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;
import com.bdoemu.commons.model.enums.EGameServiceType;
import com.bdoemu.commons.model.enums.EServiceResourceType;

@ConfigFile(name = "configs/server.properties")
public class ServerConfig {
    @ConfigComments(comment = {"Server id."})
    @ConfigProperty(name = "server.id", value = "1")
    public static byte SERVER_ID;
    @ConfigComments(comment = {"Server short name (Must be in UPPER case.)"})
    @ConfigProperty(name = "server.short.name", value = "EU")
    public static String SERVER_SHORT_NAME;
    @ConfigComments(comment = {"Channel id."})
    @ConfigProperty(name = "server.channel.id", value = "1")
    public static byte SERVER_CHANNEL_ID;
    @ConfigComments(comment = {"External server IP for client connections"})
    @ConfigProperty(name = "server.ip", value = "127.0.0.1")
    public static String SERVER_IP;
    @ConfigComments(comment = {"Game server service type.", "Options: DEV, KOR_REAL, NA_REAL, RUS_REAL, JPN_REAL"})
    @ConfigProperty(name = "server.game.service.type", value = "NA_REAL")
    public static EGameServiceType GAME_SERVICE_TYPE;
    @ConfigComments(comment = {"Game server resource type.", "Options: DEV, KR, EN, JP, CN, RU, FR, DE, ES"})
    @ConfigProperty(name = "server.game.resource.type", value = "EN")
    public static EServiceResourceType GAME_RESOURCE_TYPE;
    @ConfigComments(comment = {"Enable PC Cafe bonuses for players."})
    @ConfigProperty(name = "server.enable_pc_cafe_bonus", value = "false")
    public static boolean ENABLE_PC_CAFE_BONUS;
    @ConfigComments(comment = {"Server busy state limit values."})
    @ConfigProperty(name = "server.busy_state_limit_inspection", value = "0")
    public static int SERVER_BUSY_STATE_LIMIT_INSPECTION;
    @ConfigProperty(name = "server.busy_state_limit_smooth", value = "100")
    public static int SERVER_BUSY_STATE_LIMIT_SMOOTH;
    @ConfigProperty(name = "server.busy_state_limit_busy", value = "500")
    public static int SERVER_BUSY_STATE_LIMIT_BUSY;
    @ConfigProperty(name = "server.busy_state_limit_very_crowded", value = "1500")
    public static int SERVER_BUSY_STATE_LIMIT_VERY_CROWDED;
    @ConfigProperty(name = "server.admin_only", value = "false")
    public static boolean SERVER_ADMIN_ONLY;
}
