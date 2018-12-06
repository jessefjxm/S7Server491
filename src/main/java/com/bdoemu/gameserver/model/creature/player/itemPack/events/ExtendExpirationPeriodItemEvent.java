// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMExtendExpirationPeriod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.service.GameTimeService;

public class ExtendExpirationPeriodItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private EItemStorageLocation chargeStorageLocation;
    private int slotIndex;
    private int chargeSlotIndex;
    private Item item;
    private long expirationPeriod;

    public ExtendExpirationPeriodItemEvent(final Player player, final EItemStorageLocation storageLocation, final int slotIndex, final EItemStorageLocation chargeStorageLocation, final int chargeSlotIndex) {
        super(player, player, player, EStringTable.eErrNoExtendExpirationPeriod, EStringTable.eErrNoExtendExpirationPeriod, player.getRegionId());
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.chargeStorageLocation = chargeStorageLocation;
        this.chargeSlotIndex = chargeSlotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.item.setExpirationPeriod(this.expirationPeriod);
        this.player.sendPacket(new SMExtendExpirationPeriod(this.item));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories() || !this.chargeStorageLocation.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageLocation);
        this.item = itemPack.getItem(this.slotIndex);
        if (this.item == null) {
            return false;
        }
        if (this.item.getRemainingExpirationPeriod() > this.item.getTemplate().getExpirationPeriod() * 60) {
            return false;
        }
        final EContentsEventType contentsEventType = this.item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isLantern()) {
            return false;
        }
        final ItemPack chargeItemPack = this.playerBag.getItemPack(this.chargeStorageLocation);
        final Item chargeItem = chargeItemPack.getItem(this.chargeSlotIndex);
        if (chargeItem == null || chargeItem.getTemplate().getExpirationPeriodParam2() <= 0L) {
            return false;
        }
        this.decreaseItem(this.chargeSlotIndex, 1L, this.chargeStorageLocation);
        this.expirationPeriod = GameTimeService.getServerTimeInSecond() + this.item.getRemainingExpirationPeriod() + chargeItem.getTemplate().getExpirationPeriodParam2() / 1000L;
        return super.canAct();
    }
}
