// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerEquipments;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class SMGetAllEquipSlot extends WriteItemInfo {
    private final PlayerEquipments playerEquipments;

    public SMGetAllEquipSlot(final PlayerEquipments playerEquipments) {
        this.playerEquipments = playerEquipments;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(EPacketTaskType.Add.ordinal());
        for (int index = 0; index < this.playerEquipments.getExpandSize(); ++index) {
            final Item item = this.playerEquipments.getItem(index);
            this.writeItemInfo(buffer, item);
        }
    }
}
