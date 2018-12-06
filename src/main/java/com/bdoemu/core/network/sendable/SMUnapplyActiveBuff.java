// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;

public class SMUnapplyActiveBuff extends SendablePacket<GameClient> {
    private final ActiveBuff activeBuff;

    public SMUnapplyActiveBuff(final ActiveBuff activeBuff) {
        this.activeBuff = activeBuff;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.activeBuff.getTargetGameObjId());
        buffer.writeD(this.activeBuff.getGameObjectId());
        buffer.writeD(Integer.MIN_VALUE);
    }
}
