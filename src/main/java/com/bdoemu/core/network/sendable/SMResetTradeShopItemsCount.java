package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMResetTradeShopItemsCount extends SendablePacket<GameClient> {
    private int _npcSessionId;
    private int _itemId;
    private int _enchantLevel;

    public SMResetTradeShopItemsCount(int npcSessionId, int itemId, int enchantLevel) {
        _npcSessionId = npcSessionId;
        _itemId = itemId;
        _enchantLevel = enchantLevel;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(_npcSessionId);
        buffer.writeH(_itemId);
        buffer.writeH(_enchantLevel);
    }
}
