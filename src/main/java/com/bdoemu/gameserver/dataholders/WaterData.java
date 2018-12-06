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

@Reloadable(name = "water", group = "sqlite")
@StartupComponent("Data")
public class WaterData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) WaterData.class);
        instance = new AtomicReference<Object>();
    }

    private final Map<Integer, RegionDropT> templates;

    private WaterData() {
        this.templates = new HashMap<Integer, RegionDropT>();
        this.load();
    }

    public static WaterData getInstance() {
        Object value = WaterData.instance.get();
        if (value == null) {
            synchronized (WaterData.instance) {
                value = WaterData.instance.get();
                if (value == null) {
                    final WaterData actualValue = new WaterData();
                    value = ((actualValue == null) ? WaterData.instance : actualValue);
                    WaterData.instance.set(value);
                }
            }
        }
        return (WaterData) ((value == WaterData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("WaterData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Water_Table'")) {
            while (rs.next()) {
                final RegionDropT template = new RegionDropT(rs);
                this.templates.put(template.getColor().getRGB(), template);
            }
            WaterData.log.info("Loaded {} WaterTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            WaterData.log.error("Failed load WaterT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public RegionDropT getTemplate(final int rgb) {
        return this.templates.get(rgb);
    }
}
