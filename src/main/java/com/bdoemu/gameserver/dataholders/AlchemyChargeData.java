package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.AlchemyChargeT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "alchemycharge", group = "sqlite")
@StartupComponent("Data")
public class AlchemyChargeData implements IReloadable {
    private static class Holder {
        static final AlchemyChargeData INSTANCE = new AlchemyChargeData();
    }
    private static final Logger log = LoggerFactory.getLogger(AlchemyChargeData.class);
    private final HashMap<Integer, AlchemyChargeT> templates;

    private AlchemyChargeData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static AlchemyChargeData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("AlchemyChargeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'AlchemyCharge_Table'")) {
            while (rs.next()) {
                final AlchemyChargeT template = new AlchemyChargeT(rs);
                this.templates.put(template.getItemId(), template);
            }
        } catch (SQLException ex) {
            AlchemyChargeData.log.error("Failed load AlchemyChargeT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public AlchemyChargeT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
