// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.Encyclopedia;

import java.util.Collection;

public class SMListEncyclopedia extends SendablePacket<GameClient> {
    private Collection<Encyclopedia> encyclopedies;

    public SMListEncyclopedia(final Collection<Encyclopedia> encyclopedies) {
        this.encyclopedies = encyclopedies;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.encyclopedies.size());
        for (final Encyclopedia encyclopedia : this.encyclopedies) {
            buffer.writeH(encyclopedia.getKey());
            buffer.writeD(encyclopedia.getCount());
            buffer.writeD(encyclopedia.getMaxItemSize());
        }
    }
}
