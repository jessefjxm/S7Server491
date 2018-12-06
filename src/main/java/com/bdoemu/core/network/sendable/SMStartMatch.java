// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.PVPController;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;

public class SMStartMatch extends SendablePacket<GameClient> {
    private Player player;

    public SMStartMatch(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final PVPController pvpController = this.player.getPVPController();
        buffer.writeC(pvpController.getPvpMatchState().getId());
        final PvpMatch pvpMatch = this.player.getPVPController().getPvpMatch();
        buffer.writeD((pvpMatch != null) ? pvpMatch.getMatchObjectId() : 0);
        buffer.writeC((pvpMatch != null) ? pvpMatch.getPvpType().getId() : 0);
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(pvpController.getObjectId());
    }
}
