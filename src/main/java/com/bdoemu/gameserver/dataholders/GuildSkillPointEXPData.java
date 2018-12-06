package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.team.guild.templates.GuildSkillPointEXPT;
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

@Reloadable(name = "guildskillpointexp", group = "sqlite")
@StartupComponent("Data")
public class GuildSkillPointEXPData implements IReloadable {
    private static class Holder {
        static final GuildSkillPointEXPData INSTANCE = new GuildSkillPointEXPData();
    }
    private static final Logger log = LoggerFactory.getLogger(GuildSkillPointEXPData.class);
    private Map<Integer, GuildSkillPointEXPT> templates;

    private GuildSkillPointEXPData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static GuildSkillPointEXPData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("GuildSkillPointEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'GuildSkillPointEXP_Table'")) {
            while (rs.next()) {
                final GuildSkillPointEXPT template = new GuildSkillPointEXPT(rs);
                this.templates.put(template.getLevel(), template);
            }
            GuildSkillPointEXPData.log.info("Loaded {} GuildSkillPointEXPData's.", this.templates.size());
        } catch (SQLException ex) {
            GuildSkillPointEXPData.log.error("Failed load GuildSkillPointEXPT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public GuildSkillPointEXPT getTemplate(final int level) {
        return this.templates.get(level);
    }
}
