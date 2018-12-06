package com.bdoemu.gameserver.service;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.model.world.region.Region;
import com.bdoemu.gameserver.model.world.region.enums.ERegionImgType;
import com.bdoemu.gameserver.model.world.region.enums.ERegionType;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@StartupComponent("Service")
public class RegionService {
    private Map<Integer, Region> regionMap;

    private RegionService() {
        this.regionMap = new HashMap<>();
        for (final RegionTemplate template : RegionData.getInstance().getTemplates()) {
            final Region region = new Region(template);
            this.regionMap.put(template.getColor().getRGB(), region);
        }

        ThreadPool.getInstance().scheduleEffectAtFixedRate(() -> {
            for (final Region region : RegionService.this.regionMap.values()) {
                if (region.getRegionType() == ERegionType.Hunting) {
                    region.applySkills();
                }
            }
        }, 0L, 1L, TimeUnit.MINUTES);
    }

    public static RegionService getInstance() {
        return Holder.INSTANCE;
    }

    public Region getRegion(final double x, final double y) {
        final int rgb = RegionData.getInstance().getRegionMask(ERegionImgType.Region, x, y);
        return this.regionMap.get(rgb);
    }

    private static class Holder {
        static final RegionService INSTANCE = new RegionService();
    }
}