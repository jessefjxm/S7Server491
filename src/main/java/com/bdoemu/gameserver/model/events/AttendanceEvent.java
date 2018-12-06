package com.bdoemu.gameserver.model.events;

import com.bdoemu.gameserver.model.events.templates.AttendanceT;
import com.bdoemu.gameserver.service.GameTimeService;

import java.time.ZonedDateTime;

public class AttendanceEvent {
    private AttendanceT attendanceT;
    private boolean isEnabled;
    private ZonedDateTime startDate;
    private long eventPeriod;

    public AttendanceEvent(final AttendanceT attendanceT, final boolean isEnabled, final ZonedDateTime startDate, final long eventPeriod) {
        this.attendanceT = attendanceT;
        this.isEnabled = isEnabled;
        this.startDate = startDate;
        this.eventPeriod = eventPeriod;
    }

    public long getEventPeriod() {
        return this.eventPeriod;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public boolean isEnabled() {
        return this.isEnabled && GameTimeService.getServerTimeInMillis() < this.startDate.toInstant().toEpochMilli() + this.eventPeriod;
    }

    public AttendanceT getAttendanceT() {
        return this.attendanceT;
    }
}
