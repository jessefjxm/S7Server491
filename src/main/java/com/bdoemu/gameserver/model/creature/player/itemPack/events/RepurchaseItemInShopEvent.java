package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMRepurchaseItems;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collections;

public class RepurchaseItemInShopEvent extends AItemEvent {
    private long count;
    private int itemId;
    private int enchantLevel;
    private int endurance;
    private Npc npc;
    private Item repurchaseItem;
    private EItemStorageLocation srcStorageLocation;

    public RepurchaseItemInShopEvent(final Player player, final EItemStorageLocation srcStorageLocation, final int itemId, final int enchantLevel, final int endurance, final long count, final Npc npc) {
        super(player, player, player, EStringTable.eErrNoItemRepurchaseFromShop, EStringTable.eErrNoItemRepurchaseFromShop, npc.getRegionId());
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.endurance = endurance;
        this.count = count;
        this.npc = npc;
        this.srcStorageLocation = srcStorageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.playerBag.getRepurchaseList().remove(this.repurchaseItem);
        this.player.sendPacket(new SMRepurchaseItems(Collections.singletonList(this.repurchaseItem), EPacketTaskType.Remove, this.npc.getGameObjectId()));
    }

    @Override
    public boolean canAct() {
        for (final Item rItem : this.playerBag.getRepurchaseList()) {
            if (rItem.getItemId() == this.itemId && rItem.getEnchantLevel() == this.enchantLevel && rItem.getEndurance() == this.endurance && rItem.getCount() == this.count) {
                this.repurchaseItem = rItem;
                break;
            }
        }
        if (this.repurchaseItem == null) {
            return false;
        }
        this.decreaseItem(0, this.repurchaseItem.getItemPrice() * this.repurchaseItem.getCount(), this.srcStorageLocation);
        this.addItem(new Item(this.repurchaseItem, this.repurchaseItem.getCount()));
        return super.canAct();
    }
}
