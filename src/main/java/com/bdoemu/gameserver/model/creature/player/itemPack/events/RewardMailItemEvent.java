// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.receivable.CMHelpRewardMail;
import com.bdoemu.core.network.sendable.SMHelpRewardMail;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RewardMailItemEvent extends AItemEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) RewardMailItemEvent.class);
    }

    private EItemStorageLocation storageType;
    private int slotIndex;
    private String name;
    private String mailSubject;
    private String mailMessage;
    private Long accountId;
    private Item sendItem;

    public RewardMailItemEvent(final Player player, final EItemStorageLocation storageType, final int slotIndex, final String name, final String mailSubject, final String mailMessage) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageType = storageType;
        this.slotIndex = slotIndex;
        this.name = name;
        this.mailSubject = mailSubject;
        this.mailMessage = mailMessage;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMHelpRewardMail());
        MailService.getInstance().sendMail(this.accountId, this.player.getAccountId(), this.name, this.mailSubject, this.mailMessage, this.sendItem);
    }

    @Override
    public boolean canAct() {
        if (this.name.isEmpty()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoDbCharacterNotExist, CMHelpRewardMail.class));
            return false;
        }
        final ItemPack pack = (this.storageType == EItemStorageLocation.Inventory) ? this.playerInventory : this.cashInventory;
        final Item item = pack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType type = item.getTemplate().getContentsEventType();
        if (type == null || !type.isHelpReward()) {
            return false;
        }
        this.accountId = PlayersDBCollection.getInstance().getAccountId(this.name);
        if (this.accountId == null) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoDbCharacterNotExist, CMHelpRewardMail.class));
            return false;
        }
        if (this.accountId == this.player.getAccountId()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoCantDoToMyself, CMHelpRewardMail.class));
            return false;
        }
        final Integer mainGroupId = item.getTemplate().getContentsEventParam1();
        if (mainGroupId == null) {
            return false;
        }
        final List<ItemSubGroupT> drops = ItemMainGroupService.getItems(this.player, mainGroupId);
        if (drops.isEmpty()) {
            RewardMailItemEvent.log.warn("HelpReward dropIsEmpty for itemId: " + item.getItemId());
            return false;
        }
        final ItemSubGroupT drop = drops.get(0);
        this.sendItem = new Item(drop.getItemId(), Rnd.get(drop.getMinCount(), drop.getMaxCount()), drop.getEnchantLevel());
        this.decreaseItem(this.slotIndex, 1L, this.storageType);
        return super.canAct();
    }
}
