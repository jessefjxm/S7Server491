// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMListSellInfoAtItemMarketByParty extends SendablePacket<GameClient> {
    public static final int MAX_CAPACITY = 739;
    private Collection<PartyItemMarket> items;
    private EPacketTaskType packetTaskType;
    private int type;
    private Player player;

    public SMListSellInfoAtItemMarketByParty(final Collection<PartyItemMarket> items, final EPacketTaskType packetTaskType, final int type, final Player player) {
        this.items = items;
        this.packetTaskType = packetTaskType;
        this.type = type;
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeC(this.type);
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeH(this.items.size());
        for (final PartyItemMarket itemMarket : this.items) {
            buffer.writeQ(itemMarket.getMarketObjectId());
            buffer.writeH(itemMarket.getItemId());
            buffer.writeH(itemMarket.getEnchantLevel());
            buffer.writeQ(itemMarket.getItemPrice());
            buffer.writeC(1);
            buffer.writeC(itemMarket.hasNoVoting(this.player));
        }
    }
}
