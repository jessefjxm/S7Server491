package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.fitness.enums.EFitnessType;
import com.bdoemu.gameserver.model.creature.player.fitness.templates.FitnessTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "fitness", group = "sqlite")
@StartupComponent("Data")
public class FitnessData implements IReloadable {
    private static class Holder {
        static final FitnessData INSTANCE = new FitnessData();
    }
    private static final Logger log = LoggerFactory.getLogger(FitnessData.class);
    private HashMap<Integer, FitnessTemplate>[] templates;

    @SuppressWarnings("unchecked")
    private FitnessData() {
        this.templates = (HashMap<Integer, FitnessTemplate>[]) new HashMap[EFitnessType.values().length];
        this.load();
    }

    public static FitnessData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        for (int index = 0; index < this.templates.length; ++index) {
            this.templates[index] = new HashMap<>();
        }
        ServerInfoUtils.printSection("FitnessData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'FitnessLevel_Table'")) {
            while (rs.next()) {
                final FitnessTemplate template = new FitnessTemplate(rs);
                final EFitnessType fitnessType = EFitnessType.valueOf(rs.getInt("ExpType"));
                this.templates[fitnessType.ordinal()].put(template.getLevel(), template);
            }
            FitnessData.log.info("Loaded FitnessTemplates for {} EFitnessType's", this.templates.length);
        } catch (SQLException ex) {
            FitnessData.log.error("Failed load FitnessTemplate", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public FitnessTemplate getTemplate(final EFitnessType type, final int level) {
        return this.templates[type.ordinal()].get(level);
    }
}
