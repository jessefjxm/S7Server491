package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.contribution.templates.ContributionEXPT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "contributionexp", group = "sqlite")
@StartupComponent("Data")
public class ContributionEXPData implements IReloadable {
    private static class Holder {
        static final ContributionEXPData INSTANCE = new ContributionEXPData();
    }
    private static final Logger log = LoggerFactory.getLogger(ContributionEXPData.class);
    private HashMap<Integer, HashMap<Integer, ContributionEXPT>> templates;

    private ContributionEXPData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static ContributionEXPData getInstance() {
        return Holder.INSTANCE;
    }

    public void load() {
        ServerInfoUtils.printSection("ContributionEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ContributionEXP_Table'")) {
            while (rs.next()) {
                final int territoryKey = rs.getInt("TerritoryKey");
                this.templates.computeIfAbsent(territoryKey, k -> new HashMap<>());
                final HashMap<Integer, ContributionEXPT> map = this.templates.get(territoryKey);
                int prevExplorePoint = 0;
                int prevRequireEXP = 0;
                for (int i = 1; i <= 20; ++i) {
                    final int explorePoint = rs.getInt("ExplorePoint" + i);
                    final int requireEXP = rs.getInt("RequireEXP" + i);
                    while (prevExplorePoint <= explorePoint) {
                        if (prevExplorePoint == explorePoint) {
                            prevRequireEXP = requireEXP;
                        }
                        final ContributionEXPT template = new ContributionEXPT(prevExplorePoint, prevRequireEXP);
                        map.put(prevExplorePoint, template);
                        ++prevExplorePoint;
                    }
                }
            }
            ContributionEXPData.log.info("Loaded {} ContributionEXPData's", (Object) this.templates.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            ContributionEXPData.log.error("Failed load ContributionEXPT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public HashMap<Integer, ContributionEXPT> getTemplates(final int territoryKey) {
        return this.templates.get(territoryKey);
    }

    public ContributionEXPT getTemplate(final int territoryKey, final int level) {
        final HashMap<Integer, ContributionEXPT> map = this.templates.get(territoryKey);
        if (map == null) {
            return null;
        }
        return map.get(level);
    }
}
