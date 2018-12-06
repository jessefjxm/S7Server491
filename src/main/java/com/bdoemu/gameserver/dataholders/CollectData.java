package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.collect.templates.CollectTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "collect", group = "sqlite")
@StartupComponent("Data")
public class CollectData implements IReloadable {
    private static class Holder {
        static final CollectData INSTANCE = new CollectData();
    }
    private static final Logger log = LoggerFactory.getLogger(CollectData.class);

    private HashMap<Integer, CollectTemplate> templates;

    private CollectData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static CollectData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("CollectData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Collect_Table'")) {
            while (rs.next()) {
                final CollectTemplate template = new CollectTemplate(rs);
                this.templates.put(template.getCollectId(), template);
            }
        } catch (SQLException ex) {
            CollectData.log.error("Failed load CollectTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public CollectTemplate getTemplate(final int collectId) {
        return this.templates.get(collectId);
    }
}
