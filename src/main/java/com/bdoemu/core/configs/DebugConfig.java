package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/debug.properties")
public class DebugConfig {
    @ConfigComments(comment = {"Disable collections spawn", "Default: false"})
    @ConfigProperty(name = "DisableCollectionsSpawn", value = "false")
    public static boolean DISABLE_COLLECTIONS_SPAWN;
    @ConfigComments(comment = {"Disable loading huge region images (for memory economy while tests)", "Default: false"})
    @ConfigProperty(name = "DisableRegionDataLoad", value = "false")
    public static boolean DISABLE_REGION_DATA_LOAD;
    @ConfigComments(comment = {"Enable actions debug", "Default: false"})
    @ConfigProperty(name = "EnableActionsDebug", value = "false")
    public static boolean ENABLE_ACTIONS_DEBUG;
}
