// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMDetectCharacter extends SendablePacket<GameClient> {
    private Player detectedPlayer;

    public SMDetectCharacter(final Player detectedPlayer) {
        this.detectedPlayer = detectedPlayer;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeF((this.detectedPlayer != null) ? this.detectedPlayer.getLocation().getX() : 0.0);
        buffer.writeF((this.detectedPlayer != null) ? this.detectedPlayer.getLocation().getZ() : 0.0);
        buffer.writeF((this.detectedPlayer != null) ? this.detectedPlayer.getLocation().getY() : 0.0);
        buffer.writeC(this.detectedPlayer != null);
    }
}
