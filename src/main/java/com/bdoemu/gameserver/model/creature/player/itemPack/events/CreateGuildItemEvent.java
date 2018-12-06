// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CreateGuildItemEvent extends AItemEvent {
    public CreateGuildItemEvent(final Player player) {
        super(player, player, player, EStringTable.eErrNoGuildNotExist, EStringTable.eErrNoGuildNotExist, 0);
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        this.decreaseItem(0, 100000L, EItemStorageLocation.Inventory);
        return super.canAct();
    }
}
