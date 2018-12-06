// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class SMListDeliveryItem extends SendablePacket<GameClient> {
    private EPacketTaskType packetTaskType;

    public SMListDeliveryItem(final EPacketTaskType packetTaskType) {
        this.packetTaskType = packetTaskType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(0);
    }
}
