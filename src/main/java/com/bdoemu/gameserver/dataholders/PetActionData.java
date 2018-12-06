// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
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

@Reloadable(name = "petaction", group = "sqlite")
@StartupComponent("Data")
public class PetActionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PetActionData.class);
        instance = new AtomicReference<Object>();
    }

    private Map<Integer, String> templates;

    private PetActionData() {
        this.templates = new HashMap<Integer, String>();
        this.load();
    }

    public static PetActionData getInstance() {
        Object value = PetActionData.instance.get();
        if (value == null) {
            synchronized (PetActionData.instance) {
                value = PetActionData.instance.get();
                if (value == null) {
                    final PetActionData actualValue = new PetActionData();
                    value = ((actualValue == null) ? PetActionData.instance : actualValue);
                    PetActionData.instance.set(value);
                }
            }
        }
        return (PetActionData) ((value == PetActionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PetActionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PetAction_Table'")) {
            while (rs.next()) {
                this.templates.put(rs.getInt("Index"), rs.getString("Name"));
            }
            PetActionData.log.info("Loaded {} PetActionData's", (Object) this.templates.size());
        } catch (SQLException ex) {
            PetActionData.log.error("Failed load PetActionData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getActionsCount() {
        return this.templates.values().size();
    }
}
