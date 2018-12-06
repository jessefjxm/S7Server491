// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.templates.PCTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "pc", group = "sqlite")
@StartupComponent("Data")
public class PCData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PCData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, HashMap<Integer, PCTemplate>> templates;

    private PCData() {
        this.templates = new HashMap<Integer, HashMap<Integer, PCTemplate>>();
        this.load();
    }

    public static PCData getInstance() {
        Object value = PCData.instance.get();
        if (value == null) {
            synchronized (PCData.instance) {
                value = PCData.instance.get();
                if (value == null) {
                    final PCData actualValue = new PCData();
                    value = ((actualValue == null) ? PCData.instance : actualValue);
                    PCData.instance.set(value);
                }
            }
        }
        return (PCData) ((value == PCData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PCData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pc_Table'")) {
            while (rs.next()) {
                final PCTemplate template = new PCTemplate(rs);
                this.templates.computeIfAbsent(template.getPlayerClass(), k -> new HashMap());
                this.templates.get(template.getPlayerClass()).put(template.getLevel(), template);
            }
            PCData.log.info("Loaded {} PCTemplate's", (Object) this.templates.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            PCData.log.error("Failed load PCTemplate", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public PCTemplate getTemplate(final int classId, final int level) {
        return this.templates.get(classId).get(level);
    }
}
