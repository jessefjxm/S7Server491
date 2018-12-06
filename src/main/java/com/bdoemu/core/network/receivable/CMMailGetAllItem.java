package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MailGetSpecificItemEvent;

public class CMMailGetAllItem extends ReceivablePacket<GameClient> {
    private long _mailObjectId;
    private long _attachedItemObjectId;

    public CMMailGetAllItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _mailObjectId         = readQ();
        _attachedItemObjectId = readQ();
    }

    public void runImpl() {
        final Player player = (this.getClient()).getPlayer();
        if (player != null) {
            if (player.getRegion().getTemplate().isSafe()) {
                player.getPlayerBag().onEvent(new MailGetSpecificItemEvent(player, _mailObjectId, _attachedItemObjectId));
            } else {
                player.sendPacket(new SMNak(EStringTable.eErrNoIsntSafeZone, this.opCode));
            }
        }
    }
}