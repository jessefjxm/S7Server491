// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.world.region.templates.RegionGroupTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "regiongroup", group = "sqlite")
@StartupComponent("Data")
public class RegionGroupData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static Map<Integer, RegionGroupTemplate> templates;

    static {
        log = LoggerFactory.getLogger((Class) RegionGroupData.class);
        instance = new AtomicReference<Object>();
        RegionGroupData.templates = new HashMap<Integer, RegionGroupTemplate>();
    }

    private RegionGroupData() {
        this.load();
    }

    public static RegionGroupData getInstance() {
        Object value = RegionGroupData.instance.get();
        if (value == null) {
            synchronized (RegionGroupData.instance) {
                value = RegionGroupData.instance.get();
                if (value == null) {
                    final RegionGroupData actualValue = new RegionGroupData();
                    value = ((actualValue == null) ? RegionGroupData.instance : actualValue);
                    RegionGroupData.instance.set(value);
                }
            }
        }
        return (RegionGroupData) ((value == RegionGroupData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("RegionGroupData Loading");
        this.loadTable();
    }

    private void loadTable() {
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM RegionGroup_Table")) {
            while (rs.next()) {
                final RegionGroupTemplate template = new RegionGroupTemplate(rs);
                RegionGroupData.templates.put(template.getRegionGroupKey(), template);
            }
            RegionGroupData.log.info("Loaded {} RegionGroupData's", (Object) RegionGroupData.templates.size());
        } catch (SQLException ex) {
            RegionGroupData.log.error("Failed load RegionGroupData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Collection<RegionGroupTemplate> getTemplates() {
        return RegionGroupData.templates.values();
    }
}
