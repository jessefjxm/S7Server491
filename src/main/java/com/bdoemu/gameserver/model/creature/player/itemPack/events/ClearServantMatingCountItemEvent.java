// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMClearServantMatingCount;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ClearServantMatingCountItemEvent extends AItemEvent {
    private Servant servant;
    private int slotIndex;
    private EItemStorageLocation itemStorageLocation;

    public ClearServantMatingCountItemEvent(final Player player, final Servant servant, final EItemStorageLocation itemStorageLocation, final int slotIndex, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToClearServantMatingCount, EStringTable.eErrNoItemIsRemovedToClearServantMatingCount, regionId);
        this.servant = servant;
        this.itemStorageLocation = itemStorageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setClearedMatingCount(true);
        this.servant.setMatingCount(this.servant.getServantSetTemplate().getMatingCount());
        this.player.sendPacket(new SMClearServantMatingCount(this.servant));
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
        if (contentsEventType == null || !contentsEventType.isClearServantMatingCount()) {
            return false;
        }
        if (this.servant.getServantSetTemplate().isMale() && item.getTemplate().getContentsEventParam1() != 1) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.itemStorageLocation);
        return super.canAct() && !this.servant.isClearedMatingCount();
    }
}
