// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMRefreshVehicleEquipSlotCache extends WriteItemInfo {
    private Servant servant;

    public SMRefreshVehicleEquipSlotCache(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeD(this.servant.getEquipSlotCacheCount());
        final ServantEquipments equipment = this.servant.getEquipments();
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.chest.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.glove.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.boots.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.helm.getId()));
        this.writeEquipData(buffer, null);
        this.writeEquipData(buffer, null);
        this.writeEquipData(buffer, null);
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.lantern.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.body.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarChest.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarGlove.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarBoots.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarHelm.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarBody.getId()));
    }
}
