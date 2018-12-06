// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.misc.enums.EInstantCashType;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "instantcashitem", group = "sqlite")
@StartupComponent("Data")
public class InstantCashItemData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static final EnumMap<EInstantCashType, TreeMap<Integer, Integer>> table;

    static {
        log = LoggerFactory.getLogger((Class) InstantCashItemData.class);
        instance = new AtomicReference<Object>();
        table = new EnumMap<EInstantCashType, TreeMap<Integer, Integer>>(EInstantCashType.class);
    }

    private InstantCashItemData() {
        this.load();
    }

    public static InstantCashItemData getInstance() {
        Object value = InstantCashItemData.instance.get();
        if (value == null) {
            synchronized (InstantCashItemData.instance) {
                value = InstantCashItemData.instance.get();
                if (value == null) {
                    final InstantCashItemData actualValue = new InstantCashItemData();
                    value = ((actualValue == null) ? InstantCashItemData.instance : actualValue);
                    InstantCashItemData.instance.set(value);
                }
            }
        }
        return (InstantCashItemData) ((value == InstantCashItemData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("InstantCashItemData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'InstantCashItem'")) {
            while (rs.next()) {
                final EInstantCashType type = EInstantCashType.values()[rs.getInt("InstantType")];
                final int seconds = rs.getInt("Second");
                final int pearlCount = rs.getInt("Pearl");
                if (InstantCashItemData.table.containsKey(type)) {
                    InstantCashItemData.table.get(type).put(seconds, pearlCount);
                } else {
                    final TreeMap<Integer, Integer> typeMap = new TreeMap<Integer, Integer>();
                    typeMap.put(seconds, pearlCount);
                    InstantCashItemData.table.put(type, typeMap);
                }
            }
            InstantCashItemData.log.info("Loaded {} InstantCashItemData's", (Object) InstantCashItemData.table.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            InstantCashItemData.log.error("Exception while loading InstantCashItemData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Integer getPearlCountForInstantEnd(final EInstantCashType instantCashType, final int secondsToFinish) {
        final TreeMap<Integer, Integer> data = InstantCashItemData.table.get(instantCashType);
        if (data == null) {
            return null;
        }
        for (final Map.Entry<Integer, Integer> entry : data.entrySet()) {
            if (secondsToFinish <= entry.getKey()) {
                return entry.getValue();
            }
        }
        return data.lastEntry().getValue();
    }
}
