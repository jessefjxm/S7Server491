package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "contentsgroup", group = "sqlite")
@StartupComponent("Data")
public class ContentsGroupOptionData implements IReloadable {
    private static class Holder {
        static final ContentsGroupOptionData INSTANCE = new ContentsGroupOptionData();
    }
    private static final Logger log = LoggerFactory.getLogger(ContentsGroupOptionData.class);
    private Map<Integer, Boolean> table;

    private ContentsGroupOptionData() {
        this.table = new HashMap<>();
        this.load();
    }

    public static ContentsGroupOptionData getInstance() {
        return Holder.INSTANCE;
    }

    public void load() {
        ServerInfoUtils.printSection("ContentsGroupOptionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'ContentsGroupOption'")) {
            while (rs.next()) {
                final int contentsGroupKey = rs.getInt("ContentsGroupKey");
                final boolean value =
                        rs.getBoolean(ServerConfig.GAME_SERVICE_TYPE.toString())
                        || rs.getBoolean("KOR_REAL");
                this.table.put(contentsGroupKey, value);
            }
            ContentsGroupOptionData.log.info("Loaded {} ContentsGroupOptionData's", this.table.size());
        } catch (SQLException ex) {
            ContentsGroupOptionData.log.error("Failed load ContentsGroupOptionData", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public boolean isContentsGroupOpen(final int contentGroupId) {
        return this.table.containsKey(contentGroupId) && this.table.get(contentGroupId);
    }

    public boolean isContentsGroupOpen(final Set<Integer> contentGroupIds) {
        for (final Integer contentGroupId : contentGroupIds) {
            if (!this.isContentsGroupOpen(contentGroupId)) {
                return false;
            }
        }
        return true;
    }
}
