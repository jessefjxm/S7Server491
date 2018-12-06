package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.fishing.templates.FloatFishingT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "floatfishing", group = "sqlite")
@StartupComponent("Data")
public class FloatFishingData implements IReloadable {
    private static class Holder {
        static final FloatFishingData INSTANCE = new FloatFishingData();
    }
    private static final Logger log = LoggerFactory.getLogger(FloatFishingData.class);
    private Map<Integer, FloatFishingT> templates;

    public FloatFishingData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static FloatFishingData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("FloatFishingData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'FloatFishing_Table' LEFT JOIN FloatFishingPoint_Table ON FloatFishingPoint_Table.FishingGroupKey = FloatFishing_Table.FishingGroupKey")) {
            while (rs.next()) {
                final FloatFishingT template = new FloatFishingT(rs);
                final int index = template.getFishingGroupKey();
                this.templates.put(index, template);
            }
            FloatFishingData.log.info("Loaded {} FloatFishingTemplate's", this.templates.size());
        } catch (SQLException ex) {
            FloatFishingData.log.error("Failed load FloatFishingT",  ex);
        }
        FloatFishingData.log.info("Loaded {} FloatFishing templates", this.templates.size());
    }

    public Collection<FloatFishingT> getTemplates() {
        return this.templates.values();
    }

    public void reload() {
        this.load();
    }
}
