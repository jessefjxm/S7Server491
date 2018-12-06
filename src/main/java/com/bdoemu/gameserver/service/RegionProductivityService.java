package com.bdoemu.gameserver.service;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMSetRegionProductivity;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.dataholders.RegionGroupData;
import com.bdoemu.gameserver.model.world.region.enums.ERegionManagingType;
import com.bdoemu.gameserver.model.world.region.templates.RegionGroupTemplate;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@StartupComponent("Service")
public class RegionProductivityService extends APeriodicTaskService {
    private Map<Integer, Map<ERegionManagingType, Integer>> productivityMap;

    private RegionProductivityService() {
        super(15L, TimeUnit.MINUTES);

        this.productivityMap = new HashMap<>();
        for (final RegionGroupTemplate regionGroupTemplate : RegionGroupData.getInstance().getTemplates()) {

            // Initialize stuff region group.
            if (!this.productivityMap.containsKey(regionGroupTemplate.getRegionGroupKey()))
                this.productivityMap.put(regionGroupTemplate.getRegionGroupKey(), new HashMap<>());

            for (final ERegionManagingType regionManagingType : ERegionManagingType.values())
                this.productivityMap.get(regionGroupTemplate.getRegionGroupKey()).put(regionManagingType, Rnd.get(750, 1000) * 1000);
        }
    }

    public static RegionProductivityService getInstance() {
        return Holder.INSTANCE;
    }

    public void recalculateProductivity(final int regionGroupId, final ERegionManagingType regionManagingType, final int value) {
        //
    }

    @Override
    public void run() {
        try {
            for (final RegionGroupTemplate regionGroupTemplate : RegionGroupData.getInstance().getTemplates()) {
                for (final ERegionManagingType regionManagingType : ERegionManagingType.values()) {
                    switch (regionManagingType) {
                        case Loyalty:
                            this.productivityMap.get(regionGroupTemplate.getRegionGroupKey()).put(regionManagingType, Rnd.get(500, 1000) * 1000);
                            break;
                        case Farming:
                            this.productivityMap.get(regionGroupTemplate.getRegionGroupKey()).put(regionManagingType, Rnd.get(500, 1000) * 1000);
                            break;
                        case Fishing:
                            this.productivityMap.get(regionGroupTemplate.getRegionGroupKey()).put(regionManagingType, Rnd.get(500, 1000) * 1000);
                            break;
                    }
                }
            }

            World.getInstance().broadcastWorldPacket(new SMSetRegionProductivity());
        } catch (Exception eh) {
            eh.printStackTrace();
        }
    }

    public Map<Integer, Map<ERegionManagingType, Integer>> getProductivityMap() {
        return this.productivityMap;
    }

    private static class Holder {
        static final RegionProductivityService INSTANCE = new RegionProductivityService();
    }
}
