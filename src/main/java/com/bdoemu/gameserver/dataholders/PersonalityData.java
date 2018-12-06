// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.npc.templates.PersonalityT;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "personality", group = "sqlite")
@StartupComponent("Data")
public class PersonalityData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) PersonalityData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, PersonalityT> templates;

    private PersonalityData() {
        this.templates = new HashMap<Integer, PersonalityT>();
        this.load();
    }

    public static PersonalityData getInstance() {
        Object value = PersonalityData.instance.get();
        if (value == null) {
            synchronized (PersonalityData.instance) {
                value = PersonalityData.instance.get();
                if (value == null) {
                    final PersonalityData actualValue = new PersonalityData();
                    value = ((actualValue == null) ? PersonalityData.instance : actualValue);
                    PersonalityData.instance.set(value);
                }
            }
        }
        return (PersonalityData) ((value == PersonalityData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("PersonalityData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'Personality_Table'")) {
            while (rs.next()) {
                final PersonalityT template = new PersonalityT(rs);
                this.templates.put(template.getNpcId(), template);
            }
            PersonalityData.log.info("Loaded {} PersonalityTemplate's", (Object) this.templates.size());
        } catch (SQLException ex) {
            PersonalityData.log.error("Failed load PersonalityT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public PersonalityT getTemplate(final int npcId) {
        return this.templates.get(npcId);
    }
}
