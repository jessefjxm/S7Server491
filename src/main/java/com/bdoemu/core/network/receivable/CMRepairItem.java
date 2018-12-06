package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RepairItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMRepairItem extends ReceivablePacket<GameClient> {
    private int npcSessionId;
    private int playerSessionId;
    private int slotId;
    private int decreaseSlotId;
    private int artisanSlotId;
    private EItemStorageLocation srcStorageType;
    private EItemStorageLocation decreaseStorageType;
    private EItemStorageLocation moneyStorageType;
    private EItemStorageLocation artisansStorageType;
    private byte repairType;
    private long moneyObjId;
    private long count;

    public CMRepairItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.playerSessionId = this.readD();
        this.srcStorageType = EItemStorageLocation.valueOf(this.readC());
        this.slotId = this.readCD();
        this.readD();
        this.readH();
        this.readC();
        this.moneyStorageType = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjId = this.readQ();
        this.decreaseStorageType = EItemStorageLocation.valueOf(this.readC());
        this.decreaseSlotId = this.readCD();
        this.count = this.readQ();
        this.artisansStorageType = EItemStorageLocation.valueOf(this.readC());
        this.artisanSlotId = this.readC();
        this.repairType = this.readC();
        readQ();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
            if (npc == null) {
                return;
            }
            if (npc.getTemplate().getCreatureFunctionT().isRepair()) {
                player.getPlayerBag().onEvent(new RepairItemEvent(player, this.slotId, this.decreaseSlotId, this.artisanSlotId, this.srcStorageType, this.decreaseStorageType, this.moneyStorageType, this.artisansStorageType, this.repairType, this.count, npc.getRegionId()));
            }
        }
    }
}
