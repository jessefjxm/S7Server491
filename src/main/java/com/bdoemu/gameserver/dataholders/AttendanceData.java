package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.events.templates.AttendanceT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "attendance", group = "sqlite")
@StartupComponent("Data")
public class AttendanceData implements IReloadable {
    private static class Holder {
        static final AttendanceData INSTANCE = new AttendanceData();
    }
    private static final Logger log = LoggerFactory.getLogger(AttendanceData.class);
    private final HashMap<Integer, AttendanceT> templates;

    private AttendanceData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static AttendanceData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("Attendance_TableData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Attendance_Table'")) {
            while (rs.next()) {
                final AttendanceT attendanceT = new AttendanceT(rs);
                this.templates.put(attendanceT.getEventId(), attendanceT);
            }
        } catch (SQLException ex) {
            AttendanceData.log.error("Failed load AttendanceT", ex);
        }
        AttendanceData.log.info("Loaded {} AttendanceT templates", this.templates.size());
    }

    public void reload() {
        this.load();
    }

    public AttendanceT getTemplate(final int eventId) {
        return this.templates.get(eventId);
    }
}
