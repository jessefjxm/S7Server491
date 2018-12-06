// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMFishingTime extends SendablePacket<GameClient> {
    private long startTime;
    private long endTime;
    private int captchaType;

    public SMFishingTime(final long startTime, final long endTime, final int captchaType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.captchaType = captchaType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.startTime);
        buffer.writeQ(this.endTime);
        buffer.writeH(this.captchaType);
    }
}
