// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.ENotifyItemMarketPartyInfoType;

public class SMNotifyItemMarketByPartyInfo extends SendablePacket<GameClient> {
    private int itemId;
    private int enchantLevel;
    private long itemMarketObjId;
    private long count;
    private ENotifyItemMarketPartyInfoType type;

    public SMNotifyItemMarketByPartyInfo(final int itemId, final int enchantLevel, final long itemMarketObjId, final long count, final ENotifyItemMarketPartyInfoType type) {
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.itemMarketObjId = itemMarketObjId;
        this.count = count;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.type.ordinal());
        switch (this.type) {
            case Buy: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeQ(this.itemMarketObjId);
                buffer.writeQ(this.count);
                buffer.writeB(16);
                break;
            }
            case Clear: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeQ(this.itemMarketObjId);
                buffer.writeQ(this.count);
                buffer.writeB(16);
                break;
            }
            case Register: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeQ(this.count);
                buffer.writeB(24);
                break;
            }
            case Dice: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeQ(this.itemMarketObjId);
                buffer.writeQ(this.count);
                buffer.writeB(16);
                break;
            }
        }
    }
}
