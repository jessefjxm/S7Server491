package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMTradeGameEnd extends SendablePacket<GameClient> {
    private boolean _success;

    public SMTradeGameEnd(boolean success) {
        _success = success;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(_success);
    }
}
