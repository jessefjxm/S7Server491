package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.skills.templates.AwakeningAbilityT;
import com.bdoemu.gameserver.model.skills.templates.AwakeningT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "awakening", group = "sqlite")
@StartupComponent("Data")
public class AwakeningData implements IReloadable {
    private static class Holder {
        static final AwakeningData INSTANCE = new AwakeningData();
    }
    private static final Logger log = LoggerFactory.getLogger(AwakeningData.class);
    private HashMap<Integer, AwakeningT> templates;

    private AwakeningData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static AwakeningData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("AwakeningData Loading");
        final HashMap<Integer, AwakeningAbilityT> abilities = new HashMap<>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Awakening_Ability_Table'")) {
            while (rs.next()) {
                final AwakeningAbilityT template = new AwakeningAbilityT(rs);
                abilities.put(template.getAbilityId(), template);
            }
            AwakeningData.log.info("Loaded {} AwakeningAbilityTemplate's", abilities.size());
        } catch (SQLException ex) {
            AwakeningData.log.error("Failed load AwakeningAbilityT", ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Awakening_Table'")) {
            while (rs.next()) {
                final List<AwakeningAbilityT> abilityList = new ArrayList<>();
                for (int index = 1; index <= this.getAbilitiesCount(); ++index) {
                    final int abilityId = rs.getInt("Ability_Index" + index);
                    abilityList.add(abilities.get(abilityId));
                }
                final AwakeningT template2 = new AwakeningT(rs, abilityList);
                this.templates.put(template2.getSkillId(), template2);
            }
            AwakeningData.log.info("Loaded {} AwakeningTemplate's", this.templates.size());
        } catch (SQLException ex) {
            AwakeningData.log.error("Failed load AwakeningT", ex);
        }
    }

    public void reload() {
        this.load();
    }

    public AwakeningT getTemplate(final int awakeningId) {
        return this.templates.get(awakeningId);
    }

    public int getAbilitiesCount() {
        return 32;
    }
}
