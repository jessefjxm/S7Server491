package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.fishing.templates.FishingT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "fishing", group = "sqlite")
@StartupComponent("Data")
public class FishingData implements IReloadable {
    private static class Holder {
        static final FishingData INSTANCE = new FishingData();
    }
    private static final Logger log = LoggerFactory.getLogger(FishingData.class);
    private final HashMap<Integer, FishingT> templates;

    public FishingData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static FishingData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("FishingData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Fishing_Table'")) {
            while (rs.next()) {
                final FishingT template = new FishingT(rs);
                this.templates.put(template.getColor().getRGB(), template);
            }
            FishingData.log.info("Loaded {} FishingTemplate's", this.templates.size());
        } catch (SQLException ex) {
            FishingData.log.error("Failed load FishingT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public FishingT getTemplate(final int rgb) {
        return this.templates.get(rgb);
    }
}
