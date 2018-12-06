package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/actionverifyoption.properties")
public class ActionVerifyOptionConfig {

    @ConfigProperty(name = "action.verify.movement.enable", value = "true")
    public static boolean ACTION_VERIFY_MOVEMENT_ENABLE;

    @ConfigProperty(name = "action.verify.movement.max_distance_tolerance", value = "10000")
    public static int ACTION_VERIFY_MOVEMENT_MAX_DISTANCE_TOLERANCE;

    @ConfigProperty(name = "action.verify.movement.tolerance", value = "10")
    public static int ACTION_VERIFY_MOVEMENT_TOLERANCE;

    @ConfigProperty(name = "action.verify.movement.lock_time", value = "2000")
    public static int ACTION_VERIFY_MOVEMENT_LOCK_TIME;

    @ConfigProperty(name = "action.verify.movement.save_time", value = "5000")
    public static int ACTION_VERIFY_MOVEMENT_SAVE_TIME;
}
