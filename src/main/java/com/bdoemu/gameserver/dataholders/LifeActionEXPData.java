// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.templates.LifeActionEXPT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "lifeactionexp", group = "sqlite")
@StartupComponent("Data")
public class LifeActionEXPData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) LifeActionEXPData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, LifeActionEXPT> templates;

    private LifeActionEXPData() {
        this.templates = new HashMap<Integer, LifeActionEXPT>();
        this.load();
    }

    public static LifeActionEXPData getInstance() {
        Object value = LifeActionEXPData.instance.get();
        if (value == null) {
            synchronized (LifeActionEXPData.instance) {
                value = LifeActionEXPData.instance.get();
                if (value == null) {
                    final LifeActionEXPData actualValue = new LifeActionEXPData();
                    value = ((actualValue == null) ? LifeActionEXPData.instance : actualValue);
                    LifeActionEXPData.instance.set(value);
                }
            }
        }
        return (LifeActionEXPData) ((value == LifeActionEXPData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("LifeActionEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'LifeActionEXP_Table'")) {
            while (rs.next()) {
                final LifeActionEXPT template = new LifeActionEXPT(rs);
                this.templates.put(template.getItemId(), template);
            }
            LifeActionEXPData.log.info("Loaded {} LifeEXPTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            LifeActionEXPData.log.error("Failed load LifeEXPT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public boolean reward(final Player player, final int itemId, final ELifeExpType type) {
        final LifeActionEXPT lifeActionEXPT = getInstance().getTemplate(itemId);
        if (lifeActionEXPT != null) {
            if (lifeActionEXPT.getType() == type) {
                player.getLifeExperienceStorage().getLifeExperience(lifeActionEXPT.getType()).addExp(player, lifeActionEXPT.getExp());
            }
            if (lifeActionEXPT.getType1() == type) {
                player.getLifeExperienceStorage().getLifeExperience(lifeActionEXPT.getType1()).addExp(player, lifeActionEXPT.getExp1());
            }
        }
        return false;
    }

    public LifeActionEXPT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
