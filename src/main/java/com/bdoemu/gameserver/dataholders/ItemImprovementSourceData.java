// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemImprovementSourceT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemimprovementsource", group = "sqlite")
@StartupComponent("Data")
public class ItemImprovementSourceData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemImprovementSourceData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemImprovementSourceT> templates;

    private ItemImprovementSourceData() {
        this.templates = new HashMap<Integer, ItemImprovementSourceT>();
        this.load();
    }

    public static ItemImprovementSourceData getInstance() {
        Object value = ItemImprovementSourceData.instance.get();
        if (value == null) {
            synchronized (ItemImprovementSourceData.instance) {
                value = ItemImprovementSourceData.instance.get();
                if (value == null) {
                    final ItemImprovementSourceData actualValue = new ItemImprovementSourceData();
                    value = ((actualValue == null) ? ItemImprovementSourceData.instance : actualValue);
                    ItemImprovementSourceData.instance.set(value);
                }
            }
        }
        return (ItemImprovementSourceData) ((value == ItemImprovementSourceData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemImprovementSourceData");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Item_ImprovementSource_Table'")) {
            while (rs.next()) {
                final ItemImprovementSourceT template = new ItemImprovementSourceT(rs);
                this.templates.put(template.getSourceItemKey(), template);
            }
            ItemImprovementSourceData.log.info("Loaded {} ItemImprovementSourceTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemImprovementSourceData.log.error("Failed load ItemImprovementSourceT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ItemImprovementSourceT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
