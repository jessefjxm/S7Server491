// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.events.AttendancePlayerEvent;

public class SMAttendanceReward extends SendablePacket<GameClient> {
    private AttendancePlayerEvent attendancePlayerEvent;

    public SMAttendanceReward(final AttendancePlayerEvent attendancePlayerEvent) {
        this.attendancePlayerEvent = attendancePlayerEvent;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.attendancePlayerEvent.getAttendanceEventId());
        buffer.writeD(this.attendancePlayerEvent.getAttendanceRewardedCount());
    }
}
