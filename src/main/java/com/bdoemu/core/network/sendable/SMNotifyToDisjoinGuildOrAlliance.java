// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMNotifyToDisjoinGuildOrAlliance extends SendablePacket<GameClient> {
    private final Player player;
    private final int type;

    public SMNotifyToDisjoinGuildOrAlliance(final Player player, final int type) {
        this.player = player;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeD(this.type);
        buffer.writeQ(this.player.getGuildCache());
        buffer.writeQ(0L);
    }
}
