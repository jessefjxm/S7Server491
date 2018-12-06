// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.ReservationItemMarket;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMListReservationPurchaseAtItemMarket extends SendablePacket<GameClient> {
    private Collection<ReservationItemMarket> reservationItemsMarket;
    private int type;

    public SMListReservationPurchaseAtItemMarket(final Collection<ReservationItemMarket> reservationItemsMarket, final int type) {
        this.reservationItemsMarket = reservationItemsMarket;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type);
        buffer.writeC(1);
        buffer.writeQ(GameTimeService.getServerTimeInSecond());
        buffer.writeH(this.reservationItemsMarket.size());
        for (final ReservationItemMarket reservationItemMarket : this.reservationItemsMarket) {
            buffer.writeH(reservationItemMarket.getItemId());
            buffer.writeH(reservationItemMarket.getEnchantLevel());
            buffer.writeH(reservationItemMarket.getTerritoryKeyType().ordinal());
            buffer.writeQ(reservationItemMarket.getMoney());
            buffer.writeQ(0L);
            buffer.writeQ(reservationItemMarket.getCount());
        }
    }
}
