// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMApplyActiveBuff extends SendablePacket<GameClient> {
    private final ActiveBuff activeBuff;

    public SMApplyActiveBuff(final ActiveBuff activeBuff) {
        this.activeBuff = activeBuff;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.activeBuff.getTargetGameObjId());
        buffer.writeD(this.activeBuff.getGameObjectId());
        buffer.writeD(Integer.MIN_VALUE);
        buffer.writeH(this.activeBuff.getBuffId());
        buffer.writeQ(this.activeBuff.getEndTime());
        buffer.writeH(-1);
        buffer.writeH(1);
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeD(1);
    }
}
