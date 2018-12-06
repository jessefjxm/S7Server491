package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerPassiveSkillT;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerSkillT;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "plantworker", group = "sqlite")
@StartupComponent("Data")
public class PlantWorkerData implements IReloadable {
    private static class Holder {
        static final PlantWorkerData INSTANCE = new PlantWorkerData();
    }

    public static PlantWorkerData getInstance() {
        return Holder.INSTANCE;
    }
    private static final Logger log = LoggerFactory.getLogger(PlantWorkerData.class);
    private HashMap<Integer, PlantWorkerT> templates;
    private HashMap<Integer, PlantWorkerPassiveSkillT> passiveSkills;
    private HashMap<Integer, PlantWorkerSkillT> activeSkills;

    private PlantWorkerData() {
        this.templates = new HashMap<>();
        this.passiveSkills = new HashMap<>();
        this.activeSkills = new HashMap<>();
        this.load();
    }

    private void load() {
        ServerInfoUtils.printSection("PlantWorkerData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PlantWorkerPassiveSkill_Table'")) {
            while (rs.next()) {
                final PlantWorkerPassiveSkillT plantWorkerT = new PlantWorkerPassiveSkillT(rs);
                this.passiveSkills.put(plantWorkerT.getSkillId(), plantWorkerT);
            }
        } catch (SQLException ex) {
            PlantWorkerData.log.error("Failed load PlantWorkerPassiveSkillT", ex);
        }
        PlantWorkerData.log.info("Loaded {} PlantWorkerPassiveSkillTemplate's", this.passiveSkills.size());

        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM PlantWorkerSkill_Table GROUP BY CharacterKey")) {
            while (rs.next()) {
                final PlantWorkerSkillT plantWorkerT = new PlantWorkerSkillT(rs);
                this.activeSkills.put(plantWorkerT.getCharacterKey(), plantWorkerT);
            }
        } catch (SQLException ex) {
            PlantWorkerData.log.error("Failed load PlantWorkerSkillT", ex);
        }
        PlantWorkerData.log.info("Loaded {} PlantWorkerSkillTemplate's", this.activeSkills.size());


        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'PlantWorker_Table'")) {
            while (rs.next()) {
                final PlantWorkerT plantWorkerT2 = new PlantWorkerT(rs);
                this.templates.put(plantWorkerT2.getCharacterKey(), plantWorkerT2);
            }
        } catch (SQLException ex) {
            PlantWorkerData.log.error("Failed load PlantWorkerT", ex);
        }
        PlantWorkerData.log.info("Loaded {} PlantWorkerTemplate's", this.templates.size());
    }

    public void reload() {
        this.load();
    }

    public Collection<PlantWorkerPassiveSkillT> getPassiveSkills() {
        return this.passiveSkills.values();
    }

    public PlantWorkerPassiveSkillT getPassiveSkill(final int skillId) {
        return this.passiveSkills.get(skillId);
    }

    public PlantWorkerSkillT getSkill(final int characterKey) {
        return this.activeSkills.get(characterKey);
    }

    public PlantWorkerT getTemplate(final int characterKey) {
        return this.templates.get(characterKey);
    }
}
