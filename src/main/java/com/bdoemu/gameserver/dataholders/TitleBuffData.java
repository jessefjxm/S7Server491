// 
// Decompiled by Procyon v0.5.30
// 

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
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "titlebuff", group = "sqlite")
@StartupComponent("Data")
public class TitleBuffData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static Map<Integer, Integer> titleCountBuffs;

    static {
        log = LoggerFactory.getLogger((Class) TitleBuffData.class);
        instance = new AtomicReference<Object>();
        TitleBuffData.titleCountBuffs = new TreeMap<Integer, Integer>();
    }

    private TitleBuffData() {
        this.load();
    }

    public static TitleBuffData getInstance() {
        Object value = TitleBuffData.instance.get();
        if (value == null) {
            synchronized (TitleBuffData.instance) {
                value = TitleBuffData.instance.get();
                if (value == null) {
                    final TitleBuffData actualValue = new TitleBuffData();
                    value = ((actualValue == null) ? TitleBuffData.instance : actualValue);
                    TitleBuffData.instance.set(value);
                }
            }
        }
        return (TitleBuffData) ((value == TitleBuffData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("TitleBuffData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'TitleBuff_Table'")) {
            while (rs.next()) {
                TitleBuffData.titleCountBuffs.put(rs.getInt("Count"), rs.getInt("SkillNo"));
            }
            TitleBuffData.log.info("Loaded {} TitleBuffData's", (Object) TitleBuffData.titleCountBuffs.size());
        } catch (SQLException ex) {
            TitleBuffData.log.error("Failed to load TitleBuffData", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Map<Integer, Integer> getTitleCountBuffs() {
        return TitleBuffData.titleCountBuffs;
    }

    public int getSkillIdForTitleCount(final int currentTitleCount) {
        int skillId = -1;
        for (final Map.Entry<Integer, Integer> entry : TitleBuffData.titleCountBuffs.entrySet()) {
            if (currentTitleCount == entry.getKey()) {
                skillId = entry.getValue();
                break;
            }
        }
        return skillId;
    }
}
