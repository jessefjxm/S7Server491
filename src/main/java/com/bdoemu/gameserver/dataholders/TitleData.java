// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.titles.templates.TitleTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "title", group = "sqlite")
@StartupComponent("Data")
public class TitleData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) TitleData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, TitleTemplate> templates;

    private TitleData() {
        this.templates = new HashMap<Integer, TitleTemplate>();
        this.load();
    }

    public static TitleData getInstance() {
        Object value = TitleData.instance.get();
        if (value == null) {
            synchronized (TitleData.instance) {
                value = TitleData.instance.get();
                if (value == null) {
                    final TitleData actualValue = new TitleData();
                    value = ((actualValue == null) ? TitleData.instance : actualValue);
                    TitleData.instance.set(value);
                }
            }
        }
        return (TitleData) ((value == TitleData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("TitleData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Title_Table'")) {
            while (rs.next()) {
                final TitleTemplate template = new TitleTemplate(rs);
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template.getContentGroupKey())) {
                    this.templates.put(template.getTitleId(), template);
                }
            }
            TitleData.log.info("Loaded {} TitleTemplate's.", (Object) this.templates.size());
        } catch (SQLException ex) {
            TitleData.log.error("Failed load TitleTemplate", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public HashMap<Integer, TitleTemplate> getTemplates() {
        return this.templates;
    }

    public TitleTemplate getTemplate(final int titleId) {
        return this.templates.get(titleId);
    }
}
