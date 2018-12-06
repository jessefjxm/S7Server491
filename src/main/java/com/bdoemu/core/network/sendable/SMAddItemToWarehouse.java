package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMAddItemToWarehouse extends WriteItemInfo {
    private final int type;
    private final int townId;
    private final Collection<Item> addTasks;
    private final long objectId;
    private final EPacketTaskType packetTaskType;
    private EItemStorageLocation warehouseType;

    public SMAddItemToWarehouse(final Collection<Item> addTasks, EItemStorageLocation warehouseType, final int townId, final EPacketTaskType packetTaskType, final int type, final long objectId) {
        this.addTasks = addTasks;
        this.warehouseType = warehouseType;
        this.townId = townId;
        this.packetTaskType = packetTaskType;
        this.type = type;
        this.objectId = objectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(warehouseType.getId());
        buffer.writeH(this.townId);
        buffer.writeD(0);
        buffer.writeH(0);
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeC(this.type);
        buffer.writeQ(this.objectId);
        buffer.writeH(this.addTasks.size());
        this.addTasks.forEach(item -> this.writeItemInfo(buffer, item));
    }
}
