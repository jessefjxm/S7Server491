package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMAskInviteParty extends SendablePacket<GameClient> {
    private final String invitorName;
    private final int gameObjId;

    public SMAskInviteParty(final String invitorName, final int gameObjId) {
        this.invitorName = invitorName;
        this.gameObjId = gameObjId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS(this.invitorName, 62);
        buffer.writeD(this.gameObjId);
        buffer.writeC(0);
    }
}
