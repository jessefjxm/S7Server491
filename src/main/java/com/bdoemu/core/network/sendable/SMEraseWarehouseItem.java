// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMEraseWarehouseItem extends SendablePacket<GameClient> {
    private final EItemStorageLocation locationType;
    private final int townId;
    private final long objectId;
    private final long count;

    public SMEraseWarehouseItem(final EItemStorageLocation locationType, final int townId, final long objectId, final long count) {
        this.locationType = locationType;
        this.townId = townId;
        this.objectId = objectId;
        this.count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.locationType.getId());
        buffer.writeH(this.townId);
        buffer.writeQ(this.objectId);
        buffer.writeQ(this.count);
    }
}
