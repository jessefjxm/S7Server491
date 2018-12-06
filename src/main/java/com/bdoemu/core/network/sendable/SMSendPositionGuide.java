// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSendPositionGuide extends SendablePacket<GameClient> {
    private final float x;
    private final float y;
    private final float z;
    private final boolean isLeader;
    private final int sessionId;
    private final int markType;
    private final boolean isPath;

    public SMSendPositionGuide(final int sessionId, final boolean isLeader, final byte markType, final boolean isPath, final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.sessionId = sessionId;
        this.isLeader = isLeader;
        this.isPath = isPath;
        this.markType = markType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.sessionId);
        buffer.writeC(this.markType);
        buffer.writeC(this.isLeader);
        buffer.writeC(this.isPath);
        buffer.writeC(1);
        buffer.writeF(this.x);
        buffer.writeF(this.z);
        buffer.writeF(this.y);
        buffer.writeC(0);
    }
}
