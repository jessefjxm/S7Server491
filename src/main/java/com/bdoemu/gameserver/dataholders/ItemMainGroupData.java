// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemMainGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemmaingroup", group = "sqlite")
@StartupComponent("Data")
public class ItemMainGroupData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemMainGroupData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemMainGroupT> templates;

    private ItemMainGroupData() {
        this.templates = new HashMap<Integer, ItemMainGroupT>();
        this.load();
    }

    public static ItemMainGroupData getInstance() {
        Object value = ItemMainGroupData.instance.get();
        if (value == null) {
            synchronized (ItemMainGroupData.instance) {
                value = ItemMainGroupData.instance.get();
                if (value == null) {
                    final ItemMainGroupData actualValue = new ItemMainGroupData();
                    value = ((actualValue == null) ? ItemMainGroupData.instance : actualValue);
                    ItemMainGroupData.instance.set(value);
                }
            }
        }
        return (ItemMainGroupData) ((value == ItemMainGroupData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemMainGroupData Loading");
        final HashMap<Integer, List<ItemSubGroupT>> subGroupTemplates = new HashMap<Integer, List<ItemSubGroupT>>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ItemSubGroup_Table'")) {
            final ResultSetMetaData rsMeta = rs.getMetaData();
            int loadedTemplatesCount = 0;
            while (rs.next()) {
                final int subGroupId = rs.getInt("ItemSubGroupKey");
                final ItemSubGroupT template = new ItemSubGroupT(rs, rsMeta);
                if (template.getItemId() != 0) {
                    subGroupTemplates.computeIfAbsent(subGroupId, k -> new ArrayList());
                    subGroupTemplates.get(subGroupId).add(template);
                    ++loadedTemplatesCount;
                }
            }
            ItemMainGroupData.log.info("Loaded {} ItemSubGroupTemplate's from ItemSubGroup_Table", (Object) loadedTemplatesCount);
        } catch (SQLException ex) {
            ItemMainGroupData.log.error("Failed load data from ItemSubGroup_Table", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'TradeItemGroup_Table'")) {
            final ResultSetMetaData rsMeta = rs.getMetaData();
            int loadedTemplatesCount = 0;
            while (rs.next()) {
                final int subGroupId = rs.getInt("ItemSubGroupKey");
                final ItemSubGroupT template = new ItemSubGroupT(rs, rsMeta);
                if (template.getItemId() != 0) {
                    subGroupTemplates.computeIfAbsent(subGroupId, k -> new ArrayList());
                    subGroupTemplates.get(subGroupId).add(template);
                    ++loadedTemplatesCount;
                }
            }
            ItemMainGroupData.log.info("Loaded {} ItemSubGroupTemplate's from TradeItemGroup_Table", (Object) loadedTemplatesCount);
        } catch (SQLException ex) {
            ItemMainGroupData.log.error("Failed load data from TradeItemGroup_Table", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ItemMainGroup_Table'")) {
            while (rs.next()) {
                final ItemMainGroupT template2 = new ItemMainGroupT(rs, subGroupTemplates);
                this.templates.put(template2.getMainGroupId(), template2);
            }
            ItemMainGroupData.log.info("Loaded {} ItemMainGroupTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemMainGroupData.log.error("Failed load ItemMainGroupT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ItemMainGroupT getTemplate(final int mainGroupId) {
        return this.templates.get(mainGroupId);
    }
}
