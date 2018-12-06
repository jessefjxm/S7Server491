package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.family.templates.FamilyPointRewardT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "familypointreward", group = "sqlite")
@StartupComponent("Data")
public class FamilyPointRewardData implements IReloadable {
    private static class Holder {
        static final FamilyPointRewardData INSTANCE = new FamilyPointRewardData();
    }
    private static final Logger log = LoggerFactory.getLogger((Class) FamilyPointRewardData.class);
    private TreeMap<Integer, FamilyPointRewardT> templates;

    public FamilyPointRewardData() {
        this.templates = new TreeMap<>();
        this.load();
    }

    public static FamilyPointRewardData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("FamilyPointReward Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'FamilyPointReward'")) {
            while (rs.next()) {
                final FamilyPointRewardT template = new FamilyPointRewardT(rs);
                this.templates.put(rs.getInt("Point"), template);
            }
            FamilyPointRewardData.log.info("Loaded {} FamilyPointRewardT's", this.templates.size());
        } catch (SQLException ex) {
            FamilyPointRewardData.log.error("Failed load FamilyPointRewardT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public FamilyPointRewardT getTemplate(final int points) {
        FamilyPointRewardT template = null;
        for (final Map.Entry<Integer, FamilyPointRewardT> entry : this.templates.entrySet()) {
            if (entry.getKey() <= points) {
                template = entry.getValue();
            }
        }
        return template;
    }
}
