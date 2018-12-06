// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.List;

public class SMRepurchaseItems extends WriteItemInfo {
    private final List<Item> items;
    private final EPacketTaskType actionType;
    private final int npcSessionId;

    public SMRepurchaseItems(final List<Item> items, final EPacketTaskType actionType, final int npcSessionId) {
        this.items = items;
        this.actionType = actionType;
        this.npcSessionId = npcSessionId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.npcSessionId);
        buffer.writeH(this.actionType.ordinal());
        buffer.writeC(0); // after 408
        buffer.writeH(this.items.size());
        buffer.writeH(0);
        for (final Item item : this.items) {
            this.writeItemInfo(buffer, item);
        }
    }
}
