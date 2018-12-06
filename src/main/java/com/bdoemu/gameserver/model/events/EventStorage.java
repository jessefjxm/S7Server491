package com.bdoemu.gameserver.model.events;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMAttendance;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.events.services.AttendanceService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.concurrent.ConcurrentHashMap;

public class EventStorage extends JSONable {
    private final ConcurrentHashMap<Integer, AttendancePlayerEvent> attendanceEvents;
    private final Player player;

    public EventStorage(final Player player) {
        this.attendanceEvents = new ConcurrentHashMap<>();
        this.player = player;
    }

    public EventStorage(final Player player, final BasicDBObject dbObject) {
        this.attendanceEvents = new ConcurrentHashMap<>();
        this.player = player;
        final BasicDBList basicDBList = (BasicDBList) dbObject.getOrDefault("attendanceEvents", (Object) new BasicDBList());
        for (final Object object : basicDBList) {
            final AttendancePlayerEvent attendancePlayerEvent = new AttendancePlayerEvent((BasicDBObject) object);
            if (AttendanceService.getInstance().isActiveEvent(attendancePlayerEvent)) {
                this.attendanceEvents.put(attendancePlayerEvent.getAttendanceEventId(), attendancePlayerEvent);
            }
        }
        for (final AttendanceEvent attendanceEvent : AttendanceService.getInstance().getAttendanceEvents()) {
            if (!this.attendanceEvents.containsKey(attendanceEvent.getAttendanceT().getEventId()) && attendanceEvent.isEnabled()) {
                this.attendanceEvents.put(attendanceEvent.getAttendanceT().getEventId(), new AttendancePlayerEvent(attendanceEvent.getAttendanceT(), attendanceEvent.getStartDate().toInstant().toEpochMilli()));
            }
        }
    }

    public void onLogin() {
        this.player.sendPacket(new SMAttendance(this.attendanceEvents.values()));
    }

    public AttendancePlayerEvent getAttendanceEvent(final int eventId) {
        return this.attendanceEvents.get(eventId);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        final BasicDBList basicDBList = new BasicDBList();
        for (final AttendancePlayerEvent attendancePlayerEvent : this.attendanceEvents.values()) {
            basicDBList.add(attendancePlayerEvent.toDBObject());
        }
        builder.append("attendanceEvents", basicDBList);
        return builder.get();
    }
}
