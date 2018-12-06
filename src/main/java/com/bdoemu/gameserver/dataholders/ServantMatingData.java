// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantMatingT;
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

@Reloadable(name = "servantmating", group = "sqlite")
@StartupComponent("Data")
public class ServantMatingData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ServantMatingData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, ServantMatingT> templates;

    private ServantMatingData() {
        this.templates = new HashMap<Integer, ServantMatingT>();
        this.load();
    }

    public static ServantMatingData getInstance() {
        Object value = ServantMatingData.instance.get();
        if (value == null) {
            synchronized (ServantMatingData.instance) {
                value = ServantMatingData.instance.get();
                if (value == null) {
                    final ServantMatingData actualValue = new ServantMatingData();
                    value = ((actualValue == null) ? ServantMatingData.instance : actualValue);
                    ServantMatingData.instance.set(value);
                }
            }
        }
        return (ServantMatingData) ((value == ServantMatingData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ServantMatingData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ServantMating_Table'")) {
            while (rs.next()) {
                final ServantMatingT template = new ServantMatingT(rs);
                this.templates.put(template.getIndex(), template);
            }
            ServantMatingData.log.info("Loaded {} ServantMatingTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ServantMatingData.log.error("Failed while loading ServantMatingT.", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Collection<ServantMatingT> getTemplates() {
        return this.templates.values();
    }
}
