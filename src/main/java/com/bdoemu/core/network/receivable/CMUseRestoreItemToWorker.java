package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UseItemToWorkerEvent;

public class CMUseRestoreItemToWorker extends ReceivablePacket<GameClient> {
    private long objectId;
    private int slotIndex;
    private long count;

    public CMUseRestoreItemToWorker(final short opcode) {
        super(opcode);
    }

    protected void read() {
        slotIndex = readCD();// < Slot No	(Packet: 0 Item: 10)
        objectId = readQ();     // < Worker No	(Packet: 10240000000303820298)
        count = readQ();     // < Item Count (Packet: 1 Item: 1)
    }

    public void runImpl() {
        final Player player = ((GameClient) getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new UseItemToWorkerEvent(player, objectId, slotIndex, count));
        }
    }
}
