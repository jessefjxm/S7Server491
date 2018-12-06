package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

@Reloadable(name = "enchantfailcountoption", group = "sqlite")
@StartupComponent("Data")
public class EnchantFailCountOptionData implements IReloadable {
    private static class Holder {
        static final EnchantFailCountOptionData INSTANCE = new EnchantFailCountOptionData();
    }
    private static final Logger log = LoggerFactory.getLogger(EnchantFailCountOptionData.class);
    private HashMap<Integer, Integer> templates;

    private EnchantFailCountOptionData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static EnchantFailCountOptionData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("ItemData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'EnchantFailCountOption'")) {
            while (rs.next()) {
                final int itemKey = rs.getInt("ItemKey");
                final int enchantFailCount = rs.getInt("EnchantFailCount");
                this.templates.put(itemKey, enchantFailCount);
            }
            EnchantFailCountOptionData.log.info("Loaded {} EnchantFailCountOption's", this.templates.size());
        } catch (SQLException ex) {
            EnchantFailCountOptionData.log.error("Failed load EnchantFailCountOption", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Integer getTemplate(final int itemKey) {
        return this.templates.get(itemKey);
    }
}
