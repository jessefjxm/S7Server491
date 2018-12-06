// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "servant", group = "sqlite")
@StartupComponent("Data")
public class ServantData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ServantData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, HashMap<Integer, ServantTemplate>> templates;

    private ServantData() {
        this.templates = new HashMap<Integer, HashMap<Integer, ServantTemplate>>();
        this.load();
    }

    public static ServantData getInstance() {
        Object value = ServantData.instance.get();
        if (value == null) {
            synchronized (ServantData.instance) {
                value = ServantData.instance.get();
                if (value == null) {
                    final ServantData actualValue = new ServantData();
                    value = ((actualValue == null) ? ServantData.instance : actualValue);
                    ServantData.instance.set(value);
                }
            }
        }
        return (ServantData) ((value == ServantData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ServantData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Servant_Table'")) {
            int disabledByContentGroup = 0;
            while (rs.next()) {
                final ServantTemplate template = new ServantTemplate(rs);
                if (template.getGameStatsTemplate() != null) {
                    if (!this.templates.containsKey(template.getId())) {
                        this.templates.put(template.getId(), new HashMap<Integer, ServantTemplate>());
                    }
                    this.templates.get(template.getId()).put(template.getLevel(), template);
                } else {
                    ++disabledByContentGroup;
                }
            }
            ServantData.log.info("Loaded {} ServantTemplate entries ({} disabled by ContentsGroupOptionData)", (Object) this.templates.size(), (Object) disabledByContentGroup);
        } catch (SQLException ex) {
            ServantData.log.error("Failed while loading Servant table.", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ServantTemplate getTemplate(final int id, final int level) {
        if (this.templates.containsKey(id)) {
            return this.templates.get(id).get(level);
        }
        return null;
    }
}
