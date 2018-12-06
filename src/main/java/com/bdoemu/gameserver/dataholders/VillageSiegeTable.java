// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.siege.templates.VillageSiegeT;
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

@Reloadable(name = "villagesiege", group = "sqlite")
@StartupComponent("Data")
public class VillageSiegeTable implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static final Map<Integer, VillageSiegeT> templates;

    static {
        log = LoggerFactory.getLogger((Class) VillageSiegeTable.class);
        instance = new AtomicReference<Object>();
        templates = new HashMap<Integer, VillageSiegeT>();
    }

    private VillageSiegeTable() {
        this.load();
    }

    public static VillageSiegeTable getInstance() {
        Object value = VillageSiegeTable.instance.get();
        if (value == null) {
            synchronized (VillageSiegeTable.instance) {
                value = VillageSiegeTable.instance.get();
                if (value == null) {
                    final VillageSiegeTable actualValue = new VillageSiegeTable();
                    value = ((actualValue == null) ? VillageSiegeTable.instance : actualValue);
                    VillageSiegeTable.instance.set(value);
                }
            }
        }
        return (VillageSiegeTable) ((value == VillageSiegeTable.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("VillageSiegeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'VillageSiege_Table'")) {
            while (rs.next()) {
                final VillageSiegeT template = new VillageSiegeT(rs);
                VillageSiegeTable.templates.put(template.getVillageSiegeKey(), template);
            }
            VillageSiegeTable.log.info("Loaded {} VillageSiegeTtemplate's", (Object) VillageSiegeTable.templates.size());
        } catch (SQLException ex) {
            VillageSiegeTable.log.error("Failed load VillageSiegeT", (Throwable) ex);
        }
    }

    public VillageSiegeT getTemplate(final int villageSiegeKey) {
        return VillageSiegeTable.templates.get(villageSiegeKey);
    }

    public void reload() {
        this.load();
    }
}
