// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantBuffT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "servantbuff", group = "sqlite")
@StartupComponent("Data")
public class ServantBuffData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ServantBuffData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, ServantBuffT> templates;

    private ServantBuffData() {
        this.templates = new HashMap<Integer, ServantBuffT>();
        this.load();
    }

    public static ServantBuffData getInstance() {
        Object value = ServantBuffData.instance.get();
        if (value == null) {
            synchronized (ServantBuffData.instance) {
                value = ServantBuffData.instance.get();
                if (value == null) {
                    final ServantBuffData actualValue = new ServantBuffData();
                    value = ((actualValue == null) ? ServantBuffData.instance : actualValue);
                    ServantBuffData.instance.set(value);
                }
            }
        }
        return (ServantBuffData) ((value == ServantBuffData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ServantBuffData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ServantBuff_Table'")) {
            while (rs.next()) {
                final ServantBuffT template = new ServantBuffT(rs);
                this.templates.put(template.getIndex(), template);
            }
            ServantBuffData.log.info("Loaded {} ServantBuffTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ServantBuffData.log.error("Failed load ServantBuffT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ServantBuffT getTemplate(final int index) {
        return this.templates.get(index);
    }
}
