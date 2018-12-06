package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.events.AttendancePlayerEvent;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMAttendance extends SendablePacket<GameClient> {
    private Collection<AttendancePlayerEvent> attendancePlayerEvents;

    public SMAttendance(final Collection<AttendancePlayerEvent> attendancePlayerEvents) {
        this.attendancePlayerEvents = attendancePlayerEvents;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(EPacketTaskType.Add.ordinal());
        buffer.writeC(0);
        buffer.writeH(this.attendancePlayerEvents.size());
        for (final AttendancePlayerEvent attendancePlayerEvent : this.attendancePlayerEvents) {
            buffer.writeH(attendancePlayerEvent.getAttendanceEventId());
            buffer.writeD(attendancePlayerEvent.getAttendanceRewardCount());
            buffer.writeD(attendancePlayerEvent.getAttendanceRewardedCount());
            buffer.writeD(0);
            buffer.writeC(attendancePlayerEvent.isEnabledEvent());
            buffer.writeD(0);
            buffer.writeQ(0L);
            buffer.writeC(0);
        }
    }
}
