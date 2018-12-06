// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMListItemHeaderAtItemMarket extends SendablePacket<GameClient> {
    private final Collection<MasterItemMarket> masterItems;
    private final EPacketTaskType packetTaskType;

    public SMListItemHeaderAtItemMarket(final Collection<MasterItemMarket> masterItems, final EPacketTaskType packetTaskType) {
        this.masterItems = masterItems;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.masterItems.size());
        for (final MasterItemMarket masterItem : this.masterItems) {
            buffer.writeH(0);
            buffer.writeQ(GameTimeService.getServerTimeInSecond() + 600000L);
            buffer.writeQ(GameTimeService.getServerTimeInSecond());
            buffer.writeH(masterItem.getItemId());
            buffer.writeH(masterItem.getEnchantLevel());
            buffer.writeQ(masterItem.getLastPrice());
            buffer.writeQ(0L);
            buffer.writeQ(masterItem.getItemMaxPrice());
            buffer.writeQ(masterItem.getItemMinPrice());
            buffer.writeD(masterItem.size());
            buffer.writeQ(masterItem.getCount());
            buffer.writeQ(masterItem.getSoldCount());
            buffer.writeQ(398665L);
        }
    }
}
