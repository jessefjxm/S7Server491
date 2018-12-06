// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.servant.templates.PetTemplate;
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

@Reloadable(name = "pet", group = "sqlite")
@StartupComponent("Data")
public class PetData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PetData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, PetTemplate> templates;

    private PetData() {
        this.templates = new HashMap<Integer, PetTemplate>();
        this.load();
    }

    public static PetData getInstance() {
        Object value = PetData.instance.get();
        if (value == null) {
            synchronized (PetData.instance) {
                value = PetData.instance.get();
                if (value == null) {
                    final PetData actualValue = new PetData();
                    value = ((actualValue == null) ? PetData.instance : actualValue);
                    PetData.instance.set(value);
                }
            }
        }
        return (PetData) ((value == PetData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PetData Loading");
        final HashMap<Integer, HashMap<Integer, Integer>> expTemplates = new HashMap<Integer, HashMap<Integer, Integer>>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_Exp_Table'")) {
            while (rs.next()) {
                final int key = rs.getInt("PetExpTableKey");
                final int level = rs.getInt("Level");
                final int exp = rs.getInt("Exp");
                if (!expTemplates.containsKey(key)) {
                    expTemplates.put(key, new HashMap<Integer, Integer>());
                }
                expTemplates.get(key).put(level, exp);
            }
        } catch (SQLException ex) {
            PetData.log.error("Failed load PetExpTemplate", (Throwable) ex);
        }
        PetData.log.info("Loaded {} PetExpTemplate's.", (Object) expTemplates.size());
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Pet_Table'")) {
            while (rs.next()) {
                final int expType = rs.getInt("RequireEXPType");
                final PetTemplate template = new PetTemplate(rs, expTemplates.get(expType));
                this.templates.put(template.getId(), template);
            }
        } catch (SQLException ex) {
            PetData.log.error("Failed load PetTemplate", (Throwable) ex);
        }
        PetData.log.info("Loaded {} PetTemplate's.", (Object) expTemplates.size());
    }

    public void reload() {
        this.load();
    }

    public PetTemplate getTemplate(final int petId) {
        return this.templates.get(petId);
    }

    public Collection<PetTemplate> getTemplates() {
        return this.templates.values();
    }
}
