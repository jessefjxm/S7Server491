// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.items.Item;

import java.util.Map;

public class SMGetDroppedItems extends WriteItemInfo {
    private final int bodySessionId;
    private DropBag dropBag;

    public SMGetDroppedItems(final int bodySessionId, final DropBag dropBag) {
        this.dropBag = dropBag;
        this.bodySessionId = bodySessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.dropBag.getDropBagType().ordinal());
        buffer.writeD(this.bodySessionId);
        buffer.writeH(this.dropBag.getDropMap().size());
        for (final Map.Entry<Integer, Item> entry : this.dropBag.getDropMap().entrySet()) {
            buffer.writeC((int) entry.getKey());
            this.writeItemInfo(buffer, entry.getValue());
        }
    }
}
