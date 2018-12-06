// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMImprintServant;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ImprintServantItemEvent extends AItemEvent {
    private Servant servant;
    private int slotIndex;
    private EItemStorageLocation itemStorageLocation;
    private boolean result;

    public ImprintServantItemEvent(final Player player, final Servant servant, final EItemStorageLocation itemStorageLocation, final int slotIndex, final boolean result, final int regionId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToImprintServant, EStringTable.eErrNoItemIsRemovedToImprintServant, regionId);
        this.result = result;
        this.servant = servant;
        this.itemStorageLocation = itemStorageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setImprint(this.result);
        this.player.sendPacket(new SMImprintServant(this.servant));
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
        if (contentsEventType == null || (this.result && !contentsEventType.isImprintServant()) || (!this.result && !contentsEventType.isReleaseImprintServant())) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.itemStorageLocation);
        return super.canAct() && this.servant.isImprint() != this.result;
    }
}
