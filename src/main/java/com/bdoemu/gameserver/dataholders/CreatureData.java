package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.creature.templates.ObjectTemplate;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "creature", group = "sqlite")
@StartupComponent("Data")
public class CreatureData implements IReloadable {
    private static class Holder {
        static final CreatureData INSTANCE = new CreatureData();
    }
    private static final Logger log = LoggerFactory.getLogger(CreatureData.class);
    private HashMap<Integer, CreatureTemplate> templates;

    public CreatureData() {
        this.templates = new HashMap<>();
        this.load();
    }

    public static CreatureData getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        ServerInfoUtils.printSection("CreatureData Loading");
        final HashMap<Integer, CreatureFunctionT> functionTemplates = new HashMap<>();
        final HashMap<Integer, ObjectTemplate> objectTemplates = new HashMap<>();
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Object_Table'")) {
            while (rs.next()) {
                final ObjectTemplate template = new ObjectTemplate(rs);
                objectTemplates.put(rs.getInt("Index"), template);
            }
            CreatureData.log.info("Loaded {} ObjectTemplate's", objectTemplates.size());
        } catch (SQLException ex) {
            CreatureData.log.error("Failed load ObjectTemplate", ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'CharacterFunction_Table'")) {
            while (rs.next()) {
                final CreatureFunctionT template2 = new CreatureFunctionT(rs);
                functionTemplates.put(template2.getCreatureId(), template2);
            }
            CreatureData.log.info("Loaded {} CreatureFunctionTemplate's", functionTemplates.size());
        } catch (SQLException ex) {
            CreatureData.log.error("Failed load CreatureFunctionT", ex);
        }
        int disabledByContentGroup = 0;
        try (final Connection con2 = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement2 = con2.createStatement();
             final ResultSet rs2 = statement2.executeQuery("SELECT * FROM 'Character_Table'")) {
            while (rs2.next()) {
                final int creatureId = rs2.getInt("Index");
                final CreatureTemplate template3 = new CreatureTemplate(rs2, functionTemplates.get(creatureId), objectTemplates.get(creatureId));
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template3.getContentsGroupKey())) {
                    this.templates.put(creatureId, template3);
                } else {
                    ++disabledByContentGroup;
                }
            }
            CreatureData.log.info("Loaded {} CreatureTemplate's ({} disabled by ContentsGroupOptionData)", this.templates.size(), (Object) disabledByContentGroup);
        } catch (SQLException ex2) {
            CreatureData.log.error("Failed load CreatureTemplate's", ex2);
        }
    }

    public void reload() {
        this.load();
    }

    public CreatureTemplate getTemplate(final int creatureId) {
        return this.templates.get(creatureId);
    }
}
