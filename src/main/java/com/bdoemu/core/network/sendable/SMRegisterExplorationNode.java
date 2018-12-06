// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMRegisterExplorationNode extends SendablePacket<GameClient> {
    private final int nodeId;

    public SMRegisterExplorationNode(final int nodeId) {
        this.nodeId = nodeId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.nodeId);
    }
}
