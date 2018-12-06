package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMMailGetItem;
import com.bdoemu.core.network.sendable.SMMailRemove;
import com.bdoemu.gameserver.databaseCollections.MailsDBCollection;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.CashProductT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

/**
 * @author Nullbyte
 */
public class MailGetSpecificItemEvent extends AItemEvent {
    private Player _player;
    private Mail _mail;
    private long _mailObjectId;
    private long _itemObjectId;
    private long _itemCount;

    public MailGetSpecificItemEvent(Player player, long mailObjectId, long itemObjectId) {
        super(player, player, player, EStringTable.eErrNoItemTakeFromQuest, EStringTable.eErrNoItemTakeFromQuest, player.getRegionId());

        _player         = player;
        _mail           = null;
        _mailObjectId   = mailObjectId;
        _itemObjectId   = itemObjectId;
        _itemCount      = 0;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        _mail.unsetItem();
        MailsDBCollection.getInstance().update(_mail);
        _player.sendPacketNoFlush(new SMMailGetItem(_itemObjectId, _itemCount));
    }

    @Override
    public boolean canAct() {
        // Validate if mail exist
        _mail = this.player.getMailBox().getMailMap().get(_mailObjectId);
        if (_mail == null)
            return false;

        // Validate if we are selecting the correct item
        if (_mail.getItem() == null || _mail.getItem().getObjectId() != _itemObjectId)
            return false;

        Item mailItem = _mail.getItem();
        BuyCashItem buyCashItem = _mail.getBuyCashItem();
        if (_mail.getItemType() == 1) {
            if (buyCashItem == null) {
                return false;
            }
            for (final CashProductT product : buyCashItem.getCashItemT().getProducts()) {
                final ItemTemplate productTemplate = ItemData.getInstance().getItemTemplate(product.getItemId());
                if (!productTemplate.isStack()) {
                    for (int i = 0; i < mailItem.getCount(); ++i)
                        this.addItem(productTemplate, 1L, 0);
                } else
                    this.addItem(productTemplate, product.getCount() * _mail.getCount(), 0);
            }
        } else {
            if (mailItem == null)
                return false;
            final Item task = new Item(mailItem, _mail.getCount());
            this.addWHItem(task);
        }

        _itemCount = mailItem.getCount();
        return super.canAct() && mailItem.addCount(-_mail.getCount());
    }
}
