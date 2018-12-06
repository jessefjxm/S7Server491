// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "skill", group = "sqlite")
@StartupComponent("Data")
public class SkillData implements IReloadable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) SkillData.class);
        instance = new AtomicReference<Object>();
    }

    private HashMap<Integer, SkillT> templates;
    private HashMap<Integer, HashMap<Integer, List<SkillT>>> classTemplates;

    private SkillData() {
        this.templates = new HashMap<Integer, SkillT>();
        this.classTemplates = new HashMap<Integer, HashMap<Integer, List<SkillT>>>();
        for (final EClassType type : EClassType.values()) {
            this.classTemplates.put(type.getId(), new HashMap<Integer, List<SkillT>>());
        }
        this.load();
    }

    public static SkillData getInstance() {
        Object value = SkillData.instance.get();
        if (value == null) {
            synchronized (SkillData.instance) {
                value = SkillData.instance.get();
                if (value == null) {
                    final SkillData actualValue = new SkillData();
                    value = ((actualValue == null) ? SkillData.instance : actualValue);
                    SkillData.instance.set(value);
                }
            }
        }
        return (SkillData) ((value == SkillData.instance) ? null : value);
    }

    private void load() {
        ServerInfoUtils.printSection("SkillData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT *, Skill_Table_New.SkillNo as skill_id, Skill_Table_New.ActionName as action_name FROM SkillType_Table_New LEFT JOIN Skill_Table_New ON SkillType_Table_New.SkillNo = Skill_Table_New.SkillNo WHERE SkillType_Table_New.SkillNo NOTNULL")) {
            int disabledByContentOptions = 0;
            while (rs.next()) {
                final SkillT template = new SkillT(rs);
                if (ContentsGroupOptionData.getInstance().isContentsGroupOpen(template.getContentsGroupKey())) {
                    for (final Map.Entry<Integer, HashMap<Integer, List<SkillT>>> entry : this.classTemplates.entrySet()) {
                        final int value = rs.getInt(Integer.toString(entry.getKey()));
                        if (value == 1) {
                            final int pcReqLevel = rs.getInt("PcLevel_ForLearning");
                            if (pcReqLevel < 1) {
                                continue;
                            }
                            if (template.getSkillPointForLearning() != 0) {
                                continue;
                            }
                            if (!template.getSkillOwnerType().isCharacter()) {
                                continue;
                            }
                            if (template.getSkillType().isEquip()) {
                                continue;
                            }
                            if (!entry.getValue().containsKey(pcReqLevel)) {
                                entry.getValue().put(pcReqLevel, new ArrayList<SkillT>());
                            }
                            entry.getValue().get(pcReqLevel).add(template);
                        }
                    }
                    this.templates.put(template.getSkillId(), template);
                } else {
                    ++disabledByContentOptions;
                }
            }
            SkillData.log.info("Loaded {} SkillTemplate's. ({} disabled by ContentsGroupOptionData)", (Object) this.templates.size(), (Object) disabledByContentOptions);
        } catch (SQLException ex) {
            SkillData.log.error("Failed load SkillT", (Throwable) ex);
        }
    }

    public void reload() {
        this.load();
    }

    public SkillT getTemplate(final int skillId) {
        return this.templates.get(skillId);
    }

    public List<SkillT> getSkillData(final int classId, final int level) {
        return this.classTemplates.get(classId).get(level);
    }
}
