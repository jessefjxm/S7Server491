// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMNotifyLocalWarInfo extends SendablePacket<GameClient> {
    private Player killer;
    private Player player;
    private int points;
    private int redTeamPoints;
    private int yellowTeamPoints;

    public SMNotifyLocalWarInfo(final Player killer, final Player player, final int redTeamPoints, final int yellowTeamPoints, final int points) {
        this.killer = killer;
        this.player = player;
        this.points = points;
        this.redTeamPoints = redTeamPoints;
        this.yellowTeamPoints = yellowTeamPoints;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.killer.getName(), 62);
        buffer.writeS((CharSequence) this.player.getName(), 62);
        buffer.writeD(this.yellowTeamPoints);
        buffer.writeD(this.redTeamPoints);
        buffer.writeD(this.killer.getPVPController().getLocalWarTeamType().ordinal());
        buffer.writeD(this.points);
    }
}
