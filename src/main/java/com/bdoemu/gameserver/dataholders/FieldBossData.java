package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "fieldboss", group = "sqlite")
@StartupComponent("Data")
public class FieldBossData implements IReloadable {
    private static class Holder {
        static final FieldBossData INSTANCE = new FieldBossData();
    }
    private static final Logger _log = LoggerFactory.getLogger(FieldBossData.class);
    private Map<Integer, SortedMap<Integer, Integer>> _templates;

    private FieldBossData() {
        _templates = new HashMap<>();
        load();
    }

    public static FieldBossData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("FieldBossData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'FieldBoss_Table'")) {
            while (rs.next()) {
                final int characterKey = rs.getInt("CharacterKey");
                final int rank = rs.getInt("Rank");
                final int dropGroupKey = rs.getInt("DropGroupKey");

                _templates.computeIfAbsent(characterKey, k -> new TreeMap<>());
                _templates.get(characterKey).put(rank, dropGroupKey);
            }

            _log.info("Loaded {} FieldBoss templates.", _templates.size());
        } catch (SQLException ex) {
            _log.error("Failed to load Fieldboss template due to exception.", ex);
        }
    }

    public void reload() {
        load();
    }

    public SortedMap<Integer, Integer> getFieldBossData(final int characterKey) {
        return _templates.get(characterKey);
    }

    public boolean hasFieldBossData(final int characterKey) {
        return _templates.get(characterKey) != null;
    }
}