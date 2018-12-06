// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.ItemPuzzleT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itempuzzle", group = "sqlite")
@StartupComponent("Data")
public class ItemPuzzleData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemPuzzleData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, ItemPuzzleT> templates;

    private ItemPuzzleData() {
        this.templates = new HashMap<Integer, ItemPuzzleT>();
        this.load();
    }

    public static ItemPuzzleData getInstance() {
        Object value = ItemPuzzleData.instance.get();
        if (value == null) {
            synchronized (ItemPuzzleData.instance) {
                value = ItemPuzzleData.instance.get();
                if (value == null) {
                    final ItemPuzzleData actualValue = new ItemPuzzleData();
                    value = ((actualValue == null) ? ItemPuzzleData.instance : actualValue);
                    ItemPuzzleData.instance.set(value);
                }
            }
        }
        return (ItemPuzzleData) ((value == ItemPuzzleData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemPuzzleData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ItemPuzzle_Table'")) {
            int index = 0;
            while (rs.next()) {
                final int puzzleKey = rs.getInt("PuzzleKey");
                if (!this.templates.containsKey(puzzleKey)) {
                    index = 0;
                    final ItemPuzzleT template = new ItemPuzzleT(rs, index);
                    this.templates.put(puzzleKey, template);
                } else {
                    ++index;
                    this.templates.get(puzzleKey).update(rs, index);
                }
            }
            ItemPuzzleData.log.info("Loaded {} ItemPuzzleTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ItemPuzzleData.log.error("Failed load ItemPuzzleT", (Throwable) ex);
        }
    }

    public void reload() {
        this.templates.clear();
        this.load();
    }

    public ItemPuzzleT getTemplate(final int puzzleKey) {
        return this.templates.get(puzzleKey);
    }
}
