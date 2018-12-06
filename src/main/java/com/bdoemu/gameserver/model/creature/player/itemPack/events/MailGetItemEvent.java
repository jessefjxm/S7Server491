// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMMailGetItem;
import com.bdoemu.gameserver.databaseCollections.MailsDBCollection;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.CashProductT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class MailGetItemEvent extends AItemEvent {
    private long id;
    private long count;
    private Mail mail;
    private EItemStorageLocation storageLocation;

    public MailGetItemEvent(final Player player, final long id, final long count, final EItemStorageLocation storageLocation) {
        super(player, player, player, EStringTable.eErrNoItemTakeFromQuest, EStringTable.eErrNoItemTakeFromQuest, player.getRegionId());
        this.id = id;
        this.count = count;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMMailGetItem(this.id, this.count));
        this.mail.unsetItem();
        MailsDBCollection.getInstance().update(this.mail);
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        this.mail = this.player.getMailBox().getMailMap().get(this.id);
        if (this.mail == null || this.count <= 0L || this.count > this.mail.getCount()) {
            return false;
        }
        final Item mailItem = this.mail.getItem();
        final BuyCashItem buyCashItem = this.mail.getBuyCashItem();
        if (this.mail.getItemType() == 1) {
            if (buyCashItem == null) {
                return false;
            }
            for (final CashProductT product : buyCashItem.getCashItemT().getProducts()) {
                final ItemTemplate productTemplate = ItemData.getInstance().getItemTemplate(product.getItemId());
                if (!productTemplate.isStack()) {
                    for (int i = 0; i < this.count; ++i) {
                        this.addItem(productTemplate, 1L, 0);
                    }
                } else {
                    this.addItem(productTemplate, product.getCount() * this.count, 0);
                }
            }
        } else {
            if (mailItem == null) {
                return false;
            }
            final Item task = new Item(mailItem, this.count);
            if (this.storageLocation.isWarehouse()) {
                this.addWHItem(task);
            } else {
                this.addItem(task);
            }
        }
        return super.canAct() && ((mailItem == null) ? buyCashItem.addCount(-this.count) : mailItem.addCount(-this.count));
    }
}
