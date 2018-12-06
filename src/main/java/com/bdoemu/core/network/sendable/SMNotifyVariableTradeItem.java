// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMNotifyVariableTradeItem extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(0);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeD(2000000);
        buffer.writeH(-22087);
        buffer.writeH(-1);
        buffer.writeC(57);
        buffer.writeC(1);
        buffer.writeC(1);
        buffer.writeC(0);
    }
}
