// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class DeleteItemEvent extends AItemEvent {
    private int slotIndex;
    private long count;
    private EItemStorageLocation storageType;
    private String paymentPassword;

    public DeleteItemEvent(final Player player, final int slotIndex, final long count, final EItemStorageLocation storageType, final String paymentPassword) {
        super(player, player, player, EStringTable.eErrNoServantCantUpdateToMemory, EStringTable.eErrNoServantCantUpdateToMemory, 0);
        this.slotIndex = slotIndex;
        this.count = count;
        this.storageType = storageType;
        this.paymentPassword = paymentPassword;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (this.slotIndex < 2 || !this.storageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageType);
        final Item item = itemPack.getItem(this.slotIndex);
        if (item == null || item.getTemplate().getNeedContribute() != null) {
            return false;
        }
        this.decreaseItem(this.slotIndex, this.count, this.storageType);
        if (this.storageType.isCashInventory()) {
            final String currentPassword = this.player.getAccountData().getPaymentPin();
            if (this.paymentPassword.isEmpty() || currentPassword.isEmpty() || !this.paymentPassword.equals(currentPassword)) {
                return false;
            }
        }
        return super.canAct();
    }
}
