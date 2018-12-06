package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "actionexp", group = "sqlite")
@StartupComponent("Data")
public class ActionEXPData implements IReloadable {
    private static class Holder {
        static final ActionEXPData INSTANCE = new ActionEXPData();
    }

    private static final Logger log = LoggerFactory.getLogger(ActionEXPData.class);

    private HashMap<Integer, HashMap<Integer, ActionEXPT>> templates;

    private ActionEXPData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static ActionEXPData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("ActionEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ActionEXP_Table'")) {
            while (rs.next()) {
                final ActionEXPT template = new ActionEXPT(rs);
                this.templates.computeIfAbsent(template.getClassId(), k -> new HashMap<>()).put(template.getLevel(), template);
            }
            ActionEXPData.log.info("Loaded {} ActionEXPT entries", this.templates.values().size());
        } catch (SQLException ex) {
            ActionEXPData.log.error("Failed to load ActionEXPT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public ActionEXPT getExpTemplate(final Player player, final int level) {
        final Map<Integer, ActionEXPT> expMap = this.templates.get(player.getClassType().getId());
        if (expMap != null && !expMap.isEmpty()) {
            return expMap.get(level);
        }
        return null;
    }
}
