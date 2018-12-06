// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMRefreshPcBasicCache extends SendablePacket<GameClient> {
    private final Player player;

    public SMRefreshPcBasicCache(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.player.getObjectId());
        buffer.writeD(this.player.getBasicCacheCount());
        buffer.writeS((CharSequence) this.player.getName(), 62);
        buffer.writeC(this.player.getPlayerAppearance().getFace().getId());
        buffer.writeC(this.player.getPlayerAppearance().getHairs().getId());
        buffer.writeC(this.player.getPlayerAppearance().getFace().getBeard().getId());
        buffer.writeC(this.player.getPlayerAppearance().getFace().getMustache().getId());
        buffer.writeC(this.player.getPlayerAppearance().getFace().getWhiskers().getId());
        buffer.writeC(this.player.getPlayerAppearance().getFace().getEyebrows().getId());
    }
}
