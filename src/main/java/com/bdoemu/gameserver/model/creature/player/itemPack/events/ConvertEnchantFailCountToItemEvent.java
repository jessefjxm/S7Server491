// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMConvertEnchantFailCountToItem;
import com.bdoemu.gameserver.dataholders.EnchantFailCountOptionData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ConvertEnchantFailCountToItemEvent extends AItemEvent {
    private EItemStorageLocation storageType;
    private byte type;
    private int slotIndex;
    private int failCounts;

    public ConvertEnchantFailCountToItemEvent(final Player player, final EItemStorageLocation storageType, final byte type, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageType = storageType;
        this.type = type;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.setEnchantFailCount(this.failCounts);
        this.player.sendPacket(new SMConvertEnchantFailCountToItem(this.failCounts));
    }

    @Override
    public boolean canAct() {
        if (!this.storageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack pack = this.playerBag.getItemPack(this.storageType);
        if (pack == null) {
            return false;
        }
        final Item item = pack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType eventType = item.getTemplate().getContentsEventType();
        if (eventType == null || !eventType.isEnchantFail()) {
            return false;
        }
        switch (this.type) {
            case 0: {
                this.failCounts = EnchantFailCountOptionData.getInstance().getTemplate(item.getItemId());
                if (this.player.getEnchantFailCount() > 0) {
                    return false;
                }
                this.decreaseItem(this.slotIndex, 1L, this.storageType);
                return super.canAct();
            }
            default: {
                return false;
            }
        }
    }
}
