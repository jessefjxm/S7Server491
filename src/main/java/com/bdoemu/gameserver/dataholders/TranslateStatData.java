// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.stats.templates.TranslateStatT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "translatestat", group = "sqlite")
@StartupComponent("Data")
public class TranslateStatData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) TranslateStatData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, TranslateStatT> templates;

    private TranslateStatData() {
        this.templates = new HashMap<Integer, TranslateStatT>();
        this.load();
    }

    public static TranslateStatData getInstance() {
        Object value = TranslateStatData.instance.get();
        if (value == null) {
            synchronized (TranslateStatData.instance) {
                value = TranslateStatData.instance.get();
                if (value == null) {
                    final TranslateStatData actualValue = new TranslateStatData();
                    value = ((actualValue == null) ? TranslateStatData.instance : actualValue);
                    TranslateStatData.instance.set(value);
                }
            }
        }
        return (TranslateStatData) ((value == TranslateStatData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("TranslateStatData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'TranslateStat'")) {
            while (rs.next()) {
                final TranslateStatT template = new TranslateStatT(rs);
                this.templates.put(template.getPoint(), template);
            }
        } catch (SQLException ex) {
            TranslateStatData.log.error("Failed load TranslateStatT", (Throwable) ex);
        }
        TranslateStatData.log.info("Loaded {} TranslateStat templates", (Object) this.templates.size());
    }

    public void reload() {
        this.load();
    }

    public int size() {
        return this.templates.size();
    }

    public TranslateStatT getTemplate(final int point) {
        return this.templates.get(point);
    }
}
