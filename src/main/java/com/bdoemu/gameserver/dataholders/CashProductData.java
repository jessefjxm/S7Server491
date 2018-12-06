package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.CashItemT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "cashproduct", group = "sqlite")
@StartupComponent("Data")
public class CashProductData implements IReloadable {
    private static class Holder {
        static final CashProductData INSTANCE = new CashProductData();
    }
    private static final Logger log = LoggerFactory.getLogger((Class) CashProductData.class);
    private HashMap<Integer, CashItemT> templates;

    private CashProductData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static CashProductData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("CashProductData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'CashProduct_Table'")) {
            while (rs.next()) {
                final CashItemT template = new CashItemT(rs);
                this.templates.put(template.getProductNo(), template);
            }
            CashProductData.log.info("Loaded {} CashItemTemplate's", this.templates.size());
        } catch (SQLException ex) {
            CashProductData.log.error("Failed load CashItemT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public CashItemT getTemplate(final int productNr) {
        return this.templates.get(productNr);
    }

    public HashMap<Integer, CashItemT> getTemplates() {
        return this.templates;
    }
}
