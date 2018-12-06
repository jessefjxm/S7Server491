// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMAddItemToInventory extends WriteItemInfo {
    private final int gameObjectId;
    private final Collection<Item> addTasks;
    private final EPacketTaskType packetTaskType;
    private EStringTable messageId;

    public SMAddItemToInventory(final int gameObjectId, final Collection<Item> addTasks, final EStringTable messageId, final EPacketTaskType packetTaskType) {
        this.messageId = EStringTable.NONE;
        this.gameObjectId = gameObjectId;
        this.addTasks = addTasks;
        this.messageId = messageId;
        this.packetTaskType = packetTaskType;
    }

    public SMAddItemToInventory(final int gameObjectId, final Collection<Item> addTasks, final EPacketTaskType packetTaskType) {
        this.messageId = EStringTable.NONE;
        this.gameObjectId = gameObjectId;
        this.addTasks = addTasks;
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeC(1);
        buffer.writeD(this.gameObjectId);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(this.messageId.getHash());
        buffer.writeH(this.addTasks.size());
        for (final Item item : this.addTasks) {
            buffer.writeC(item.getStorageLocation().getId());
            buffer.writeC(item.getSlotIndex());
            this.writeItemInfo(buffer, item);
        }
    }
}
