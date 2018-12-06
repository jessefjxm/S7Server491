// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;

public class SMStartActionNak extends SendablePacket<GameClient> {
    private EStringTable message;
    private Creature owner;

    public SMStartActionNak(final Creature owner, final EStringTable message) {
        this.owner = owner;
        this.message = message;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.message.getHash());
        buffer.writeD(this.owner.getActionStorage().getActionHash());
        buffer.writeC(0);
    }
}
