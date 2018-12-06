// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.DeliverItem;

public class SMAddDeliveryItem extends SendablePacket<GameClient> {
    private DeliverItem deliverItem;
    private long objectId;

    public SMAddDeliveryItem(final DeliverItem deliverItem, final long objectId) {
        this.deliverItem = deliverItem;
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(334);
        buffer.writeH(this.deliverItem.getOriginTownId());
        buffer.writeH(this.deliverItem.getDestTownId());
        buffer.writeC(1);
        buffer.writeQ(this.objectId);
        buffer.writeQ(this.deliverItem.getItem().getCount());
        buffer.writeQ(this.deliverItem.getItem().getObjectId());
    }
}
