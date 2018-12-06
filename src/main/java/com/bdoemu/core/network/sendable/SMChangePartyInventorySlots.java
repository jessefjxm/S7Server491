// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.Item;

import java.util.Map;
import java.util.TreeMap;

public class SMChangePartyInventorySlots extends WriteItemInfo {
    private final int sessionId;
    private final Item item;
    private final int slot;
    private final int type;
    private final TreeMap<Integer, Item> itemMap;

    private SMChangePartyInventorySlots(final int sessionId, final Item item, final int slot, final int type, final TreeMap<Integer, Item> itemMap) {
        this.sessionId = sessionId;
        this.item = item;
        this.slot = slot;
        this.type = type;
        this.itemMap = itemMap;
    }

    public SMChangePartyInventorySlots(final int sessionId, final Item item, final int slot) {
        this(sessionId, item, slot, 0, null);
    }

    public SMChangePartyInventorySlots(final TreeMap<Integer, Item> itemMap) {
        this(-1024, null, 0, 0, itemMap);
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        if (this.item != null) {
            if (this.type == 0) {
                buffer.writeH(this.item.getItemId());
                buffer.writeH(this.item.getEnchantLevel());
                buffer.writeQ(this.item.getCount());
                buffer.writeQ(0L);
                buffer.writeH(1);
                this.writeItemData(buffer, null, this.slot);
            }
        } else if (this.itemMap != null) {
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeH(this.itemMap.size());
            for (final Map.Entry<Integer, Item> entry : this.itemMap.entrySet()) {
                this.writeItemData(buffer, entry.getValue(), entry.getKey());
            }
        }
    }

    private void writeItemData(final SendByteBuffer buffer, final Item item, final int slot) {
        buffer.writeC(slot);
        this.writeItemInfo(buffer, item);
        if (item == null) {
            buffer.writeQ(-1L);
            buffer.writeQ(-1L);
            buffer.writeQ(-1L);
            buffer.writeQ(-1L);
            buffer.writeQ(-1L);
        }
    }
}
