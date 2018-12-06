package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMUpdateGuardGauge extends SendablePacket<GameClient> {
    private int type;
    private int min;
    private int max;
    private int mod;

    public SMUpdateGuardGauge(int type, double min, double max, double mod) {
        this.type = type;
        this.min = (int) min;
        this.max = (int) max;
        this.mod = (int) mod;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type);
        buffer.writeD(this.min);
        buffer.writeD(this.mod);
        buffer.writeD(this.max);
    }
}
