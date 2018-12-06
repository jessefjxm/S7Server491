// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.dataholders.AlchemyUpgradeData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EAlchemyStoneType;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.AlchemyUpgradeT;

public class AlchemyUpgradeItemEvent extends AItemEvent {
    private EItemStorageLocation stoneStorageLocation;
    private EItemStorageLocation storageLocation;
    private int stoneSlotIndex;
    private int slotIndex;
    private long count;
    private long experience;
    private Item alchemyItem;

    public AlchemyUpgradeItemEvent(final Player player, final EItemStorageLocation stoneStorageLocation, final EItemStorageLocation storageLocation, final int stoneSlotIndex, final int slotIndex, final long count) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.stoneStorageLocation = stoneStorageLocation;
        this.storageLocation = storageLocation;
        this.stoneSlotIndex = stoneSlotIndex;
        this.slotIndex = slotIndex;
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.alchemyItem.upgradeAlchemyStone(this.player, this.experience);
    }

    @Override
    public boolean canAct() {
        if ((!this.storageLocation.isInventory() && !this.storageLocation.isCashInventory()) || (!this.stoneStorageLocation.isInventory() && !this.stoneStorageLocation.isCashInventory())) {
            return false;
        }
        this.alchemyItem = this.playerBag.getItemPack(this.stoneStorageLocation).getItem(this.stoneSlotIndex);
        if (this.alchemyItem == null || this.count <= 0L) {
            return false;
        }
        final EContentsEventType contentsEventType = this.alchemyItem.getTemplate().getContentsEventType();
        if (!contentsEventType.isStone()) {
            return false;
        }
        final Item changeItem = this.playerBag.getItemPack(this.storageLocation).getItem(this.slotIndex);
        if (changeItem == null) {
            return false;
        }
        final AlchemyUpgradeT alchemyUpgradeT = AlchemyUpgradeData.getInstance().getTemplate(changeItem.getItemId());
        if (alchemyUpgradeT == null || alchemyUpgradeT.getStoneType() != EAlchemyStoneType.valueOf(this.alchemyItem.getTemplate().getContentsEventParam1())) {
            return false;
        }
        this.experience = this.count * alchemyUpgradeT.getExp();
        this.decreaseItem(this.slotIndex, this.count, this.storageLocation);
        return super.canAct();
    }
}
