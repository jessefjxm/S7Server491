// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "itemexchange", group = "sqlite")
@StartupComponent("Data")
public class ItemExchangeData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ItemExchangeData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, EnumMap<EClassType, Integer>> templates;

    private ItemExchangeData() {
        this.templates = new HashMap<Integer, EnumMap<EClassType, Integer>>();
        this.load();
    }

    public static ItemExchangeData getInstance() {
        Object value = ItemExchangeData.instance.get();
        if (value == null) {
            synchronized (ItemExchangeData.instance) {
                value = ItemExchangeData.instance.get();
                if (value == null) {
                    final ItemExchangeData actualValue = new ItemExchangeData();
                    value = ((actualValue == null) ? ItemExchangeData.instance : actualValue);
                    ItemExchangeData.instance.set(value);
                }
            }
        }
        return (ItemExchangeData) ((value == ItemExchangeData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ItemExchangeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Item_Exchange'")) {
            while (rs.next()) {
                final int index = rs.getInt("Index");
                this.templates.put(index, new EnumMap<EClassType, Integer>(EClassType.class));
                for (final EClassType classType : EClassType.values()) {
                    final int value = rs.getInt("ClassType" + classType.getId());
                    this.templates.get(index).put(classType, Integer.valueOf(value));
                }
            }
            ItemExchangeData.log.info("Loaded {} ItemExchange's", (Object) this.templates.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            ItemExchangeData.log.error("Failed load ItemExchange", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Integer getItemId(final int itemId, final EClassType classType) {
        for (final EnumMap<EClassType, Integer> map : this.templates.values()) {
            for (final int _itemId : map.values()) {
                if (_itemId == itemId) {
                    return map.get(classType);
                }
            }
        }
        return null;
    }
}
