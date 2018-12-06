package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "guildquest", group = "sqlite")
@StartupComponent("Data")
public class GuildQuestData implements IReloadable {
    private static class Holder {
        static final GuildQuestData INSTANCE = new GuildQuestData();
    }
    private static final Logger log = LoggerFactory.getLogger(GuildQuestData.class);
    private HashMap<Integer, GuildQuestT> templates;

    private GuildQuestData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static GuildQuestData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("GuildQuestData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'GuildQuest_Table'")) {
            int disabledByContentOptions = 0;
            while (rs.next()) {
                final GuildQuestT template = new GuildQuestT(rs);
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template.getContentsGroupKey())) {
                    this.templates.put(template.getGuildQuestNr(), template);
                } else {
                    ++disabledByContentOptions;
                }
            }
            GuildQuestData.log.info("Loaded {} GuildQuestTemplate group's. ({} disabled by ContentsGroupOptionData)", this.templates.size(), disabledByContentOptions);
        } catch (SQLException ex) {
            GuildQuestData.log.error("Failed load GuildQuestTemplate", ex);
        }
    }

    public GuildQuestT getTemplate(final int questId) {
        return this.templates.get(questId);
    }

    public Collection<GuildQuestT> getTemplates() {
        return this.templates.values();
    }

    public void reload() {
        this.load();
    }
}
