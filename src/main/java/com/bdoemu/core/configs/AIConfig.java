package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/ai.properties")
public class AIConfig {
    @ConfigProperty(name = "spawn_ai", value = "true")
    public static boolean SPAWN_AI;

    @ConfigProperty(name = "spawn.npc", value = "true")
    public static boolean SPAWN_NPC;

    @ConfigProperty(name = "ai.legacy", value="true")
    public static boolean USE_LEGACY_AI;

    @ConfigProperty(name = "event_loop.fps", value = "33.333332f")
    public static float EVENT_LOOP_FPS;

    @ConfigProperty(name = "thread.pool_type", value="2")
    public static int TREAD_POOL_TYPE;

    @ConfigProperty(name = "thread.pool_size", value = "16")
    public static int THREAD_POOL_SIZE;
}
