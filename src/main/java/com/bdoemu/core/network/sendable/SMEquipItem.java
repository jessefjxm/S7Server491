package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UnequipItemTask;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SMEquipItem extends WriteItemInfo {
    private final Player player;
    private final Playable target;
    private final int invSlot;
    private final int equipSlot;
    private final EItemStorageLocation storageType;
    private final Item item;
    private final ConcurrentLinkedQueue<UnequipItemTask> itemTasks;
    private long unk;

    public SMEquipItem(final Player player, final Playable target, final Item item, final int invSlot, final int equipSlot, final EItemStorageLocation storageType, final ConcurrentLinkedQueue<UnequipItemTask> itemTasks, final long unk) {
        this.player = player;
        this.target = target;
        this.invSlot = invSlot;
        this.equipSlot = equipSlot;
        this.item = item;
        this.storageType = storageType;
        this.itemTasks = itemTasks;
        this.unk = unk;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.target.getGameObjectId());
        buffer.writeD(this.player.getGameObjectId());
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
        buffer.writeQ(-1L);
        this.writeItemInfo(buffer, this.item);
        buffer.writeH(this.itemTasks.size());
        for (final UnequipItemTask task : this.itemTasks) {
            buffer.writeC(task.getEquipSlot());
            buffer.writeC(task.getLocationType().getId());
            buffer.writeC(task.getUnequipSlot());
            buffer.writeQ(-1L);
            this.writeItemInfo(buffer, task.getItem());
        }
    }
}
