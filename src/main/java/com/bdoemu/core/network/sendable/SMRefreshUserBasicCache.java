// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMRefreshUserBasicCache extends SendablePacket<GameClient> {
    private final Player player;

    public SMRefreshUserBasicCache(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.player.getAccountId());
        buffer.writeD(this.player.getAccountData().getUserBasicCacheCount());
        buffer.writeS((CharSequence) this.player.getFamily(), 62);
        buffer.writeS((CharSequence) this.player.getAccountData().getComment(), 402);
    }
}
