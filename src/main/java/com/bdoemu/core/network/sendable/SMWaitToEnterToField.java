// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.mongodb.BasicDBObject;

import java.util.Collection;

public class SMWaitToEnterToField extends SendablePacket<GameClient> {
    private final Collection<BasicDBObject> players;

    public SMWaitToEnterToField(final Collection<BasicDBObject> players) {
        this.players = players;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(-1L);
        buffer.writeQ(0L);
        buffer.writeQ(0L);
        buffer.writeC(this.players.size());
        for (final BasicDBObject player : this.players) {
            buffer.writeQ(player.getLong("_id"));
            buffer.writeQ(-1L);
        }
    }
}
