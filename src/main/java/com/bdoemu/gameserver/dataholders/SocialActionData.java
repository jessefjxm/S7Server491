// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.social.actions.templates.SocialActionT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "socialaction", group = "sqlite")
@StartupComponent("Data")
public class SocialActionData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) SocialActionData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Long, SocialActionT> templates;
    private HashMap<Integer, SocialActionT> templatesByIndex;

    private SocialActionData() {
        this.templates = new HashMap<Long, SocialActionT>();
        this.templatesByIndex = new HashMap<Integer, SocialActionT>();
        this.load();
    }

    public static SocialActionData getInstance() {
        Object value = SocialActionData.instance.get();
        if (value == null) {
            synchronized (SocialActionData.instance) {
                value = SocialActionData.instance.get();
                if (value == null) {
                    final SocialActionData actualValue = new SocialActionData();
                    value = ((actualValue == null) ? SocialActionData.instance : actualValue);
                    SocialActionData.instance.set(value);
                }
            }
        }
        return (SocialActionData) ((value == SocialActionData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("SocialActionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'SocialAction_Table'")) {
            while (rs.next()) {
                final SocialActionT template = new SocialActionT(rs);
                this.templates.put(template.getActionHash(), template);
                this.templatesByIndex.put(template.getIndex(), template);
            }
            SocialActionData.log.info("Loaded {} SocialActionTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            SocialActionData.log.error("Failed load SocialActionT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int size() {
        return this.templates.size();
    }

    public SocialActionT getTemplateByIndex(final int index) {
        return this.templatesByIndex.get(index);
    }

    public SocialActionT getTemplate(final long hash) {
        return this.templates.get(hash);
    }
}
