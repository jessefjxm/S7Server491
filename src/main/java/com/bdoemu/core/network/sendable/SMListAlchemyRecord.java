// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.alchemy.AlchemyRecord;

import java.util.Collection;

public class SMListAlchemyRecord extends SendablePacket<GameClient> {
    private Collection<AlchemyRecord> alchemyRecords;
    private int cardId;
    private int type;

    public SMListAlchemyRecord(final int cardId, final int type, final Collection<AlchemyRecord> alchemyRecords) {
        this.cardId = cardId;
        this.type = type;
        this.alchemyRecords = alchemyRecords;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.cardId);
        buffer.writeC(this.type);
        buffer.writeH(this.alchemyRecords.size());
        for (final AlchemyRecord alchemyRecord : this.alchemyRecords) {
            for (final int itemId : alchemyRecord.getItemIds()) {
                buffer.writeH(itemId);
                buffer.writeH(0);
            }
            for (final long count : alchemyRecord.getCounts()) {
                buffer.writeQ(count);
            }
        }
    }
}
