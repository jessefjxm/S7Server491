// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ProductToolPropertyT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "producttoolproperty", group = "sqlite")
@StartupComponent("Data")
public class ProductToolPropertyData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) ProductToolPropertyData.class);
        instance = new AtomicReference<Object>();
    }

    private Map<Integer, TreeMap<Integer, ProductToolPropertyT>> templates;

    private ProductToolPropertyData() {
        this.templates = new HashMap<Integer, TreeMap<Integer, ProductToolPropertyT>>();
        this.load();
    }

    public static ProductToolPropertyData getInstance() {
        Object value = ProductToolPropertyData.instance.get();
        if (value == null) {
            synchronized (ProductToolPropertyData.instance) {
                value = ProductToolPropertyData.instance.get();
                if (value == null) {
                    final ProductToolPropertyData actualValue = new ProductToolPropertyData();
                    value = ((actualValue == null) ? ProductToolPropertyData.instance : actualValue);
                    ProductToolPropertyData.instance.set(value);
                }
            }
        }
        return (ProductToolPropertyData) ((value == ProductToolPropertyData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("ProductToolPropertyData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ProductTool_Property'")) {
            while (rs.next()) {
                final ProductToolPropertyT template = new ProductToolPropertyT(rs);
                if (!this.templates.containsKey(template.getItemKey())) {
                    this.templates.put(template.getItemKey(), new TreeMap<Integer, ProductToolPropertyT>());
                }
                this.templates.get(template.getItemKey()).put(template.getEnchantLevel(), template);
            }
            ProductToolPropertyData.log.info("Loaded {} ProductToolPropertyData's", (Object) this.templates.size());
        } catch (SQLException ex) {
            ProductToolPropertyData.log.error("Failed load ProductToolPropertyData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getAutoFishingTime(final Item item) {
        int autoFishingTime = LocalizingOptionConfig.AUTO_FISHING_TIME;
        if (this.templates.containsKey(item.getItemId())) {
            if (this.templates.get(item.getItemId()).containsKey(item.getEnchantLevel())) {
                autoFishingTime = autoFishingTime / 100 * (this.templates.get(item.getItemId()).get(item.getEnchantLevel()).getAutofishingTimePercents() / 10000);
            } else {
                autoFishingTime = autoFishingTime / 100 * (this.templates.get(item.getItemId()).lastEntry().getValue().getAutofishingTimePercents() / 10000);
            }
        }
        return autoFishingTime;
    }
}
