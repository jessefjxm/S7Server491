// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.DeliverItem;

public class SMCancelDeliveryItem extends SendablePacket<GameClient> {
    private DeliverItem deliverItem;

    public SMCancelDeliveryItem(final DeliverItem deliverItem) {
        this.deliverItem = deliverItem;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.deliverItem.getItem().getObjectId());
        buffer.writeQ(this.deliverItem.getItem().getObjectId());
    }
}
