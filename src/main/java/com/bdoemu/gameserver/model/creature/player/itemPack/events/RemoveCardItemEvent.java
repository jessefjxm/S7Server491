// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class RemoveCardItemEvent extends AItemEvent {
    private int cardId;
    private EItemStorageLocation storageLocation;
    private int slotIndex;

    public RemoveCardItemEvent(final Player player, final int cardId, final EItemStorageLocation storageLocation, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.cardId = cardId;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.getMentalCardHandler().removeMentalCard(this.cardId);
    }

    @Override
    public boolean canAct() {
        if (this.storageLocation == null) {
            return false;
        }
        final ItemPack itemPack = this.player.getPlayerBag().getItemPack(this.storageLocation);
        if (itemPack == null) {
            return false;
        }
        final Item item = itemPack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct() && contentsEventType != null && contentsEventType.isRemoveKnowledge() && this.player.getMentalCardHandler().containsCard(this.cardId);
    }
}
