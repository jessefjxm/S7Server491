// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.DebugConfig;
import com.bdoemu.core.configs.RegionConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.bdoemu.gameserver.model.world.region.BKDZoneSectorPoint;
import com.bdoemu.gameserver.model.world.region.enums.ERegionImgType;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import com.bdoemu.gameserver.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "region", group = "sqlite")
@StartupComponent("Data")
public class RegionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    public static int MAP_MIN_X;
    public static int MAP_MAX_Y;
    public static float MAP_IMAGE_SCALE;
    private static Map<Integer, RegionTemplate> templates;
    private static Map<Integer, RegionTemplate> templatesByRgb;
    private static EnumMap<ETerritoryKeyType, List<RegionTemplate>> templatesByTerritoryKey;
    private static BufferedImage regionImg;
    private static BufferedImage fishingImg;
    private static BufferedImage waterImg;
    private static BufferedImage undergroundImg;
    private static BufferedImage villageSiegeImg;
    private static BufferedImage siegeImg;
    private static BufferedImage waterLevelImg;

    static {
        log = LoggerFactory.getLogger((Class) RegionData.class);
        instance = new AtomicReference<Object>();
        RegionData.templates = new HashMap<Integer, RegionTemplate>();
        RegionData.templatesByRgb = new HashMap<Integer, RegionTemplate>();
        RegionData.templatesByTerritoryKey = new EnumMap<ETerritoryKeyType, List<RegionTemplate>>(ETerritoryKeyType.class);
        RegionData.regionImg = null;
        RegionData.fishingImg = null;
        RegionData.waterImg = null;
        RegionData.undergroundImg = null;
        RegionData.villageSiegeImg = null;
        RegionData.siegeImg = null;
        RegionData.waterLevelImg = null;
        RegionData.MAP_MIN_X = RegionConfig.MAP_SECTOR_START_X * 12800;
        RegionData.MAP_MAX_Y = RegionConfig.MAP_SECTOR_END_Y * 12800;
        RegionData.MAP_IMAGE_SCALE = 301.17648f;
    }

    private RegionData() {
        this.load();
    }

    public static RegionData getInstance() {
        Object value = RegionData.instance.get();
        if (value == null) {
            synchronized (RegionData.instance) {
                value = RegionData.instance.get();
                if (value == null) {
                    final RegionData actualValue = new RegionData();
                    value = ((actualValue == null) ? RegionData.instance : actualValue);
                    RegionData.instance.set(value);
                }
            }
        }
        return (RegionData) ((value == RegionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("RegionData Loading");
        this.loadTable();
        this.loadImages();
    }

    private void loadTable() {
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM RegionWeather_Table LEFT JOIN Region_Table ON RegionWeather_Table.`Index` = Region_Table.`Index`")) {
            final ResultSetMetaData rsMeta = rs.getMetaData();
            final int disabledByContentGroup = 0;
            while (rs.next()) {
                final RegionTemplate template = new RegionTemplate(rs, rsMeta);
                RegionData.templates.put(template.getRegionId(), template);
                RegionData.templatesByRgb.put(template.getColor().getRGB(), template);
                RegionData.templatesByTerritoryKey.computeIfAbsent(template.getTerritoryKey(), k -> new ArrayList<>()).add(template);
            }
            RegionData.log.info("Loaded {} RegionTemplate's. ({} disabled by ContentsGroupOptionData)", RegionData.templates.size(), (Object) disabledByContentGroup);
        } catch (SQLException ex) {
            RegionData.log.error("Failed load RegionTemplate", (Throwable) ex);
        }
    }

    private void loadImages() {
        this.readBmp("./data/static_data/binary/regions/regionmap_new.region", ERegionImgType.Region);
        if (!DebugConfig.DISABLE_REGION_DATA_LOAD) {
            this.readBmp("./data/static_data/binary/regions/fishingmap.region", ERegionImgType.Fishing);
            this.readBmp("./data/static_data/binary/regions/watermap.region", ERegionImgType.Water);
            this.readBmp("./data/static_data/binary/regions/underground.region", ERegionImgType.Underground);
            this.readBmp("./data/static_data/binary/regions/villagesiegemap.region", ERegionImgType.VillageSiege);
            this.readBmp("./data/static_data/binary/regions/siegemap.region", ERegionImgType.Siege);
        }
    }

    private void readBmp(final String path, final ERegionImgType imgType) {
        try (final FileBinaryReader bdoReadFile = new FileBinaryReader(path)) {
            final int colorCount = bdoReadFile.readD();
            final List<Color> colors = new ArrayList<Color>();
            for (int colorIndex = 0; colorIndex < colorCount; ++colorIndex) {
                colors.add(new Color(bdoReadFile.readD()));
            }
            final int width = bdoReadFile.readD();
            final int height = bdoReadFile.readD();
            final BufferedImage image = new BufferedImage(width, height, 1);
            final int bkdBlockCount = bdoReadFile.readD();
            int offSetX = 0;
            int offSetY = 0;
            Label_0343:
            for (int bkdBlockIndex = 0; bkdBlockIndex < bkdBlockCount; ++bkdBlockIndex) {
                final int bkdBlockSubCount = bdoReadFile.readD();
                final ConcurrentLinkedQueue<BKDZoneSectorPoint> points = new ConcurrentLinkedQueue<BKDZoneSectorPoint>();
                for (int bkdSubBlockIndex = 0; bkdSubBlockIndex < bkdBlockSubCount; ++bkdSubBlockIndex) {
                    final int x = bdoReadFile.readCD();
                    final int y = bdoReadFile.readCD();
                    final int ridIndex = bdoReadFile.readHD();
                    final Color color = colors.get(ridIndex);
                    points.add(new BKDZoneSectorPoint(x, y, color));
                }
                BKDZoneSectorPoint currentPoint = points.poll();
                Color color2 = currentPoint.getColor();
                for (int blockY = 0; blockY <= 255; ++blockY) {
                    for (int blockX = 0; blockX <= 255; ++blockX) {
                        if (blockY == currentPoint.getOffsetY() && blockX == currentPoint.getOffsetX()) {
                            color2 = currentPoint.getColor();
                            if (points.size() > 0) {
                                currentPoint = points.poll();
                            }
                        }
                        image.setRGB(offSetX, offSetY, color2.getRGB());
                        if (offSetX == width - 1) {
                            offSetX = 0;
                            if (++offSetY == height) {
                                break Label_0343;
                            }
                        } else {
                            ++offSetX;
                        }
                    }
                }
            }
            switch (imgType) {
                case Region: {
                    RegionData.regionImg = image;
                    break;
                }
                case Fishing: {
                    RegionData.fishingImg = image;
                    break;
                }
                case Water: {
                    RegionData.waterImg = image;
                    break;
                }
                case Underground: {
                    RegionData.undergroundImg = image;
                    break;
                }
                case VillageSiege: {
                    RegionData.villageSiegeImg = image;
                    break;
                }
                case Siege: {
                    RegionData.siegeImg = image;
                    break;
                }
            }
        } catch (Exception e) {
            RegionData.log.error("Error while reading region file", (Throwable) e);
        }
    }

    public List<RegionTemplate> getTemplateByTerritoryKey(final ETerritoryKeyType territoryKey) {
        return RegionData.templatesByTerritoryKey.get(territoryKey);
    }

    public RegionTemplate getTemplate(final int regionId) {
        return RegionData.templates.get(regionId);
    }

    public RegionTemplate getTemplate(final double x, final double y) {
        final int rgb = this.getRegionMask(ERegionImgType.Region, x, y);
        return RegionData.templatesByRgb.get(rgb);
    }

    public Collection<RegionTemplate> getTemplates() {
        return RegionData.templates.values();
    }

    public int getRegionMask(final ERegionImgType regionType, final double x, final double y) {
        final double diffX = x - RegionData.MAP_MIN_X;
        final double diffY = RegionData.MAP_MAX_Y - y;
        final int pixelX = (int) (diffX / RegionData.MAP_IMAGE_SCALE);
        final int pixelY = (int) (diffY / RegionData.MAP_IMAGE_SCALE);
        switch (regionType) {
            case Underground: {
                return RegionData.undergroundImg.getRGB(pixelX, pixelY);
            }
            case Water: {
                return RegionData.waterImg.getRGB(pixelX, pixelY);
            }
            case Fishing: {
                return RegionData.fishingImg.getRGB(pixelX, pixelY);
            }
            case Region: {
                return RegionData.regionImg.getRGB(pixelX, pixelY);
            }
            case VillageSiege: {
                return RegionData.villageSiegeImg.getRGB(pixelX, pixelY);
            }
            case Siege: {
                return RegionData.siegeImg.getRGB(pixelX, pixelY);
            }
            default: {
                return -1;
            }
        }
    }

    public RegionTemplate getNearestReturnTemplate(final double x, final double y, final double z, final ETerritoryKeyType territoryKeyType) {
        RegionTemplate nearestTemplate = null;
        for (final RegionTemplate template : RegionData.templates.values()) {
            if (template.getTerritoryKey() != territoryKeyType) {
                continue;
            }
            if (nearestTemplate == null) {
                nearestTemplate = template;
            } else {
                if (MathUtils.getDistance(x, y, z, template.getReturnX(), template.getReturnY(), template.getReturnZ()) >= MathUtils.getDistance(x, y, z, nearestTemplate.getReturnX(), nearestTemplate.getReturnY(), nearestTemplate.getReturnZ())) {
                    continue;
                }
                nearestTemplate = template;
            }
        }
        return nearestTemplate;
    }

    public void reload() {
        this.load();
    }
}
