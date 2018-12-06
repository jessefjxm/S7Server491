// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class CreatureSkill extends JSONable {
    private int skillLevel;
    private SkillT template;

    public CreatureSkill(final SkillT template, final int skillLevel) {
        this.skillLevel = skillLevel;
        this.template = template;
    }

    public static CreatureSkill newSkill(final int skillId) {
        final SkillT template = SkillData.getInstance().getTemplate(skillId);
        if (template == null) {
            return null;
        }
        return newSkill(template);
    }

    public static CreatureSkill newSkill(final SkillT template) {
        if (template == null) {
            return null;
        }
        return new CreatureSkill(template, template.getLevel());
    }

    public static CreatureSkill newSkill(final BasicDBObject dbObject) {
        final int skillId = dbObject.getInt("skillId");
        final int skillLevel = dbObject.getInt("skillLevel");
        return newSkill(skillId);
    }

    public int getSkillId() {
        return this.template.getSkillId();
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }

    public SkillT getTemplate() {
        return this.template;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("skillId", (Object) this.getSkillId());
        builder.append("skillLevel", (Object) this.skillLevel);
        return builder.get();
    }
}
