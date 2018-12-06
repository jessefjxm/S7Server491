// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMSkillAwakenReset;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SkillAwakenResetItemEvent extends AItemEvent {
    private int skillId;
    private int slotIndex;

    public SkillAwakenResetItemEvent(final Player player, final int skillId, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.skillId = skillId;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMSkillAwakenReset(this.skillId));
    }

    @Override
    public boolean canAct() {
        final Item item = this.cashInventory.getItem(this.slotIndex);
        if (item == null || item.getItemId() != 18040) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, EItemStorageLocation.CashInventory);
        return super.canAct() && this.player.getSkillList().removeAwaken(this.skillId);
    }
}
