package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;
import com.bdoemu.gameserver.model.geo.GeoSectorType;

import java.util.ArrayList;
import java.util.List;

@ConfigFile(name = "configs/geodata.properties")
public class GeoDataConfig {
    @ConfigComments(comment = {"Enable Geo data", "Default: false"})
    @ConfigProperty(name = "enable", value = "false")
    public static boolean ENABLE;
    @ConfigComments(comment = {"Enable can see check for monsters", "Default: false"})
    @ConfigProperty(name = "enable.can.see", value = "false")
    public static boolean ENABLE_CAN_SEE;
    @ConfigComments(comment = {"If true - loading geo from ZIP archive (Path specified by geo.zip.file.path property)", "If false - loading geo from folder (Path specified by geo.file.path property)", "Default: true"})
    @ConfigProperty(name = "load.from.zip", value = "true")
    public static boolean LOAD_FROM_ZIP;
    @ConfigComments(comment = {"Path to ZIP file, containing geo files", "Default: data/static_data/binary/geodata.zip"})
    @ConfigProperty(name = "geo.zip.file.path", value = "data/static_data/binary/geodata.zip")
    public static String GEO_ZIP_FILE_PATH;
    @ConfigComments(comment = {"Path to folder, containing geo files", "Default: data/static_data/binary/geodata"})
    @ConfigProperty(name = "geo.file.path", value = "data/static_data/binary/geodata")
    public static String GEO_FILE_PATH;
    @ConfigComments(comment = {"Load geo sector to memory after trying to access.", "Default: true"})
    @ConfigProperty(name = "geo.dynamic.load", value = "true")
    public static boolean GEO_DYNAMIC_LOAD;
    @ConfigComments(comment = {"Allowed to load geo sector types.", "Default: TERRAIN, COLLISION"})
    @ConfigProperty(name = "allowed.geo.sector.types", value = "TERRAIN, COLLISION")
    public static List<GeoSectorType> ALLOWED_GEO_SECTOR_TYPES;
    @ConfigComments(comment = {"Lod Levels {0,1,2,3,4,5,6,7} decrease mesh quality 10% per lod level.", "Default: 0"})
    @ConfigProperty(name = "geo.terrain.lod_level", value = "0")
    public static int GEO_TERRAIN_LOD_LEVEL;
    @ConfigComments(comment = {"Lod Levels {0,1,2,3,4,5,6,7} decrease mesh quality 10% per lod level.", "Default: 0"})
    @ConfigProperty(name = "geo.collision.lod_level", value = "0")
    public static int GEO_COLLISION_LOD_LEVEL;

    static {
        GeoDataConfig.ALLOWED_GEO_SECTOR_TYPES = new ArrayList<GeoSectorType>();
    }
}
