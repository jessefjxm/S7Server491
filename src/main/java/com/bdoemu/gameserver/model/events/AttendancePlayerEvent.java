package com.bdoemu.gameserver.model.events;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.AttendanceData;
import com.bdoemu.gameserver.model.events.services.AttendanceService;
import com.bdoemu.gameserver.model.events.templates.AttendanceT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class AttendancePlayerEvent extends JSONable {
    private long lastAttendanceRewardedDate;
    private long startEventDate;
    private int attendanceRewardedCount;
    private int attendanceRewardCount;
    private AttendanceT attendanceT;
    private AttendanceEvent attendanceEvent;

    public AttendancePlayerEvent(final AttendanceT attendanceT, final long startEventDate) {
        this.attendanceT = attendanceT;
        this.startEventDate = startEventDate;
        this.attendanceEvent = AttendanceService.getInstance().getAttendanceEvent(attendanceT.getEventId());
        this.updateEvent();
    }

    public AttendancePlayerEvent(final BasicDBObject dbObject) {
        final int attendanceEventId = dbObject.getInt("attendanceEventId");
        this.attendanceRewardCount = dbObject.getInt("attendanceRewardCount");
        this.attendanceRewardedCount = dbObject.getInt("attendanceRewardedCount");
        this.lastAttendanceRewardedDate = dbObject.getLong("lastAttendanceRewardedDate");
        this.startEventDate = dbObject.getLong("startEventDate");
        this.attendanceT = AttendanceData.getInstance().getTemplate(attendanceEventId);
        this.attendanceEvent = AttendanceService.getInstance().getAttendanceEvent(this.attendanceT.getEventId());
        this.updateEvent();
    }

    private final void updateEvent() {
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.lastAttendanceRewardedDate), TimeZone.getDefault().toZoneId());
        final LocalDate localTime = localDateTime.toLocalDate();
        final LocalDate currentTime = LocalDate.now();
        if (this.attendanceRewardedCount < this.attendanceT.getRewards().length && (localTime.getDayOfYear() != currentTime.getDayOfYear() || localTime.getYear() != currentTime.getYear())) {
            this.lastAttendanceRewardedDate = GameTimeService.getServerTimeInMillis();
            ++this.attendanceRewardCount;
        }
    }

    public boolean isEnabledEvent() {
        return this.attendanceEvent.isEnabled();
    }

    public AttendanceT getAttendanceT() {
        return this.attendanceT;
    }

    public int getAttendanceEventId() {
        return this.attendanceT.getEventId();
    }

    public int getAttendanceRewardCount() {
        return this.attendanceRewardCount;
    }

    public void setAttendanceRewardCount(final int attendanceRewardCount) {
        this.attendanceRewardCount = attendanceRewardCount;
    }

    public int getAttendanceRewardedCount() {
        return this.attendanceRewardedCount;
    }

    public void setAttendanceRewardedCount(final int attendanceRewardedCount) {
        this.attendanceRewardedCount = attendanceRewardedCount;
    }

    public long getLastAttendanceRewardedDate() {
        return this.lastAttendanceRewardedDate;
    }

    public void setLastAttendanceRewardedDate(final long lastAttendanceRewardedDate) {
        this.lastAttendanceRewardedDate = lastAttendanceRewardedDate;
    }

    public long getStartEventDate() {
        return this.startEventDate;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("attendanceEventId", this.attendanceT.getEventId());
        builder.append("attendanceRewardCount", this.attendanceRewardCount);
        builder.append("attendanceRewardedCount", this.attendanceRewardedCount);
        builder.append("lastAttendanceRewardedDate", this.lastAttendanceRewardedDate);
        builder.append("startEventDate", this.startEventDate);
        return builder.get();
    }
}
