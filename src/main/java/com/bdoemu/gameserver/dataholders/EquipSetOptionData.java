package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.EquipSetOptionT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "equipsetoption", group = "sqlite")
@StartupComponent("Data")
public class EquipSetOptionData implements IReloadable {
    private static class Holder {
        static final EquipSetOptionData INSTANCE = new EquipSetOptionData();
    }
    private static final Logger log = LoggerFactory.getLogger(EquipSetOptionData.class);
    private HashMap<Integer, EquipSetOptionT> templates;

    public EquipSetOptionData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static EquipSetOptionData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("EquipSetOptionData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'EquipSetOption'")) {
            while (rs.next()) {
                final EquipSetOptionT template = new EquipSetOptionT(rs);
                for (final Integer itemId : template.getSetItems()) {
                    this.templates.put(itemId, template);
                }
            }
            EquipSetOptionData.log.info("Loaded {} EquipSetOptionTemplate's", this.templates.size());
        } catch (SQLException ex) {
            EquipSetOptionData.log.error("Failed load EquipSetOptionT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public EquipSetOptionT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }
}
