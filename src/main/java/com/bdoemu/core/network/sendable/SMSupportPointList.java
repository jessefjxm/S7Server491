// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSupportPointList extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(4);
        buffer.writeH(0);
        buffer.writeH(1);
        buffer.writeH(1);
        buffer.writeD(106);
        buffer.writeD(0);
        buffer.writeH(1);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeD(157);
        buffer.writeD(0);
        buffer.writeH(2);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeD(106);
        buffer.writeD(0);
        buffer.writeH(3);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeD(1);
        buffer.writeD(0);
    }
}
