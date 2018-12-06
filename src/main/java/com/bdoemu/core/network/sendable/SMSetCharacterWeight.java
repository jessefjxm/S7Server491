// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Playable;

public class SMSetCharacterWeight extends SendablePacket<GameClient> {
    private Playable playable;

    public SMSetCharacterWeight(final Playable playable) {
        this.playable = playable;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.playable.getGameObjectId());
        buffer.writeQ(this.playable.getGameStats().getWeight().getLongValue());
        buffer.writeQ(this.playable.getGameStats().getWeight().getLongMaxValue());
    }
}
