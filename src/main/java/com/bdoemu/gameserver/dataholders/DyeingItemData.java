package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

@Reloadable(name = "dyeing", group = "sqlite")
@StartupComponent("Data")
public class DyeingItemData implements IReloadable {
    private static class Holder {
        static final DyeingItemData INSTANCE = new DyeingItemData();
    }
    private static final Logger log = LoggerFactory.getLogger(DyeingItemData.class);
    private HashMap<Integer, DyeingItemT> templates;

    private DyeingItemData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static DyeingItemData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("DyeingItemData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'DyeingItem_Table'")) {
            while (rs.next()) {
                final DyeingItemT template = new DyeingItemT(rs);
                this.templates.put(template.getItemId(), template);
            }
            DyeingItemData.log.info("Loaded {} DyeingItemTemplate's", this.templates.size());
        } catch (SQLException ex) {
            DyeingItemData.log.error("Failed load DyeingItemT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public DyeingItemT getTemplate(final int itemId) {
        return this.templates.get(itemId);
    }

    public HashMap<Integer, DyeingItemT> getTemplates() {
        return this.templates;
    }
}
