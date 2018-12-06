package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/region.properties")
public class RegionConfig {
    @ConfigComments(comment = {"Map start sector by X", "Default: -160"})
    @ConfigProperty(name = "map.sector.start.x", value = "-160")
    public static int MAP_SECTOR_START_X;
    @ConfigComments(comment = {"Map start sector by Y", "Default: -88"})
    @ConfigProperty(name = "map.sector.start.y", value = "-88")
    public static int MAP_SECTOR_START_Y;
    @ConfigComments(comment = {"Map end sector by X", "Default: 112"})
    @ConfigProperty(name = "map.sector.end.x", value = "112")
    public static int MAP_SECTOR_END_X;
    @ConfigComments(comment = {"Map end sector by Y", "Default: 160"})
    @ConfigProperty(name = "map.sector.end.y", value = "160")
    public static int MAP_SECTOR_END_Y;
    @ConfigProperty(name = "map.subsector.x", value = "-3")
    public static int SUB_SECTOR_X_OFFSET;
    @ConfigProperty(name = "map.subsector.y", value = "-3")
    public static int SUB_SECTOR_Y_OFFSET;
    @ConfigProperty(name = "map.sector.size", value = "12800")
    public static int MAP_SECTOR_SIZE;
    @ConfigProperty(name = "know.subsector.size", value = "3700")
    public static int MAP_SUBSECTOR_SIZE;
    @ConfigProperty(name = "see.range", value = "3200")
    public static int SEE_RANGE;
    @ConfigProperty(name = "exit.range", value = "3700")
    public static int EXIT_RANGE;
}
