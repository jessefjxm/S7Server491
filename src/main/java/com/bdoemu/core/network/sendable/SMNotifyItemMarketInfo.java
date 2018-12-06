// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMNotifyItemMarketInfo extends SendablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) SMNotifyItemMarketInfo.class);
    }

    private final long itemMarketObjectId;
    private final long registeredDate;
    private final int itemId;
    private final int enchant;
    private final ENotifyItemMarketInfoType type;
    private final long count;
    private ItemMarket itemMarket;

    public SMNotifyItemMarketInfo(final ItemMarket itemMarket, final ENotifyItemMarketInfoType type) {
        this(itemMarket, type, 0L);
    }

    public SMNotifyItemMarketInfo(final ItemMarket itemMarket, final ENotifyItemMarketInfoType type, final long count) {
        this.itemMarket = itemMarket;
        this.type = type;
        this.count = count;
        this.itemMarketObjectId = itemMarket.getObjectId();
        this.registeredDate = itemMarket.getRegisteredDate();
        this.itemId = itemMarket.getItemId();
        this.enchant = itemMarket.getEnchantLevel();
    }

    public SMNotifyItemMarketInfo(final ENotifyItemMarketInfoType type, final long itemMarketObjectId, final int itemId, final int enchant) {
        this.type = type;
        this.count = 0L;
        this.itemMarketObjectId = itemMarketObjectId;
        this.itemId = itemId;
        this.enchant = enchant;
        this.registeredDate = 0L;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.type.ordinal());
        buffer.writeQ(this.registeredDate);
        buffer.writeQ(this.itemMarketObjectId);
        switch (this.type) {
            case BUY:
            case UPDATE: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchant);
                buffer.writeB(new byte[12]);
                buffer.writeQ(this.count);
                buffer.writeQ(0L);
                break;
            }
            case WITHDRAW_MONEY: {
                buffer.writeH(1);
                buffer.writeB(new byte[30]);
                break;
            }
            case UNREGISTER: {
                buffer.writeH(0);
                buffer.writeH(0);
                buffer.writeQ(0L);
                buffer.writeQ(0L);
                buffer.writeH(0);
                buffer.writeQ(0L);
                buffer.writeH(0);
                break;
            }
            case REGISTER: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchant);
                buffer.writeQ(0L);
                buffer.writeQ(this.itemMarket.getExpirationDate());
                buffer.writeH(this.itemMarket.getTerritoryKey());
                buffer.writeQ(this.itemMarket.getCount());
                buffer.writeH(0);
                break;
            }
            case REGISTER_RESERVATION: {
                buffer.writeB(new byte[32]);
                break;
            }
            case CANCEL_RESERVATION: {
                buffer.writeQ(0L);
                buffer.writeQ(0L);
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchant);
                buffer.writeD(0);
                buffer.writeQ(0L);
                break;
            }
        }
    }
}
