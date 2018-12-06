package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMBroadcastRegisterWorkerAtAuction extends SendablePacket<GameClient> {
    private int _workerNpcId;
    private long _workerPrice;

    public SMBroadcastRegisterWorkerAtAuction(int workerNpcId, long workerPrice) {
        _workerNpcId = workerNpcId;
        _workerPrice = workerPrice;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(_workerNpcId);
        buffer.writeQ(_workerPrice);
    }
}
