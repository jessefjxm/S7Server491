// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.quests.templates.QuestTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "quest", group = "sqlite")
@StartupComponent("Data")
public class QuestData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) QuestData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, HashMap<Integer, QuestTemplate>> templates;

    private QuestData() {
        this.templates = new HashMap<Integer, HashMap<Integer, QuestTemplate>>();
        this.load();
    }

    public static QuestData getInstance() {
        Object value = QuestData.instance.get();
        if (value == null) {
            synchronized (QuestData.instance) {
                value = QuestData.instance.get();
                if (value == null) {
                    final QuestData actualValue = new QuestData();
                    value = ((actualValue == null) ? QuestData.instance : actualValue);
                    QuestData.instance.set(value);
                }
            }
        }
        return (QuestData) ((value == QuestData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("QuestData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Quest_Table'")) {
            int disabledByContentOptions = 0;
            while (rs.next()) {
                final QuestTemplate template = new QuestTemplate(rs);
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template.getContentsGroupKey())) {
                    if (!this.templates.containsKey(template.getQuestGroupId())) {
                        this.templates.put(template.getQuestGroupId(), new HashMap<Integer, QuestTemplate>());
                    }
                    this.templates.get(template.getQuestGroupId()).put(template.getQuestId(), template);
                } else {
                    ++disabledByContentOptions;
                }
            }
            QuestData.log.info("Loaded {} QuestTemplate group's. ({} disabled by ContentsGroupOptionData)", (Object) this.templates.size(), (Object) disabledByContentOptions);
        } catch (SQLException ex) {
            QuestData.log.error("Failed load QuestTemplate", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public QuestTemplate getTemplate(final int groupId, final int questId) {
        final HashMap<Integer, QuestTemplate> data = this.templates.get(groupId);
        if (data == null) {
            return null;
        }
        return data.get(questId);
    }

    public HashMap<Integer, QuestTemplate> getTemplates(final int groupId) {
        return this.templates.get(groupId);
    }
}
