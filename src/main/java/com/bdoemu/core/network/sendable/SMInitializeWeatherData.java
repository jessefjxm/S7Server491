package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

import java.util.Collection;

public class SMInitializeWeatherData extends SendablePacket<GameClient> {
    public static int MAXIMUM;

    static {
        SMInitializeWeatherData.MAXIMUM = 1356;
    }

    private final Collection<float[]> data;
    private final long _lastWeatherUpdate;

    public SMInitializeWeatherData(final long lastWeatherUpdate, final Collection<float[]> weatherData) {
        this._lastWeatherUpdate = lastWeatherUpdate;
        this.data = weatherData;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this._lastWeatherUpdate);
        buffer.writeH(this.data.size());
        for (final float[] data : this.data) {
            buffer.writeF(data[0]);
            buffer.writeF(data[1]);
            buffer.writeF(data[2]);
        }
    }
}
