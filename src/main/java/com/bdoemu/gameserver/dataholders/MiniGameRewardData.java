// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.minigame.template.MiniGameT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "minigamereward", group = "sqlite")
@StartupComponent("Data")
public class MiniGameRewardData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) MiniGameRewardData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, MiniGameT> templates;

    private MiniGameRewardData() {
        this.templates = new HashMap<Integer, MiniGameT>();
        this.load();
    }

    public static MiniGameRewardData getInstance() {
        Object value = MiniGameRewardData.instance.get();
        if (value == null) {
            synchronized (MiniGameRewardData.instance) {
                value = MiniGameRewardData.instance.get();
                if (value == null) {
                    final MiniGameRewardData actualValue = new MiniGameRewardData();
                    value = ((actualValue == null) ? MiniGameRewardData.instance : actualValue);
                    MiniGameRewardData.instance.set(value);
                }
            }
        }
        return (MiniGameRewardData) ((value == MiniGameRewardData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("MiniGameRewardData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'MiniGameReward'")) {
            while (rs.next()) {
                final MiniGameT template = new MiniGameT(rs);
                this.templates.put(template.getGameId(), template);
            }
            MiniGameRewardData.log.info("Loaded {} MiniGameTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            MiniGameRewardData.log.error("Failed load MiniGameT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public MiniGameT getTemplate(final int gameId) {
        return this.templates.get(gameId);
    }
}
