// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.theme.templates.ThemeTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "theme", group = "sqlite")
@StartupComponent("Data")
public class ThemeData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ThemeData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ThemeTemplate> templates;

    private ThemeData() {
        this.templates = new HashMap<Integer, ThemeTemplate>();
        this.load();
    }

    public static ThemeData getInstance() {
        Object value = ThemeData.instance.get();
        if (value == null) {
            synchronized (ThemeData.instance) {
                value = ThemeData.instance.get();
                if (value == null) {
                    final ThemeData actualValue = new ThemeData();
                    value = ((actualValue == null) ? ThemeData.instance : actualValue);
                    ThemeData.instance.set(value);
                }
            }
        }
        return (ThemeData) ((value == ThemeData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ThemeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Theme_Table'")) {
            while (rs.next()) {
                final ThemeTemplate template = new ThemeTemplate(rs);
                this.templates.put(template.getThemeId(), template);
            }
            ThemeData.log.info("Loaded {} ThemeTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ThemeData.log.error("Failed load ThemeTemplate", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ThemeTemplate getTemplate(final int themeId) {
        return this.templates.get(themeId);
    }
}
