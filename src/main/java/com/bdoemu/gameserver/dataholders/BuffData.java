package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "buff", group = "sqlite")
@StartupComponent("Data")
public class BuffData implements IReloadable {
    private static class Holder {
        static final BuffData INSTANCE = new BuffData();
    }
    private static final Logger log = LoggerFactory.getLogger(BuffData.class);
    private HashMap<Integer, BuffTemplate> templates;

    public BuffData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static BuffData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("BuffData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Buff_Table'")) {
            while (rs.next()) {
                final BuffTemplate template = new BuffTemplate(rs);
                this.templates.put(template.getBuffId(), template);
            }
            BuffData.log.info("Loaded {} BuffTemplate's", this.templates.size());
        } catch (SQLException ex) {
            BuffData.log.error("Failed load BuffTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public BuffTemplate getTemplate(final int buffId) {
        return this.templates.get(buffId);
    }
}
