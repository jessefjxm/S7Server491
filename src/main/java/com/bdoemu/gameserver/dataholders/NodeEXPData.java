// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.NodeEXPT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "nodeexp", group = "sqlite")
@StartupComponent("Data")
public class NodeEXPData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) NodeEXPData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, NodeEXPT> templates;

    private NodeEXPData() {
        this.templates = new HashMap<Integer, NodeEXPT>();
        this.load();
    }

    public static NodeEXPData getInstance() {
        Object value = NodeEXPData.instance.get();
        if (value == null) {
            synchronized (NodeEXPData.instance) {
                value = NodeEXPData.instance.get();
                if (value == null) {
                    final NodeEXPData actualValue = new NodeEXPData();
                    value = ((actualValue == null) ? NodeEXPData.instance : actualValue);
                    NodeEXPData.instance.set(value);
                }
            }
        }
        return (NodeEXPData) ((value == NodeEXPData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("NodeEXPData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'NodeEXP_Table'")) {
            while (rs.next()) {
                final NodeEXPT template = new NodeEXPT(rs);
                this.templates.put(template.getLevel(), template);
            }
        } catch (SQLException ex) {
            NodeEXPData.log.error("Failed load NodeEXPT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public int getSize() {
        return this.templates.size();
    }

    public NodeEXPT getTemplate(final int level) {
        return this.templates.get(level);
    }
}
