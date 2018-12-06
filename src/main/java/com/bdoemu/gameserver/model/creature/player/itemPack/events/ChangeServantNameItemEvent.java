// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMChangeServantName;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ChangeServantNameItemEvent extends AItemEvent {
    private long servantObjId;
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private Servant servant;
    private String name;

    public ChangeServantNameItemEvent(final Player player, final String name, final long servantObjId, final int slotIndex, final EItemStorageLocation storageLocation, final int regionId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.name = name;
        this.servantObjId = servantObjId;
        this.slotIndex = slotIndex;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setName(this.name);
        this.player.sendPacket(new SMChangeServantName(this.name, this.servantObjId));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories()) {
            return false;
        }
        this.servant = this.player.getServantController().getServant(this.servantObjId);
        if (this.servant == null || !this.servant.getServantState().isStable()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct();
    }
}
