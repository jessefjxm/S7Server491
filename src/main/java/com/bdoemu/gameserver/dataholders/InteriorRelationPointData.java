// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.templates.InteriorRelationPointT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "interiorrelationpoint", group = "sqlite")
@StartupComponent("Data")
public class InteriorRelationPointData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) InteriorRelationPointData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, InteriorRelationPointT> templates;

    private InteriorRelationPointData() {
        this.templates = new HashMap<Integer, InteriorRelationPointT>();
        this.load();
    }

    public static InteriorRelationPointData getInstance() {
        Object value = InteriorRelationPointData.instance.get();
        if (value == null) {
            synchronized (InteriorRelationPointData.instance) {
                value = InteriorRelationPointData.instance.get();
                if (value == null) {
                    final InteriorRelationPointData actualValue = new InteriorRelationPointData();
                    value = ((actualValue == null) ? InteriorRelationPointData.instance : actualValue);
                    InteriorRelationPointData.instance.set(value);
                }
            }
        }
        return (InteriorRelationPointData) ((value == InteriorRelationPointData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("InteriorRelationPointData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'InteriorRelationPoint_Table'")) {
            while (rs.next()) {
                final InteriorRelationPointT template = new InteriorRelationPointT(rs);
                this.templates.put(rs.getInt("CharacterKey"), template);
            }
            InteriorRelationPointData.log.info("Loaded {} InteriorRelationPointTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            InteriorRelationPointData.log.error("Failed load InteriorRelationPointT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public InteriorRelationPointT getTemplate(final int characterKey) {
        return this.templates.get(characterKey);
    }
}
