// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.PlantExchangeGroupT;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.PlantZoneWorkingT;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.WaypointTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import com.bdoemu.gameserver.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "waypoint", group = "sqlite")
@StartupComponent("Data")
public class WaypointDataOld implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) WaypointDataOld.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, WaypointTemplate> templates;

    private WaypointDataOld() {
        this.templates = new HashMap<Integer, WaypointTemplate>();
        this.load();
    }

    public static WaypointDataOld getInstance() {
        Object value = WaypointDataOld.instance.get();
        if (value == null) {
            synchronized (WaypointDataOld.instance) {
                value = WaypointDataOld.instance.get();
                if (value == null) {
                    final WaypointDataOld actualValue = new WaypointDataOld();
                    value = ((actualValue == null) ? WaypointDataOld.instance : actualValue);
                    WaypointDataOld.instance.set(value);
                }
            }
        }
        return (WaypointDataOld) ((value == WaypointDataOld.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("WaypointData Loading");
        final HashMap<Integer, PlantExchangeGroupT> plantExchangeTemplates = new HashMap<Integer, PlantExchangeGroupT>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PlantExchangeGroup_Table'")) {
            while (rs.next()) {
                final PlantExchangeGroupT template = new PlantExchangeGroupT(rs);
                plantExchangeTemplates.put(rs.getInt("Index"), template);
            }
            WaypointDataOld.log.info("Loaded {} PlantExchangeGroupTemplate's", (Object) plantExchangeTemplates.size());
        } catch (SQLException ex) {
            WaypointDataOld.log.error("Failed load PlantExchangeGroupT", (Throwable) ex);
        }
        final HashMap<Integer, PlantZoneWorkingT> plantZoneTemplates = new HashMap<Integer, PlantZoneWorkingT>();
        try (final Connection con2 = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement2 = con2.createStatement();
             final ResultSet rs2 = statement2.executeQuery("SELECT * FROM 'PlantZoneWorking_Table'")) {
            while (rs2.next()) {
                final PlantZoneWorkingT template2 = new PlantZoneWorkingT(rs2, plantExchangeTemplates.get(rs2.getInt("ExchangeGroupIndex")));
                plantZoneTemplates.put(rs2.getInt("WaypointKey"), template2);
            }
            WaypointDataOld.log.info("Loaded {} PlantZoneWorkingTemplate's", plantZoneTemplates.size());
        } catch (SQLException ex2) {
            WaypointDataOld.log.error("Failed load PlantZoneWorkingT", ex2);
        }
        try (final Connection con2 = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement2 = con2.createStatement();
             final ResultSet rs2 = statement2.executeQuery("SELECT * FROM 'Explore_Table'")) {
            while (rs2.next()) {
                final WaypointTemplate template3 = new WaypointTemplate(rs2, plantZoneTemplates.get(rs2.getInt("WaypointKey")));
                this.templates.put(template3.getWaypointKey(), template3);
            }
            WaypointDataOld.log.info("Loaded {} WaypointTemplate's", (Object) this.templates.size());
        } catch (SQLException ex2) {
            WaypointDataOld.log.error("Failed load WaypointTemplate", (Throwable) ex2);
        }
    }

    public void reload() {
        this.load();
    }

    public WaypointTemplate getTemplate(final int waypointKey) {
        return this.templates.get(waypointKey);
    }

    public WaypointTemplate getNearestTemplate(final double x, final double y, final double z) {
        WaypointTemplate nearestTemplate = null;
        for (final WaypointTemplate template : this.templates.values()) {
            if (nearestTemplate == null) {
                nearestTemplate = template;
            } else {
                if (MathUtils.getDistance(x, y, z, template.getX(), template.getY(), template.getZ()) >= MathUtils.getDistance(x, y, z, nearestTemplate.getX(), nearestTemplate.getY(), nearestTemplate.getZ())) {
                    continue;
                }
                nearestTemplate = template;
            }
        }
        return nearestTemplate;
    }
}
