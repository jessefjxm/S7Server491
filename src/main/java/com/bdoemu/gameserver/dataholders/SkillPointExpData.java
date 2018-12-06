// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.skills.templates.SkillExpT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "skillpointexp", group = "sqlite")
@StartupComponent("Data")
public class SkillPointExpData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) SkillPointExpData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, SkillExpT> templates;

    private SkillPointExpData() {
        this.templates = new HashMap<Integer, SkillExpT>();
        this.load();
    }

    public static SkillPointExpData getInstance() {
        Object value = SkillPointExpData.instance.get();
        if (value == null) {
            synchronized (SkillPointExpData.instance) {
                value = SkillPointExpData.instance.get();
                if (value == null) {
                    final SkillPointExpData actualValue = new SkillPointExpData();
                    value = ((actualValue == null) ? SkillPointExpData.instance : actualValue);
                    SkillPointExpData.instance.set(value);
                }
            }
        }
        return (SkillPointExpData) ((value == SkillPointExpData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("SkillExpData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'SkillPointEXP_Table_New' ")) {
            while (rs.next()) {
                final SkillExpT template = new SkillExpT(rs);
                this.templates.put(template.getSkillExpLevel(), template);
            }
            SkillPointExpData.log.info("Loaded {} SkillExpTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            SkillPointExpData.log.error("Failed load SkillExpT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public SkillExpT getTemplate(final int skillExpLevel) {
        return this.templates.get(skillExpLevel);
    }
}
