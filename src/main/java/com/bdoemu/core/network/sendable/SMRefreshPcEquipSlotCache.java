// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerEquipments;

public class SMRefreshPcEquipSlotCache extends WriteItemInfo {
    private final Player player;

    public SMRefreshPcEquipSlotCache(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.player.getObjectId());
        buffer.writeD(this.player.getEquipSlotCacheCount());
        final PlayerEquipments equipment = this.player.getPlayerBag().getEquipments();
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.rightHand.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.leftHand.getId()));
        this.writeEquipData(buffer, null);
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.chest.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.glove.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.boots.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.helm.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.lantern.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarChest.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarGlove.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarBoots.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarHelm.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarWeapon.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarSubWeapon.getId()));
        this.writeEquipData(buffer, null);
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.avatarUnderWear.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.faceDecoration1.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.faceDecoration2.getId()));
        this.writeEquipData(buffer, equipment.getItem(EEquipSlot.faceDecoration3.getId()));
    }
}
