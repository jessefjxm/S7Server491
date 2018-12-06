package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMNotifyPvpBattleGround extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(System.currentTimeMillis() / 1000L);
    }
}
