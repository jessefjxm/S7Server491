// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.templates.NpcRelationT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "npcrelation", group = "sqlite")
@StartupComponent("Data")
public class NpcRelationData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) NpcRelationData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, NpcRelationT> templates;

    private NpcRelationData() {
        this.templates = new HashMap<Integer, NpcRelationT>();
        this.load();
    }

    public static NpcRelationData getInstance() {
        Object value = NpcRelationData.instance.get();
        if (value == null) {
            synchronized (NpcRelationData.instance) {
                value = NpcRelationData.instance.get();
                if (value == null) {
                    final NpcRelationData actualValue = new NpcRelationData();
                    value = ((actualValue == null) ? NpcRelationData.instance : actualValue);
                    NpcRelationData.instance.set(value);
                }
            }
        }
        return (NpcRelationData) ((value == NpcRelationData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("NpcRelationData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'NpcRelation'")) {
            while (rs.next()) {
                final NpcRelationT template = new NpcRelationT(rs);
                this.templates.put(template.getNpcId(), template);
            }
            NpcRelationData.log.info("Loaded {} NpcRelationTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            NpcRelationData.log.error("Failed load NpcRelationT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public NpcRelationT getTemplate(final int npcId) {
        return this.templates.get(npcId);
    }
}
