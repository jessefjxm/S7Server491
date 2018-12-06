package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.world.region.enums.ERegionManagingType;
import com.bdoemu.gameserver.service.RegionProductivityService;

import java.util.Map;

public class SMSetRegionProductivity extends SendablePacket<GameClient> {
    private Map<Integer, Map<ERegionManagingType, Integer>> productivityMap;

    public SMSetRegionProductivity() {
        this.productivityMap = RegionProductivityService.getInstance().getProductivityMap();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.productivityMap.size());
        for (final Map.Entry<Integer, Map<ERegionManagingType, Integer>> groupEntry : this.productivityMap.entrySet()) {
            buffer.writeH((int) groupEntry.getKey());
            buffer.writeD((int) groupEntry.getValue().get(ERegionManagingType.Fishing));
            buffer.writeD((int) groupEntry.getValue().get(ERegionManagingType.Farming));
            buffer.writeD((int) groupEntry.getValue().get(ERegionManagingType.Loyalty));
        }
    }
}
