// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMPopJewelFromSocket;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.Jewel;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class PopJewelFromSocketEvent extends AItemEvent {
    private EItemStorageLocation itemStorageType;
    private EItemStorageLocation extractStorageType;
    private int itemSlot;
    private int jewelIndex;
    private int extractSlotIndex;
    private Item item;
    private Long extractItemCount;

    public PopJewelFromSocketEvent(final Player player, final int jewelIndex, final EItemStorageLocation itemStorageType, final int itemSlot, final EItemStorageLocation extractStorageType, final int extractSlotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.jewelIndex = jewelIndex;
        this.itemStorageType = itemStorageType;
        this.extractStorageType = extractStorageType;
        this.extractSlotIndex = extractSlotIndex;
        this.itemSlot = itemSlot;
    }

    @Override
    public void onEvent() {
        int type = 1;
        if (this.addTasks != null) {
            super.onEvent();
            type = 0;
        }
        this.item.removeJewel(this.jewelIndex);
        final byte[] sockets = new byte[6];
        sockets[this.jewelIndex] = 1;
        this.player.sendPacket(new SMPopJewelFromSocket(type, this.itemStorageType, this.itemSlot, sockets));
    }

    @Override
    public boolean canAct() {
        if (this.jewelIndex < 0 || this.jewelIndex > 6) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.itemStorageType);
        if (itemPack == null || this.itemSlot > itemPack.getExpandSize()) {
            return false;
        }
        if (this.itemSlot < itemPack.getDefaultSlotIndex()) {
            return false;
        }
        this.item = itemPack.getItem(this.itemSlot);
        if (this.item == null) {
            return false;
        }
        final Jewel jewel = this.item.getJewelsByIndex(this.jewelIndex);
        if (jewel == null) {
            return false;
        }
        final ItemTemplate jewelTemplate = jewel.getTemplate();
        if (this.extractSlotIndex > 0) {
            final int[] extractItemIds = jewelTemplate.getJewelSubtractionNeedItem();
            this.extractItemCount = jewelTemplate.getJewelSubtractionNeedItemCount();
            final ItemPack extractItemPack = this.playerBag.getItemPack(this.extractStorageType);
            if (extractItemPack != null) {
                final Item extractItem = extractItemPack.getItem(this.extractSlotIndex);
                if (extractItem != null) {
                    final int[] array = extractItemIds;
                    final int length = array.length;
                    int i = 0;
                    while (i < length) {
                        final int itemId = array[i];
                        if (extractItem.getItemId() == itemId) {
                            if (extractItem.getCount() < this.extractItemCount) {
                                return false;
                            }
                            this.addItem(jewelTemplate.getItemId(), 1L, 0);
                            this.decreaseItem(this.extractSlotIndex, this.extractItemCount, this.extractStorageType);
                            break;
                        } else {
                            ++i;
                        }
                    }
                }
            }
        }
        return super.canAct();
    }
}
