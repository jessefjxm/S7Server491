// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.world.Location;

public class SMPlayerKill extends SendablePacket<GameClient> {
    private int type;
    private int killerId;
    private String ownerName;
    private String killerName;
    private Location location;

    public SMPlayerKill(final int type, final String ownerName, final String killerName, final int killerId, final Location location) {
        this.type = type;
        this.ownerName = ownerName;
        this.killerName = killerName;
        this.location = location;
        this.killerId = killerId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type);
        buffer.writeD(0);
        buffer.writeS((CharSequence) this.ownerName, 62);
        buffer.writeS((CharSequence) this.killerName, 62);
        buffer.writeQ(0L);
        buffer.writeQ(0L);
        buffer.writeF(this.location.getX());
        buffer.writeF(this.location.getZ());
        buffer.writeF(this.location.getY());
        buffer.writeB(new byte[42]);
        buffer.writeF(this.location.getX());
        buffer.writeF(this.location.getZ());
        buffer.writeF(this.location.getY());
        buffer.writeC(0);
        buffer.writeH(this.killerId);
    }
}
