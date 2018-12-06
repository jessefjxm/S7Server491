package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.templates.CashItemT;

import java.util.Collection;

public class SMListCashItem extends SendablePacket<GameClient> {
    private final Collection<CashItemT> cashItems;
    private final int type;
    private final long time;

    public SMListCashItem(final Collection<CashItemT> cashItems, final long time, final int type) {
        this.cashItems = cashItems;
        this.type = type;
        this.time = time;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.time);
        buffer.writeC(this.type);
        buffer.writeH(this.cashItems.size());
        for (final CashItemT cashItem : this.cashItems) {
            buffer.writeH(cashItem.getProductNo());
            buffer.writeQ(cashItem.getPriceCash());
            buffer.writeQ(cashItem.getPricePearl());
            buffer.writeQ(cashItem.getPriceMileage());
            buffer.writeQ(0L);
            buffer.writeQ(cashItem.getDiscountPrice());
            buffer.writeQ(cashItem.getSalesStartPeriod());
            buffer.writeQ(cashItem.getSalesEndPeriod());
            buffer.writeQ(cashItem.getDiscountStartPeriod());
            buffer.writeQ(cashItem.getDiscountEndPeriod());
            buffer.writeC(cashItem.isMallDisplay());
            buffer.writeC((cashItem.getPriceCash() > 0L) ? 0 : ((cashItem.getPricePearl() > 0L) ? 1 : 2));
        }
    }
}
