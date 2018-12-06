// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.world.templates.RegionDropT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "underground", group = "sqlite")
@StartupComponent("Data")
public class UndergroundData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) UndergroundData.class);
        instance = new AtomicReference<Object>();
    }

    private final Map<Integer, RegionDropT> templates;

    private UndergroundData() {
        this.templates = new HashMap<Integer, RegionDropT>();
        this.load();
    }

    public static UndergroundData getInstance() {
        Object value = UndergroundData.instance.get();
        if (value == null) {
            synchronized (UndergroundData.instance) {
                value = UndergroundData.instance.get();
                if (value == null) {
                    final UndergroundData actualValue = new UndergroundData();
                    value = ((actualValue == null) ? UndergroundData.instance : actualValue);
                    UndergroundData.instance.set(value);
                }
            }
        }
        return (UndergroundData) ((value == UndergroundData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("UndergroundData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Underground_Table'")) {
            while (rs.next()) {
                final RegionDropT template = new RegionDropT(rs);
                this.templates.put(template.getColor().getRGB(), template);
            }
            UndergroundData.log.info("Loaded {} UndergroundTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            UndergroundData.log.error("Failed load UndergroundT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public RegionDropT getTemplate(final int rgb) {
        return this.templates.get(rgb);
    }
}
