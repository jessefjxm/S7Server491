// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "lifeexp", group = "sqlite")
@StartupComponent("Data")
public class LifeEXPData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) LifeEXPData.class);
        instance = new AtomicReference<Object>();
    }

    private Double[][] templates;

    private LifeEXPData() {
        this.templates = new Double[ELifeExpType.values().length][101];
        this.load();
    }

    public static LifeEXPData getInstance() {
        Object value = LifeEXPData.instance.get();
        if (value == null) {
            synchronized (LifeEXPData.instance) {
                value = LifeEXPData.instance.get();
                if (value == null) {
                    final LifeEXPData actualValue = new LifeEXPData();
                    value = ((actualValue == null) ? LifeEXPData.instance : actualValue);
                    LifeEXPData.instance.set(value);
                }
            }
        }
        return (LifeEXPData) ((value == LifeEXPData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("LifeEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'LifeEXP_Table'")) {
            while (rs.next()) {
                final int level = rs.getInt("Level");
                for (int i = 0; i < ELifeExpType.values().length - 1; ++i) {
                    final Double exp = rs.getDouble("LifeType" + i);
                    this.templates[i][level] = exp;
                }
            }
            LifeEXPData.log.info("Loaded {} LifeEXPData's", (Object) this.templates.length);
        } catch (SQLException ex) {
            LifeEXPData.log.error("Failed load LifeEXPData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Double getMaxExp(final ELifeExpType type, final int level) {
        return this.templates[type.ordinal()][level];
    }
}
