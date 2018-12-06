package com.bdoemu.gameserver.model.events.services;

import com.bdoemu.core.configs.EventsConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.dataholders.AttendanceData;
import com.bdoemu.gameserver.model.events.AttendanceEvent;
import com.bdoemu.gameserver.model.events.AttendancePlayerEvent;
import com.bdoemu.gameserver.model.events.templates.AttendanceT;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;

@StartupComponent("Service")
public class AttendanceService {
    private final HashMap<Integer, AttendanceEvent> attendanceEvents;

    private AttendanceService() {
        this.attendanceEvents = new HashMap<>();
        for (final String event : EventsConfig.ATTENDANCE_EVENTS) {
            final String[] eventData = event.split(";");
            final int eventId = Integer.parseInt(eventData[0]);
            final AttendanceT attendanceT = AttendanceData.getInstance().getTemplate(eventId);
            if (attendanceT != null) {
                final boolean isEnabled = Boolean.parseBoolean(eventData[1]);
                final ZonedDateTime startDate = ZonedDateTime.parse(eventData[2]);
                if (startDate != null) {
                    final long eventPeriod = Long.parseLong(eventData[3]);
                    this.attendanceEvents.put(attendanceT.getEventId(), new AttendanceEvent(attendanceT, isEnabled, startDate, eventPeriod));
                }
            }
        }
    }

    public static AttendanceService getInstance() {
        return Holder.INSTANCE;
    }

    public AttendanceEvent getAttendanceEvent(final int eventId) {
        return this.attendanceEvents.get(eventId);
    }

    public Collection<AttendanceEvent> getAttendanceEvents() {
        return this.attendanceEvents.values();
    }

    public boolean isActiveEvent(final AttendancePlayerEvent attendancePlayerEvent) {
        final AttendanceEvent attendanceEvent = this.attendanceEvents.get(attendancePlayerEvent.getAttendanceEventId());
        return attendanceEvent != null && attendanceEvent.isEnabled() && attendanceEvent.getStartDate().toInstant().toEpochMilli() == attendancePlayerEvent.getStartEventDate();
    }

    private static class Holder {
        static final AttendanceService INSTANCE = new AttendanceService();
    }
}
