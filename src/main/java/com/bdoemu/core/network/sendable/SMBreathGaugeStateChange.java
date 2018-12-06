// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMBreathGaugeStateChange extends SendablePacket<GameClient> {
    private int gameObjId;
    private int type;

    public SMBreathGaugeStateChange(final int gameObjId, final int type) {
        this.gameObjId = gameObjId;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.gameObjId);
        buffer.writeD(this.type);
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
    }
}
