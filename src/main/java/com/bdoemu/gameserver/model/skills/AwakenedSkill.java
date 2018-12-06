// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills;

import com.bdoemu.commons.collection.BitMask;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.AwakeningData;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.skills.templates.AwakeningAbilityT;
import com.bdoemu.gameserver.model.skills.templates.AwakeningT;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.List;

public class AwakenedSkill {
    private final SkillT skillT;
    private int skillLevel;
    private AwakeningT template;
    private BitMask mask;

    public AwakenedSkill(final int skillLevel, final AwakeningT template, final int abilityId) {
        this.mask = new BitMask(AwakeningData.getInstance().getAbilitiesCount());
        this.skillLevel = skillLevel;
        this.template = template;
        this.mask.setMask((long) abilityId);
        this.skillT = SkillData.getInstance().getTemplate(template.getSkillId());
    }

    public AwakenedSkill(final BasicDBObject object) {
        this.mask = new BitMask(AwakeningData.getInstance().getAbilitiesCount());
        final int skillId = object.getInt("skillId");
        this.mask.setMask((long) object.getInt("abilityId", 0));
        this.skillLevel = 1;
        this.template = AwakeningData.getInstance().getTemplate(skillId);
        this.skillT = SkillData.getInstance().getTemplate(skillId);
    }

    public static AwakenedSkill newAwakenedSkill(final int skillId, final int abilityId) {
        final AwakeningT template = AwakeningData.getInstance().getTemplate(skillId);
        if (template == null) {
            return null;
        }
        return new AwakenedSkill(1, template, abilityId);
    }

    public final void reawakening(final int abilityId) {
        this.mask.clear();
        this.mask.setMask((long) abilityId);
    }

    public final void reawakening() {
        this.mask.clear();
        this.mask.setMask((long) Rnd.get(0, this.template.getAbilityList().size() - 1));
    }

    public int getAbilityId() {
        return this.mask.getMaskInt();
    }

    private List<AwakeningAbilityT> getAbilityList() {
        final List<AwakeningAbilityT> abilitys = new ArrayList<AwakeningAbilityT>();
        for (int i = 0; i < AwakeningData.getInstance().getAbilitiesCount(); ++i) {
            if (this.mask.get(i)) {
                abilitys.add(this.template.getAbilityList().get(i));
            }
        }
        return abilitys;
    }

    public SkillT getSkillT() {
        return this.skillT;
    }

    public int getSkillId() {
        return this.template.getSkillId();
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }

    public AwakeningT getTemplate() {
        return this.template;
    }
}
