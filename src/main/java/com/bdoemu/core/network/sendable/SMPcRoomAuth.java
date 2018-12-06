package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

/**
 * @author Nullbyte
 */
public class SMPcRoomAuth extends SendablePacket<GameClient> {
    @Override
    protected void writeBody(SendByteBuffer sendByteBuffer) {
        sendByteBuffer.writeC(1);
    }
}
