// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;
import com.bdoemu.gameserver.service.LocalWarService;

public class SMJoinLocalWar extends SendablePacket<GameClient> {
    private Player player;
    private LocalWarStatus localWarStatus;

    public SMJoinLocalWar(final Player player) {
        this.player = player;
        this.localWarStatus = LocalWarService.getInstance().getLocalWarStatus();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getPVPController().getLocalWarTeamType().ordinal());
        buffer.writeD(this.localWarStatus.getYellowTeamPoints());
        buffer.writeD(this.localWarStatus.getRedTeamPoints());
        buffer.writeC(1);
        buffer.writeQ(this.localWarStatus.getEndLocalWarDate());
    }
}
