// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.Encyclopedia;

public class SMUpdateEncyclopedia extends SendablePacket<GameClient> {
    private Encyclopedia encyclopedia;
    private int currentSize;

    public SMUpdateEncyclopedia(final Encyclopedia encyclopedia, final int currentSize) {
        this.encyclopedia = encyclopedia;
        this.currentSize = currentSize;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.encyclopedia.getKey());
        buffer.writeD(this.encyclopedia.getCount());
        buffer.writeD(this.currentSize);
    }
}
