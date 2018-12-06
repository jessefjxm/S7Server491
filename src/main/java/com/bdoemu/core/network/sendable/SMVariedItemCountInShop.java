package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;

import java.util.Collection;

public class SMVariedItemCountInShop extends SendablePacket<GameClient> {
    private Collection<Item> _item;
    private Player _player;

    public SMVariedItemCountInShop(Player player, Collection<Item> item) {
        _player = player;
        _item   = item;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(0);
        buffer.writeD(_player.getGameObjectId());
        buffer.writeH(_item.size());
        for (Item item : _item)
        {
            buffer.writeH(item.getItemId());
            buffer.writeH(item.getEnchantLevel());
            buffer.writeQ(item.getCount());
            buffer.writeQ(0); // expire time for fishes
            buffer.writeQ(item.getItemPrice());
        }
    }
}
