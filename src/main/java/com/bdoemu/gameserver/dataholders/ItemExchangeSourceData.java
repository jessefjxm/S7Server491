// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemexchangesource", group = "sqlite")
@StartupComponent("Data")
public class ItemExchangeSourceData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemExchangeSourceData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemExchangeSourceT> templates;

    private ItemExchangeSourceData() {
        this.templates = new HashMap<Integer, ItemExchangeSourceT>();
        this.load();
    }

    public static ItemExchangeSourceData getInstance() {
        Object value = ItemExchangeSourceData.instance.get();
        if (value == null) {
            synchronized (ItemExchangeSourceData.instance) {
                value = ItemExchangeSourceData.instance.get();
                if (value == null) {
                    final ItemExchangeSourceData actualValue = new ItemExchangeSourceData();
                    value = ((actualValue == null) ? ItemExchangeSourceData.instance : actualValue);
                    ItemExchangeSourceData.instance.set(value);
                }
            }
        }
        return (ItemExchangeSourceData) ((value == ItemExchangeSourceData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemExchangeSourceData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ItemExchangeSource'")) {
            while (rs.next()) {
                final ItemExchangeSourceT template = new ItemExchangeSourceT(rs);
                this.templates.put(template.getIndex(), template);
            }
            ItemExchangeSourceData.log.info("Loaded {} ItemExchangeSourceTemplates", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemExchangeSourceData.log.error("Failed load ItemExchangeSourceT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ItemExchangeSourceT getTemplate(final int index) {
        return this.templates.get(index);
    }
}
