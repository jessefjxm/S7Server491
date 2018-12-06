// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.manufactures.templates.ManufactureConditionT;
import com.bdoemu.gameserver.model.manufactures.templates.ManufactureT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "manufacture", group = "sqlite")
@StartupComponent("Data")
public class ManufactureData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ManufactureData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<String, ManufactureConditionT> conditionTemplates;
    private final HashMap<String, List<ManufactureT>> templates;

    private ManufactureData() {
        this.conditionTemplates = new HashMap<String, ManufactureConditionT>();
        this.templates = new HashMap<String, List<ManufactureT>>();
        this.load();
    }

    public static ManufactureData getInstance() {
        Object value = ManufactureData.instance.get();
        if (value == null) {
            synchronized (ManufactureData.instance) {
                value = ManufactureData.instance.get();
                if (value == null) {
                    final ManufactureData actualValue = new ManufactureData();
                    value = ((actualValue == null) ? ManufactureData.instance : actualValue);
                    ManufactureData.instance.set(value);
                }
            }
        }
        return (ManufactureData) ((value == ManufactureData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ManufactureData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ManufactureCondition_Table'")) {
            while (rs.next()) {
                final ManufactureConditionT template = new ManufactureConditionT(rs);
                this.conditionTemplates.put(template.getActionName(), template);
            }
            ManufactureData.log.info("Loaded {} ManufactureConditionTemplate's", (Object) this.conditionTemplates.size());
        } catch (SQLException ex) {
            ManufactureData.log.error("Failed load ManufactureConditionT", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Manufacture_Table'")) {
            while (rs.next()) {
                final String actionName = rs.getString("ActionName");
                final ManufactureT template2 = new ManufactureT(rs);
                this.templates.computeIfAbsent(actionName, k -> new ArrayList());
                this.templates.get(actionName).add(template2);
            }
            ManufactureData.log.info("Loaded {} ManufactureTemplate's", (Object) this.templates.values().stream().mapToInt(List::size).sum());
        } catch (SQLException ex) {
            ManufactureData.log.error("Failed load ManufactureT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ManufactureT getTemplate(final String manufactureName, final Collection<Item> items) {
        for (final ManufactureT template : this.templates.get(manufactureName)) {
            if (items.size() != template.getItems().size()) {
                continue;
            }
            boolean found = true;
            for (final Item item : items) {
                if (!template.getItems().containsKey(item.getItemId())) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return template;
            }
        }
        return null;
    }
}
