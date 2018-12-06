package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.items.ShopItem;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class SMGetTradeShopItems extends SendablePacket<GameClient> {
    private final Npc npc;
    private Collection<ShopItem> items;
    private EPacketTaskType packetTaskType;
    private AtomicInteger buyIndex;
    private AtomicInteger sellIndex;
    private int totalSize;
    private int item, tradeShopType;

    public SMGetTradeShopItems(final Npc npc, final int totalSize, final Collection<ShopItem> items, final EPacketTaskType packetTaskType, final AtomicInteger buyIndex, final AtomicInteger sellIndex) {
        this.npc = npc;
        this.items = items;
        this.totalSize = totalSize;
        this.packetTaskType = packetTaskType;
        this.buyIndex = buyIndex;
        this.sellIndex = sellIndex;
    }

    public SMGetTradeShopItems(final Npc npc, final int totalSize, final Collection<ShopItem> items, final EPacketTaskType packetTaskType, final AtomicInteger buyIndex, final AtomicInteger sellIndex, final int item, final int tradeShopType) {
        this.npc = npc;
        this.items = items;
        this.totalSize = totalSize;
        this.packetTaskType = packetTaskType;
        this.buyIndex = buyIndex;
        this.sellIndex = sellIndex;
        this.item = item;
        this.tradeShopType = tradeShopType;
    }
    
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.npc.getGameObjectId());
        buffer.writeH(this.npc.getCreatureId());
        buffer.writeH(0);
        buffer.writeH(this.items.size());
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeD(this.totalSize);
        buffer.writeC(tradeShopType); // if type == 2, make the loop search ONLY for the itemId given over all trade item subgroups
        buffer.writeC(1); // trading type: 0=none 1=normal 2=imperial
        for (final ShopItem shopItem : this.items) {
            ItemTemplate tpl = ItemData.getInstance().getItemTemplate(shopItem.getItemSubGroup().getItemId());

            buffer.writeH(shopItem.getItemSubGroup().getItemId());
            buffer.writeH(shopItem.getItemSubGroup().getEnchantLevel());
            buffer.writeD(tpl.getOriginalPrice()); // original price
            buffer.writeD(shopItem.getItemSubGroup().getNeedAmountForStock());
            buffer.writeD(shopItem.getItemSubGroup().getMinRemainAmountForStock());
            buffer.writeD(shopItem.getItemSubGroup().getMaxRemainAmountForStock());
            buffer.writeD(shopItem.getItemSubGroup().getVariedAmountPerTickForStock());
            buffer.writeD(shopItem.getItemSubGroup().getReverseAmountForStock());
            buffer.writeH(0);
            buffer.writeC(0);
            buffer.writeC(1);
            buffer.writeC(-1); // 55(velia) / 120 (loggia farm)
            buffer.writeC(shopItem.getItemSubGroup().isBuyItem());
            buffer.writeH(shopItem.getItemSubGroup().isBuyItem() ? this.buyIndex.getAndIncrement() : this.sellIndex.getAndIncrement());
			buffer.writeQ(-1L); // itemExpireTime
            buffer.writeQ(1L); // inStockCount
            buffer.writeC(0); // if inStockCount == 0, than this is 1 else 0
			// price chart?
            buffer.writeD(tpl.getOriginalPrice());
            buffer.writeD(tpl.getOriginalPrice() + 500);
            buffer.writeD(tpl.getOriginalPrice() + 500);
            buffer.writeD(tpl.getOriginalPrice() + 600);
            buffer.writeD(tpl.getOriginalPrice() + 700);
            buffer.writeD(tpl.getOriginalPrice() + 500);
            buffer.writeD(tpl.getOriginalPrice() + 800);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 300);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeD(tpl.getOriginalPrice() + 400);
            buffer.writeH(0);
        }
    }
}
