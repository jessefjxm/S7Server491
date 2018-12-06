// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.templates.PCSetTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "pcset", group = "sqlite")
@StartupComponent("Data")
public class PCSetData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PCSetData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, PCSetTemplate> templates;

    private PCSetData() {
        this.templates = new HashMap<Integer, PCSetTemplate>();
        this.load();
    }

    public static PCSetData getInstance() {
        Object value = PCSetData.instance.get();
        if (value == null) {
            synchronized (PCSetData.instance) {
                value = PCSetData.instance.get();
                if (value == null) {
                    final PCSetData actualValue = new PCSetData();
                    value = ((actualValue == null) ? PCSetData.instance : actualValue);
                    PCSetData.instance.set(value);
                }
            }
        }
        return (PCSetData) ((value == PCSetData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PCSetData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PC_Set_Table'")) {
            while (rs.next()) {
                final PCSetTemplate template = new PCSetTemplate(rs);
                this.templates.put(template.getPlayerClassId(), template);
            }
            PCSetData.log.info("Loaded {} PCSetTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            PCSetData.log.error("Failed load PCSetTemplate", (Throwable) ex);
        }
    }

    public PCSetTemplate getTemplate(final int classId) {
        return this.templates.get(classId);
    }

    public void reload() {
        this.load();
    }
}
