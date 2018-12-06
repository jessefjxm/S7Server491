// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;

import java.util.Collection;

public class SMAddAppliedActiveBuffs extends SendablePacket<GameClient> {
    private final Collection<ActiveBuff> buffs;

    public SMAddAppliedActiveBuffs(final Collection<ActiveBuff> buffs) {
        this.buffs = buffs;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.buffs.size());
        for (final ActiveBuff activeBuff : this.buffs) {
            buffer.writeD(activeBuff.getTargetGameObjId());
            buffer.writeD(activeBuff.getGameObjectId());
            buffer.writeD(Integer.MIN_VALUE);
            buffer.writeH(activeBuff.getBuffId());
            buffer.writeQ(activeBuff.getEndTime());
            buffer.writeH(1);
            buffer.writeH(0);
            buffer.writeC(0);
        }
    }
}
