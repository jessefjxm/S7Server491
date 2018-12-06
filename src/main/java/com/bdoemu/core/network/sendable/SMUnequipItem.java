package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMUnequipItem extends WriteItemInfo {
    private final int invSlot;
    private final int equipSlot;
    private final EItemStorageLocation storageType;
    private final Item item;
    private final Player player;
    private final Playable target;

    public SMUnequipItem(final Player player, final Playable target, final int invSlot, final int equipSlot, final Item item) {
        this.storageType = EItemStorageLocation.Inventory;
        this.player = player;
        this.target = target;
        this.invSlot = invSlot;
        this.equipSlot = equipSlot;
        this.item = item;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeD(this.target.getGameObjectId());
        buffer.writeC(0);
        buffer.writeD(this.target.getEquipSlotCacheCount());
        final ServantEquipOnOff servantEquipOnOff = this.player.getServantController().getServantEquipOnOff();
        buffer.writeD(servantEquipOnOff.getVehicleEquipOnOff());
        buffer.writeD(servantEquipOnOff.getCamelEquipOnOff());
        buffer.writeD(servantEquipOnOff.getUnk1EquipOnOff());
        buffer.writeD(servantEquipOnOff.getUnk2EquipOnOff());
        buffer.writeD(servantEquipOnOff.getShipEquipOnOff());
        buffer.writeD(0);
        buffer.writeC(this.equipSlot);
        buffer.writeC(this.storageType.getId());
        buffer.writeC(this.invSlot);
        this.writeItemInfo(buffer, this.item);
    }
}
