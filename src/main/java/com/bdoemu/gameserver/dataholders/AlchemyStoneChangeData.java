package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.AlchemyStoneChangeT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "alchemystonechange", group = "sqlite")
@StartupComponent("Data")
public class AlchemyStoneChangeData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) AlchemyStoneChangeData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, AlchemyStoneChangeT> templates;

    private AlchemyStoneChangeData() {
        this.templates = new HashMap<Integer, AlchemyStoneChangeT>();
        this.load();
    }

    public static AlchemyStoneChangeData getInstance() {
        Object value = AlchemyStoneChangeData.instance.get();
        if (value == null) {
            synchronized (AlchemyStoneChangeData.instance) {
                value = AlchemyStoneChangeData.instance.get();
                if (value == null) {
                    final AlchemyStoneChangeData actualValue = new AlchemyStoneChangeData();
                    value = ((actualValue == null) ? AlchemyStoneChangeData.instance : actualValue);
                    AlchemyStoneChangeData.instance.set(value);
                }
            }
        }
        return (AlchemyStoneChangeData) ((value == AlchemyStoneChangeData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("AlchemyStoneChangeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'AlchemyStoneChange_Table'")) {
            while (rs.next()) {
                final AlchemyStoneChangeT template = new AlchemyStoneChangeT(rs);
                this.templates.put(template.getItemId(), template);
            }
        } catch (SQLException ex) {
            AlchemyStoneChangeData.log.error("Failed load AlchemyStoneChangeT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public AlchemyStoneChangeT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
