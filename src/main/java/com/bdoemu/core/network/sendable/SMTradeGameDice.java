package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.ETradeGameResult;

public class SMTradeGameDice extends SendablePacket<GameClient> {
    private ETradeGameResult _result;
    private int _diceValue;

    public SMTradeGameDice(ETradeGameResult result, int diceValue) {
        _result = result;
        _diceValue = diceValue;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(_diceValue);
        buffer.writeC(_result.ordinal());
    }
}
