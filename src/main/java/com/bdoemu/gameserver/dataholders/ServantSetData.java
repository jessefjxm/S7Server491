// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "servantset", group = "sqlite")
@StartupComponent("Data")
public class ServantSetData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ServantSetData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ServantSetTemplate> templates;

    private ServantSetData() {
        this.templates = new HashMap<Integer, ServantSetTemplate>();
        this.load();
    }

    public static ServantSetData getInstance() {
        Object value = ServantSetData.instance.get();
        if (value == null) {
            synchronized (ServantSetData.instance) {
                value = ServantSetData.instance.get();
                if (value == null) {
                    final ServantSetData actualValue = new ServantSetData();
                    value = ((actualValue == null) ? ServantSetData.instance : actualValue);
                    ServantSetData.instance.set(value);
                }
            }
        }
        return (ServantSetData) ((value == ServantSetData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ServantSetData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ServantSet_Table';")) {
            int disabledByContentGroup = 0;
            while (rs.next()) {
                final ServantSetTemplate template = new ServantSetTemplate(rs);
                if (CreatureData.getInstance().getTemplate(template.getCharacterKey()) != null) {
                    this.templates.put(template.getCharacterKey(), template);
                } else {
                    ++disabledByContentGroup;
                }
            }
            ServantSetData.log.info("Loaded {} ServantSet data entries ({} disabled by ContentsGroupOptionData)", (Object) this.templates.size(), (Object) disabledByContentGroup);
        } catch (SQLException ex) {
            ServantSetData.log.error("Failed while loading ServantSet table.", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Collection<ServantSetTemplate> getTemplates() {
        return this.templates.values();
    }

    public ServantSetTemplate getTemplate(final int servantId) {
        return this.templates.get(servantId);
    }
}
