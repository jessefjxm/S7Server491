package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMGetTradeShopItemsCount extends SendablePacket<GameClient> {

    private int _npcSessionId;
    private int _itemId;
    private int _enchantLevel;
    private long _count;

    public SMGetTradeShopItemsCount(int npcSessionId, int itemId, int enchantLevel, long count) {
        _npcSessionId = npcSessionId;
        _itemId = itemId;
        _enchantLevel = enchantLevel;
        _count = count;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(_npcSessionId);
        buffer.writeH(0);
        buffer.writeH(_itemId);
        buffer.writeH(_enchantLevel);
        buffer.writeQ(_count);
    }
}
