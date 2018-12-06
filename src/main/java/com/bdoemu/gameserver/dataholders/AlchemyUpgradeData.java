package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.AlchemyUpgradeT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "alchemyupgrade", group = "sqlite")
@StartupComponent("Data")
public class AlchemyUpgradeData implements IReloadable {
    private static class Holder {
        static final AlchemyUpgradeData INSTANCE = new AlchemyUpgradeData();
    }
    private static final Logger log = LoggerFactory.getLogger(AlchemyUpgradeData.class);
    private final HashMap<Integer, AlchemyUpgradeT> templates;

    private AlchemyUpgradeData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static AlchemyUpgradeData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("AlchemyUpgradeData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'AlchemyUpgrade_Table'")) {
            while (rs.next()) {
                final AlchemyUpgradeT template = new AlchemyUpgradeT(rs);
                this.templates.put(template.getItemId(), template);
            }
            AlchemyUpgradeData.log.info("Loaded {} AlchemyUpgradeTemplate's", this.templates.size());
        } catch (SQLException ex) {
            AlchemyUpgradeData.log.error("Failed load AlchemyUpgradeT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public AlchemyUpgradeT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
