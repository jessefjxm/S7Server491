// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantInteractionT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "servantinteraction", group = "sqlite")
@StartupComponent("Data")
public class ServantInteractionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ServantInteractionData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, ServantInteractionT> templates;

    private ServantInteractionData() {
        this.templates = new HashMap<Integer, ServantInteractionT>();
        this.load();
    }

    public static ServantInteractionData getInstance() {
        Object value = ServantInteractionData.instance.get();
        if (value == null) {
            synchronized (ServantInteractionData.instance) {
                value = ServantInteractionData.instance.get();
                if (value == null) {
                    final ServantInteractionData actualValue = new ServantInteractionData();
                    value = ((actualValue == null) ? ServantInteractionData.instance : actualValue);
                    ServantInteractionData.instance.set(value);
                }
            }
        }
        return (ServantInteractionData) ((value == ServantInteractionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ServantInteractionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ServantInteraction_Table'")) {
            while (rs.next()) {
                final ServantInteractionT template = new ServantInteractionT(rs);
                this.templates.put(template.getCreatureId(), template);
            }
            ServantInteractionData.log.info("Loaded {} ServantInteractionTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ServantInteractionData.log.error("Failed load ServantInteractionT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ServantInteractionT getTemplate(final int creatureId) {
        return this.templates.get(creatureId);
    }
}
