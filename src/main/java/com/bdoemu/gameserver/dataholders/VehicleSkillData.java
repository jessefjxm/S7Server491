// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillOwnerT;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillT;
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

@Reloadable(name = "vehicleskill", group = "sqlite")
@StartupComponent("Data")
public class VehicleSkillData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) VehicleSkillData.class);
        instance = new AtomicReference<Object>();
    }

    private final HashMap<Integer, VehicleSkillOwnerT> templates;
    private final HashMap<Integer, VehicleSkillT> skillTemplates;

    private VehicleSkillData() {
        this.templates = new HashMap<Integer, VehicleSkillOwnerT>();
        this.skillTemplates = new HashMap<Integer, VehicleSkillT>();
        this.load();
    }

    public static VehicleSkillData getInstance() {
        Object value = VehicleSkillData.instance.get();
        if (value == null) {
            synchronized (VehicleSkillData.instance) {
                value = VehicleSkillData.instance.get();
                if (value == null) {
                    final VehicleSkillData actualValue = new VehicleSkillData();
                    value = ((actualValue == null) ? VehicleSkillData.instance : actualValue);
                    VehicleSkillData.instance.set(value);
                }
            }
        }
        return (VehicleSkillData) ((value == VehicleSkillData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("VehicleSkillData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'VehicleSkill_Table'")) {
            while (rs.next()) {
                final VehicleSkillT template = new VehicleSkillT(rs);
                this.skillTemplates.put(template.getSkillId(), template);
            }
            VehicleSkillData.log.info("Loaded {} VehicleSkillT entries", (Object) this.skillTemplates.values().size());
        } catch (SQLException ex) {
            VehicleSkillData.log.error("Failed to load ActionEXPT", (Throwable) ex);
        }
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'VehicleSkillOwner_Table'")) {
            while (rs.next()) {
                final VehicleSkillOwnerT template2 = new VehicleSkillOwnerT(rs);
                this.templates.put(template2.getCreatureId(), template2);
            }
            VehicleSkillData.log.info("Loaded {} VehicleSkillOwnerT entries", (Object) this.templates.values().size());
        } catch (SQLException ex) {
            VehicleSkillData.log.error("Failed to load ActionEXPT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public Collection<VehicleSkillT> getSkillTemplates() {
        return this.skillTemplates.values();
    }

    public VehicleSkillT getSkillTemplate(final int skillId) {
        return this.skillTemplates.get(skillId);
    }

    public VehicleSkillOwnerT getTemplate(final int creatureId) {
        return this.templates.get(creatureId);
    }
}
