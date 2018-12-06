package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMTradeGameStart extends SendablePacket<GameClient> {
    private int _minGoal;
    private int _maxGoal;
    private int _tryCount;
    private int _startDice;

    public SMTradeGameStart(int minGoal, int maxGoal, int tryCount, int startDice) {
        _minGoal = minGoal;
        _maxGoal = maxGoal;
        _tryCount = tryCount;
        _startDice = startDice;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(_minGoal);
        buffer.writeD(_maxGoal);
        buffer.writeD(_tryCount);
        buffer.writeD(_startDice);
    }
}
