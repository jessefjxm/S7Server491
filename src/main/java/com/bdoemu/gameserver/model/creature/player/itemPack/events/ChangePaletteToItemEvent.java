// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMUpdatePalette;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;

public class ChangePaletteToItemEvent extends AItemEvent {
    private int dyeItemId;
    private long dyeItemCount;

    public ChangePaletteToItemEvent(final Player player, final int dyeItemId, final long dyeItemCount) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.dyeItemId = dyeItemId;
        this.dyeItemCount = dyeItemCount;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMUpdatePalette(this.dyeItemId, this.dyeItemCount, false, false));
    }

    @Override
    public boolean canAct() {
        this.addItem(ItemData.getInstance().getItemTemplate(this.dyeItemId), this.dyeItemCount, 0);
        return super.canAct() && this.player.getDyeStorage().removeDye(this.dyeItemId, this.dyeItemCount);
    }
}
