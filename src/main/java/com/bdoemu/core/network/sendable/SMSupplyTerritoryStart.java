package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSupplyTerritoryStart extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(0);
        buffer.writeH(11);
        buffer.writeH(111);
        buffer.writeH(212);
        buffer.writeH(311);
        buffer.writeH(411);
        buffer.writeH(514);
        buffer.writeH(614);
    }
}
