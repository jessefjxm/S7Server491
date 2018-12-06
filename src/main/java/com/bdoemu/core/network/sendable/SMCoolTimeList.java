// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.cooltimes.CoolTime;

import java.util.Collection;

public class SMCoolTimeList extends SendablePacket<GameClient> {
    private final Collection<CoolTime> coolTimes;

    public SMCoolTimeList(final Collection<CoolTime> coolTimes) {
        this.coolTimes = coolTimes;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(0);
        buffer.writeH(0);
        buffer.writeH(1);
        buffer.writeD(0);
        buffer.writeH(this.coolTimes.size());
        for (final CoolTime time : this.coolTimes) {
            buffer.writeH(time.getType());
            buffer.writeH(time.getSkillId());
            buffer.writeH(time.getReuseGroup());
            buffer.writeQ(time.getEndTime());
        }
    }
}
