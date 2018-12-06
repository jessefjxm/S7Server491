package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "encyclopedia", group = "sqlite")
@StartupComponent("Data")
public class EncyclopediaData implements IReloadable {
    private static class Holder {
        static final EncyclopediaData INSTANCE = new EncyclopediaData();
    }
    private static final Logger log = LoggerFactory.getLogger(EncyclopediaData.class);
    private final HashMap<Integer, EncyclopediaT> templates;

    private EncyclopediaData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static EncyclopediaData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("EncyclopediaData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Encyclopedia_Table'")) {
            while (rs.next()) {
                final EncyclopediaT template = new EncyclopediaT(rs);
                this.templates.put(template.getTypeKey(), template);
            }
            EncyclopediaData.log.info("Loaded {} EncyclopediaTemplate's", this.templates.size());
        } catch (SQLException ex) {
            EncyclopediaData.log.error("Failed load EncyclopediaT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public HashMap<Integer, EncyclopediaT> getTemplates() {
        return this.templates;
    }

    public EncyclopediaT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
