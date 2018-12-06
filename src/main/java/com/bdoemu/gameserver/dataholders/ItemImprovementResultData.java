// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemImprovementResultT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemimprovementresult", group = "sqlite")
@StartupComponent("Data")
public class ItemImprovementResultData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemImprovementResultData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemImprovementResultT> templates;

    private ItemImprovementResultData() {
        this.templates = new HashMap<Integer, ItemImprovementResultT>();
        this.load();
    }

    public static ItemImprovementResultData getInstance() {
        Object value = ItemImprovementResultData.instance.get();
        if (value == null) {
            synchronized (ItemImprovementResultData.instance) {
                value = ItemImprovementResultData.instance.get();
                if (value == null) {
                    final ItemImprovementResultData actualValue = new ItemImprovementResultData();
                    value = ((actualValue == null) ? ItemImprovementResultData.instance : actualValue);
                    ItemImprovementResultData.instance.set(value);
                }
            }
        }
        return (ItemImprovementResultData) ((value == ItemImprovementResultData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemImprovementResultData");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Item_ImprovementResult_Table'")) {
            while (rs.next()) {
                final ItemImprovementResultT template = new ItemImprovementResultT(rs);
                this.templates.put(template.getIndex(), template);
            }
            ItemImprovementResultData.log.info("Loaded {} ItemImprovementResultTemplates", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemImprovementResultData.log.error("Failed load ItemImprovementResultT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ItemImprovementResultT getTemplate(final int itemId) {
        for (final ItemImprovementResultT template : this.templates.values()) {
            if (template.containItem(itemId)) {
                return template;
            }
        }
        return null;
    }
}
