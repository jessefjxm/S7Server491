// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AttendanceItemEvent;

public class CMAttendanceReward extends ReceivablePacket<GameClient> {
    private int eventId;

    public CMAttendanceReward(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.eventId = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new AttendanceItemEvent(player, this.eventId));
        }
    }
}
