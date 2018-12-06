// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Knowledge.templates.KnowledgeLearningT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "knowledgelearning", group = "sqlite")
@StartupComponent("Data")
public class KnowledgeLearningData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) KnowledgeLearningData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, KnowledgeLearningT> templates;

    private KnowledgeLearningData() {
        this.templates = new HashMap<Integer, KnowledgeLearningT>();
        this.load();
    }

    public static KnowledgeLearningData getInstance() {
        Object value = KnowledgeLearningData.instance.get();
        if (value == null) {
            synchronized (KnowledgeLearningData.instance) {
                value = KnowledgeLearningData.instance.get();
                if (value == null) {
                    final KnowledgeLearningData actualValue = new KnowledgeLearningData();
                    value = ((actualValue == null) ? KnowledgeLearningData.instance : actualValue);
                    KnowledgeLearningData.instance.set(value);
                }
            }
        }
        return (KnowledgeLearningData) ((value == KnowledgeLearningData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("KnowledgeLearningData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'KnowledgeLearning_Table'")) {
            while (rs.next()) {
                final KnowledgeLearningT template = new KnowledgeLearningT(rs);
                this.templates.put(template.getCreatureId(), template);
            }
            KnowledgeLearningData.log.info("Loaded {} KnowledgeLearningTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            KnowledgeLearningData.log.error("Failed load KnowledgeLearningT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public KnowledgeLearningT getTemplate(final int keyId) {
        return this.templates.get(keyId);
    }
}
