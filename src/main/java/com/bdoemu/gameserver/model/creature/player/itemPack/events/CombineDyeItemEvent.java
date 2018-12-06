// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.network.sendable.SMCombineDyeItem;
import com.bdoemu.gameserver.dataholders.DyeingItemData;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CombineDyeItemEvent extends AItemEvent {
    private static DyeingItemData instance;

    static {
        CombineDyeItemEvent.instance = DyeingItemData.getInstance();
    }

    private int slot1;
    private int slot2;
    private EItemStorageLocation storageType1;
    private EItemStorageLocation storageType2;
    private Item item1;
    private Item item2;
    private Item addTask;

    public CombineDyeItemEvent(final Player player, final int slot1, final int slot2, final EItemStorageLocation storageType1, final EItemStorageLocation storageType2) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.storageType1 = storageType1;
        this.storageType2 = storageType2;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMCombineDyeItem(this.addTask.getSlotIndex(), this.cashInventory.getLocationType()));
    }

    @Override
    public boolean canAct() {
        if (!this.storageType1.isPlayerInventories() || !this.storageType2.isPlayerInventories()) {
            return false;
        }
        this.item1 = this.playerBag.getItemPack(this.storageType1).getItem(this.slot1);
        this.item2 = this.playerBag.getItemPack(this.storageType2).getItem(this.slot2);
        if (this.slot1 == this.slot2 || this.item1 == null || this.item2 == null || CombineDyeItemEvent.instance.getTemplate(this.item1.getItemId()) == null || CombineDyeItemEvent.instance.getTemplate(this.item2.getItemId()) == null) {
            return false;
        }
        this.addTask = this.addItem(ItemData.getInstance().getItemTemplate(EtcOptionConfig.DYEING_COMBINE_ITEM), 1L, 0);
        this.decreaseItem(this.slot1, 1L, this.storageType1);
        this.decreaseItem(this.slot2, 1L, this.storageType2);
        return super.canAct();
    }
}
