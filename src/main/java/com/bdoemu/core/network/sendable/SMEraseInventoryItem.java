// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseItemTask;

import java.util.Collection;

public class SMEraseInventoryItem extends SendablePacket<GameClient> {
    private final int gameObjectId;
    private final Collection<DecreaseItemTask> tasks;
    private EStringTable messageId;

    public SMEraseInventoryItem(final int gameObjectId, final Collection<DecreaseItemTask> tasks, final EStringTable messageId) {
        this.gameObjectId = gameObjectId;
        this.tasks = tasks;
        this.messageId = messageId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjectId);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(this.messageId.getHash());
        buffer.writeH(this.tasks.size());
        for (final DecreaseItemTask task : this.tasks) {
            buffer.writeC(task.getStorageLocation().getId());
            buffer.writeC(task.getSlotIndex());
            buffer.writeQ(task.getObjectId());
            buffer.writeC(0);
            buffer.writeQ(task.getCount());
        }
    }
}
