// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMBroadcastGetItem;
import com.bdoemu.core.network.sendable.SMOpenItemBox;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.dataholders.xml.OpenBox31Data;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemGetType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.OpenBox31T;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OpenItemBoxEvent extends AItemEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) OpenItemBoxEvent.class);
    }

    private EItemStorageLocation storageType;
    private byte type;
    private int slotIndex;
    private Item itemBox;

    public OpenItemBoxEvent(final Player player, final EItemStorageLocation storageType, final int slotIndex, final byte type) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageType = storageType;
        this.slotIndex = slotIndex;
        this.type = type;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        for (final Item addItem : this.addTasks) {
            this.player.sendPacket(new SMOpenItemBox(addItem.getItemId(), addItem.getEnchantLevel()));
            if (addItem.getTemplate().isNotifyWorld()) {
                World.getInstance().broadcastWorldPacket(new SMBroadcastGetItem(EItemGetType.OPEN_BOX, this.player.getName(), addItem.getItemId(), addItem.getEnchantLevel(), this.itemBox.getItemId()));
            }
        }
    }

    @Override
    public boolean canAct() {
        if (!this.storageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack decreasePack = (this.storageType == EItemStorageLocation.Inventory) ? this.playerInventory : this.cashInventory;
        this.itemBox = decreasePack.getItem(this.slotIndex);
        if (this.itemBox == null) {
            return false;
        }
        final EContentsEventType eventType = this.itemBox.getTemplate().getContentsEventType();
        if (eventType == null || !eventType.isItemBox()) {
            return false;
        }
        final Integer index = this.itemBox.getTemplate().getContentsEventParam1();
        if (index == null) {
            return false;
        }
        if (this.type == 0) {
            final ItemExchangeSourceT exchangeTemplate = ItemExchangeSourceData.getInstance().getTemplate(index);
            if (exchangeTemplate == null) {
                return false;
            }
            final List<ItemSubGroupT> items = ItemMainGroupService.getItems(this.player, exchangeTemplate.getItemDropId());
            for (final ItemSubGroupT itemSubGroup : items) {
                this.addItem(itemSubGroup.getItemId(), Rnd.get(itemSubGroup.getMinCount(), itemSubGroup.getMaxCount()), itemSubGroup.getEnchantLevel());
            }
        } else {
            if (this.type != 1) {
                return false;
            }
            final List<OpenBox31T> boxes = OpenBox31Data.getInstance().getTemplate(this.itemBox.getItemId(), this.player.getClassType());
            if (boxes == null) {
                return false;
            }
            for (final OpenBox31T box : boxes) {
                if (Rnd.getChance(box.getSelectRate(), 10000)) {
                    if (ItemData.getInstance().getItemTemplate(box.getItemKey()) == null) {
                        OpenItemBoxEvent.log.warn("OpenItemBoxEvent null for itemId: {} ", (Object) box.getItemKey());
                        return false;
                    }
                    this.addItem(box.getItemKey(), Rnd.get(box.getMinCount(), box.getMaxCount()), box.getEnchantLevel());
                    break;
                }
            }
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageType);
        if (this.addTasks == null || this.addTasks.isEmpty()) {
            OpenItemBoxEvent.log.warn("OpenItemBoxEvent missed Drop for itemId: {} ", (Object) this.itemBox.getItemId());
            return false;
        }
        return super.canAct();
    }
}
