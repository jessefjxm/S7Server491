// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMClearServantDeadCount;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ClearServantDeadCountItemEvent extends AItemEvent {
    private Servant servant;
    private int slotIndex;
    private EItemStorageLocation itemStorageLocation;

    public ClearServantDeadCountItemEvent(final Player player, final Servant servant, final EItemStorageLocation itemStorageLocation, final int slotIndex, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToClearServantDeadCount, EStringTable.eErrNoItemIsRemovedToClearServantDeadCount, regionId);
        this.servant = servant;
        this.itemStorageLocation = itemStorageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setClearedDeathCount(true);
        this.servant.setDeathCount(0);
        this.player.sendPacket(new SMClearServantDeadCount(this.servant));
    }

    @Override
    public boolean canAct() {
        if (!this.itemStorageLocation.isPlayerInventories()) {
            return false;
        }
        final Item item = this.playerBag.getItemPack(this.itemStorageLocation).getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isClearServantDeadCount()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.itemStorageLocation);
        return super.canAct() && this.servant.getDeathCount() > 0 && !this.servant.isClearedDeathCount();
    }
}
