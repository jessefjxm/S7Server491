// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "teleport", group = "sqlite")
@StartupComponent("Data")
public class TeleportData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) TeleportData.class);
        instance = new AtomicReference<Object>();
    }

    private EnumMap<ETeleportType, Map<Integer, Location>> table;

    private TeleportData() {
        this.table = new EnumMap<ETeleportType, Map<Integer, Location>>(ETeleportType.class);
        this.load();
    }

    public static TeleportData getInstance() {
        Object value = TeleportData.instance.get();
        if (value == null) {
            synchronized (TeleportData.instance) {
                value = TeleportData.instance.get();
                if (value == null) {
                    final TeleportData actualValue = new TeleportData();
                    value = ((actualValue == null) ? TeleportData.instance : actualValue);
                    TeleportData.instance.set(value);
                }
            }
        }
        return (TeleportData) ((value == TeleportData.instance) ? null : value);
    }

    public void load() {
        ServerInfoUtils.printSection("TeleportData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Teleport_Table'")) {
            while (rs.next()) {
                final ETeleportType teleportType = ETeleportType.values()[rs.getInt("Type")];
                final int index = rs.getInt("Index");
                final Location location = new Location(rs.getDouble("PositionX"), rs.getDouble("PositionZ"), rs.getDouble("PositionY"));
                this.table.computeIfAbsent(teleportType, k -> new HashMap()).put(index, location);
            }
            TeleportData.log.info("Loaded {} ContentsGroupOptionData's", (Object) this.table.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            TeleportData.log.error("Failed load ContentsGroupOptionData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Location getTeleportLocation(final ETeleportType teleportType, final int index) {
        final Map<Integer, Location> teleportData = this.table.get(teleportType);
        if (teleportData != null) {
            return teleportData.get(index);
        }
        return null;
    }
}
