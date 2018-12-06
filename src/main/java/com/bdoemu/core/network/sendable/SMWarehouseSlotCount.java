// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;

public class SMWarehouseSlotCount extends SendablePacket<GameClient> {
    private final Warehouse warehouse;

    public SMWarehouseSlotCount(final Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.warehouse.getTownId());
        buffer.writeC(this.warehouse.getExpandSize());
    }
}
