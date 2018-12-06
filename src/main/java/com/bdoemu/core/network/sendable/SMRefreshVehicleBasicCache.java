// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMRefreshVehicleBasicCache extends SendablePacket<GameClient> {
    private final Servant servant;

    public SMRefreshVehicleBasicCache(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeQ((this.servant.getObjectId() < 0L) ? 0L : this.servant.getObjectId());
        buffer.writeD(this.servant.getBasicCacheCount());
        buffer.writeS((CharSequence) this.servant.getName(), 62);
        buffer.writeQ(this.servant.getOwner().getAccountId());
        buffer.writeQ(this.servant.getOwner().getGuildCache());
        buffer.writeS((CharSequence) this.servant.getOwner().getFamily(), 62);
        buffer.writeQ(this.servant.getOwner().getObjectId());
        buffer.writeS((CharSequence) this.servant.getOwner().getName(), 62);
        buffer.writeC(0);
    }
}
