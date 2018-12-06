// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemChangeT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemchange", group = "sqlite")
@StartupComponent("Data")
public class ItemChangeData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemChangeData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemChangeT> templates;

    private ItemChangeData() {
        this.templates = new HashMap<Integer, ItemChangeT>();
        this.load();
    }

    public static ItemChangeData getInstance() {
        Object value = ItemChangeData.instance.get();
        if (value == null) {
            synchronized (ItemChangeData.instance) {
                value = ItemChangeData.instance.get();
                if (value == null) {
                    final ItemChangeData actualValue = new ItemChangeData();
                    value = ((actualValue == null) ? ItemChangeData.instance : actualValue);
                    ItemChangeData.instance.set(value);
                }
            }
        }
        return (ItemChangeData) ((value == ItemChangeData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemChangeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ItemChange_Table'")) {
            while (rs.next()) {
                final ItemChangeT template = new ItemChangeT(rs);
                this.templates.put(template.getFromItemId(), template);
            }
            ItemChangeData.log.info("Loaded {} ItemChangeTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemChangeData.log.error("Failed load ItemChangeT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ItemChangeT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
