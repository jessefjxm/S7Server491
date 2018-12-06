// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.misc.enums.ETaxType;
import com.bdoemu.gameserver.model.trade.templates.TaxTemplate;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "tax", group = "sqlite")
@StartupComponent("Data")
public class TaxData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) TaxData.class);
        instance = new AtomicReference<Object>();
    }

    private EnumMap<ETerritoryKeyType, EnumMap<ETaxType, TaxTemplate>> templates;

    private TaxData() {
        this.templates = new EnumMap<ETerritoryKeyType, EnumMap<ETaxType, TaxTemplate>>(ETerritoryKeyType.class);
        this.load();
    }

    public static TaxData getInstance() {
        Object value = TaxData.instance.get();
        if (value == null) {
            synchronized (TaxData.instance) {
                value = TaxData.instance.get();
                if (value == null) {
                    final TaxData actualValue = new TaxData();
                    value = ((actualValue == null) ? TaxData.instance : actualValue);
                    TaxData.instance.set(value);
                }
            }
        }
        return (TaxData) ((value == TaxData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("TaxData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM Tax")) {
            while (rs.next()) {
                final TaxTemplate template = new TaxTemplate(rs);
                this.templates.computeIfAbsent(template.getTerritoryKey(), k -> new EnumMap(ETaxType.class)).put(template.getTaxType(), template);
            }
            TaxData.log.info("Loaded {} TaxTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            TaxData.log.error("Failed load TaxTemplate", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public TaxTemplate getTemplate(final ETerritoryKeyType territoryKey, final ETaxType taxType) {
        return this.templates.get(territoryKey).get(taxType);
    }
}
