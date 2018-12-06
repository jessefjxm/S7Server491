package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.card.templates.ZodiacSignT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "zodiacsign", group = "sqlite")
@StartupComponent("Data")
public class ZodiacSignData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ZodiacSignData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ZodiacSignT> templates;

    private ZodiacSignData() {
        this.templates = new HashMap<Integer, ZodiacSignT>();
        this.load();
    }

    public static ZodiacSignData getInstance() {
        Object value = ZodiacSignData.instance.get();
        if (value == null) {
            synchronized (ZodiacSignData.instance) {
                value = ZodiacSignData.instance.get();
                if (value == null) {
                    final ZodiacSignData actualValue = new ZodiacSignData();
                    value = ((actualValue == null) ? ZodiacSignData.instance : actualValue);
                    ZodiacSignData.instance.set(value);
                }
            }
        }
        return (ZodiacSignData) ((value == ZodiacSignData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ZodiacSignData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ZodiacSignIndex_Table'")) {
            while (rs.next()) {
                final ZodiacSignT template = new ZodiacSignT(rs);
                this.templates.put(template.getZodiacSignKey(), template);
            }
        } catch (SQLException ex) {
            ZodiacSignData.log.error("Failed load ZodiacSignT", (Throwable) ex);
        }
        ZodiacSignData.log.info("Loaded {} ZodiacSignT templates", (Object) this.templates.size());
    }

    public void reload() {
        this.load();
    }

    public ZodiacSignT getTemplate(final int zodiacSignId) {
        return this.templates.get(zodiacSignId);
    }
}
