// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.RepairMaxEnduranceT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Reloadable(name = "repairmaxendurance", group = "sqlite")
@StartupComponent("Data")
public class RepairMaxEnduranceData implements IReloadable {
    private static class Holder {
        static final RepairMaxEnduranceData INSTANCE = new RepairMaxEnduranceData();
    }
    private static final Logger log = LoggerFactory.getLogger(RepairMaxEnduranceData.class);
    private final HashMap<Integer, RepairMaxEnduranceT> templates;

    private RepairMaxEnduranceData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static RepairMaxEnduranceData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("RepairMaxEnduranceData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'RepairMaxEndurance'")) {
            while (rs.next()) {
                final RepairMaxEnduranceT template = new RepairMaxEnduranceT(rs);
                templates.put(template.getBaseItemId(), template);
            }
            RepairMaxEnduranceData.log.info("Loaded {} RepairMaxEnduranceTemplate's", this.templates.size());
        } catch (SQLException ex) {
            RepairMaxEnduranceData.log.error("Failed load RepairMaxEnduranceT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public RepairMaxEnduranceT getTemplate(int itemId, int enchantLevel) {
        return templates.get(itemId);
    }
}
