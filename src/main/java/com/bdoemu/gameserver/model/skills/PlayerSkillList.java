// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.dataholders.SkillPointExpData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillExpT;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerSkillList extends JSONable {
    private final ConcurrentHashMap<Integer, CreatureSkill> skills;
    private final ConcurrentHashMap<Integer, AwakenedSkill> awakeningSkills;
    private int totalSkillPoints;
    private int currentSkillPointsExp;
    private int availableSkillPoints;
    private int skillExpLevel;
    private SkillExpT template;
    private Player owner;

    public PlayerSkillList(final Player owner) {
        this.skills = new ConcurrentHashMap<Integer, CreatureSkill>();
        this.awakeningSkills = new ConcurrentHashMap<Integer, AwakenedSkill>();
        this.owner = owner;
        this.template = SkillPointExpData.getInstance().getTemplate(0);
    }

    public PlayerSkillList(final BasicDBObject object, final Player owner) {
        this.skills = new ConcurrentHashMap<Integer, CreatureSkill>();
        this.awakeningSkills = new ConcurrentHashMap<Integer, AwakenedSkill>();
        this.owner = owner;
        for (int i = 1; i <= owner.getLevel(); ++i) {
            this.updateSkills(owner.getClassType().getId(), i);
        }
        final BasicDBList skillsDB = (BasicDBList) object.get("skills");
        for (final Object aSkillsDB : skillsDB) {
            this.addSkill(CreatureSkill.newSkill((BasicDBObject) aSkillsDB));
        }
        final BasicDBList awakeningDB = (BasicDBList) object.get("awakenedSkills");
        for (final Object anAwakeningDB : awakeningDB) {
            final AwakenedSkill awakeningSkill = new AwakenedSkill((BasicDBObject) anAwakeningDB);
            this.awakeningSkills.put(awakeningSkill.getSkillId(), awakeningSkill);
        }
        this.skillExpLevel = object.getInt("skillExpLevel");
        this.totalSkillPoints = object.getInt("totalSkillPoints");
        this.availableSkillPoints = object.getInt("availableSkillPoints");
        this.currentSkillPointsExp = object.getInt("currentSkillPointsExp");
        this.template = SkillPointExpData.getInstance().getTemplate(this.skillExpLevel);
    }

    public void updateSkills(final int classType, final int level) {
        final List<SkillT> templates = SkillData.getInstance().getSkillData(classType, level);
        if (templates != null) {
            for (final SkillT template : templates) {
                this.addSkill(new CreatureSkill(template, template.getLevel()));
            }
        }
    }

    public boolean learnSkill(final int skillId, final boolean isEffect) {
        final SkillT template = SkillData.getInstance().getTemplate(skillId);
        if (template == null || !template.canLearn(this.owner)) {
            return false;
        }
        synchronized (this.skills) {
            if (this.skills.containsKey(skillId)) {
                return false;
            }
            final CreatureSkill skill = CreatureSkill.newSkill(skillId);
            if (skill == null || !template.getSkillOwnerType().isCharacter()) {
                return false;
            }
            final int points = template.getSkillPointForLearning();
            if ((!isEffect && points == 0) || this.availableSkillPoints < points) {
                return false;
            }
            this.availableSkillPoints -= points;
            this.addSkill(skill);
            this.owner.getObserveController().notifyObserver(EObserveType.learnSkill, skillId);
            this.owner.setLearnSkillCacheCount(this.owner.getLearnSkillCacheCount() + 1);
            this.owner.sendBroadcastItSelfPacket(new SMLearnSkill(this.owner, skillId, skill.getSkillLevel(), points));
            this.owner.sendPacket(new SMSkillList(this.owner));
            this.owner.sendPacket(new SMSetCharacterSkillPoints(this));
            if (skill.getTemplate().getSkillType().isPassive()) {
                SkillService.useSkill(this.owner, skill.getTemplate(), null, Collections.singletonList(this.owner));
            }
            return true;
        }
    }

    public boolean containsSkill(final int skillId) {
        return this.skills.containsKey(skillId);
    }

    public Collection<CreatureSkill> getSkills() {
        return this.skills.values();
    }

    private void addSkill(final CreatureSkill skill) {
        if (skill != null) {
            this.skills.put(skill.getSkillId(), skill);
        }
    }

    public CreatureSkill getSkill(final int skillId) {
        return this.skills.get(skillId);
    }

    public void addSkillPoints(final int points) {
        synchronized (this.skills) {
            this.totalSkillPoints += points;
            this.availableSkillPoints += points;
        }
        this.owner.sendPacket(new SMSetCharacterSkillPoints(this));
    }

    public void addSkillExp(final float exp) {
        this.addSkillExp(exp, false);
    }

    public void addSkillExp(double exp, final boolean combat) {
        if (exp <= 0.0 || this.template == null) {
            return;
        }
        synchronized (this.skills) {
            final float limit = combat ? this.template.getRequireSkillExpLimit() : ((float) this.template.getQuestRequireExpLimit());
            if (exp > limit) {
                exp = limit;
            }
            double ratedExp = exp * RateConfig.RATE_SKILL_EXP / 100.0;
            final int skillExpStat = this.owner.getGameStats().getSkillExpRate().getIntMaxValue() / 10000;
            if (skillExpStat > 0) {
                ratedExp += ratedExp * skillExpStat / 100.0;
            }
            this.currentSkillPointsExp += (int) ratedExp;
            if (this.currentSkillPointsExp > this.template.getRequireExp()) {
                this.currentSkillPointsExp -= this.template.getRequireExp();
                this.totalSkillPoints += this.template.getAquiredPoint();
                this.availableSkillPoints += this.template.getAquiredPoint();
                this.template = SkillPointExpData.getInstance().getTemplate(++this.skillExpLevel);
            }
            this.owner.sendPacket(new SMSetCharacterSkillPoints(this));
        }
    }

    public void applyPassiveBuffs() {
        this.skills.values().stream().filter(creatureSkill -> creatureSkill.getTemplate().getSkillType().isPassive()).forEach(creatureSkill -> SkillService.useSkill(this.owner, creatureSkill.getTemplate(), null, Collections.singletonList(this.owner)));
    }

    public void clearLearnedSkills() {
        synchronized (this.skills) {
            this.availableSkillPoints = this.totalSkillPoints;
            this.skills.values().stream().filter(skill -> skill.getTemplate().getSkillPointForLearning() > 0).forEach(skill -> {
                this.skills.values().remove(skill);
                this.owner.getBuffList().dispelBuffs(skill.getSkillId());
                return;
            });
            this.owner.sendPacket(new SMClearSkills(this.owner));
        }
    }

    public void clearLearnedSkill(final int skillId) {
        synchronized (this.skills) {
            for (final CreatureSkill learnedSkill : this.skills.values()) {
                final SkillT learnedSkillT = learnedSkill.getTemplate();
                if (learnedSkillT.getNeedSkillNo0ForLearning() > 0 && learnedSkillT.getNeedSkillNo0ForLearning() == skillId) {
                    return;
                }
                if (learnedSkillT.getNeedSkillNo1ForLearning() > 0 && learnedSkillT.getNeedSkillNo1ForLearning() == skillId) {
                    return;
                }
            }
            final CreatureSkill skill = this.skills.get(skillId);
            if (skill != null) {
                if (skill.getTemplate().getSkillPointForLearning() <= 0) {
                    return;
                }
                this.skills.values().remove(skill);
                this.availableSkillPoints += skill.getTemplate().getSkillPointForLearning();
                this.owner.sendPacket(new SMClearSkillPartly(this.owner, skill.getSkillLevel(), skillId));
                this.owner.getBuffList().dispelBuffs(skill.getSkillId());
            }
        }
    }

    public int getSkillExpLevel() {
        return this.skillExpLevel;
    }

    public int getTotalSkillPoints() {
        return this.totalSkillPoints;
    }

    public int getAvailableSkillPoints() {
        return this.availableSkillPoints;
    }

    public int getCurrentSkillPointsExp() {
        return this.currentSkillPointsExp;
    }

    public void learnAwakeningSkill(final int skillId, final int abilityId) {
        int availableCount = 0;
        final SkillT skillT = SkillData.getInstance().getTemplate(skillId);
        if (skillT == null) {
            return;
        }
        switch (skillT.getSkillAwakeningType()) {
            case Normal: {
                if (this.owner.getLevel() >= 54) {
                    availableCount = 3;
                } else if (this.owner.getLevel() >= 52) {
                    availableCount = 2;
                } else if (this.owner.getLevel() >= 50) {
                    availableCount = 1;
                }
                if (this.getNormalAwakenings().size() >= availableCount) {
                    return;
                }
                break;
            }
            case AwakeningWeapon: {
                if (this.owner.getLevel() >= 60) {
                    availableCount = 3;
                } else if (this.owner.getLevel() >= 58) {
                    availableCount = 2;
                } else if (this.owner.getLevel() >= 56) {
                    availableCount = 1;
                }
                if (this.getWeaponAwakenings().size() >= availableCount) {
                    return;
                }
                break;
            }
            default: {
                return;
            }
        }
        synchronized (this.skills) {
            if (this.skills.containsKey(skillId) && !this.awakeningSkills.containsKey(skillId)) {
                final AwakenedSkill awakeningSkill = AwakenedSkill.newAwakenedSkill(skillId, abilityId);
                if (awakeningSkill != null) {
                    this.awakeningSkills.put(awakeningSkill.getSkillId(), awakeningSkill);
                    this.owner.sendPacket(new SMSkillAwakening(awakeningSkill, this.getNormalAwakenings().size(), this.getWeaponAwakenings().size()));
                }
            }
        }
    }

    public void changeAweakenSkill(final int oldSkillId, final int newSkillId, final int abilityId) {
        synchronized (this.skills) {
            if (this.skills.containsKey(newSkillId) && !this.awakeningSkills.containsKey(newSkillId)) {
                final AwakenedSkill awakeningSkill = this.awakeningSkills.remove(oldSkillId);
                if (awakeningSkill != null) {
                    final AwakenedSkill newAwakeningSkill = AwakenedSkill.newAwakenedSkill(newSkillId, abilityId);
                    if (newAwakeningSkill != null) {
                        this.awakeningSkills.put(newAwakeningSkill.getSkillId(), newAwakeningSkill);
                        this.owner.sendPacket(new SMChangeAwakenSkill(oldSkillId, newSkillId, abilityId));
                    }
                }
            }
        }
    }

    public void reawakening(final int skillId, final int abilityId) {
        synchronized (this.skills) {
            final AwakenedSkill awakeningSkill = this.awakeningSkills.get(skillId);
            if (awakeningSkill != null) {
                awakeningSkill.reawakening(abilityId);
                this.owner.sendPacket(new SMChangeSkillAwakeningBitFlag(skillId, abilityId));
            }
        }
    }

    public boolean removeAwaken(final int skillId) {
        return this.awakeningSkills.remove(skillId) != null;
    }

    public Collection<AwakenedSkill> getAwakenings() {
        return this.awakeningSkills.values();
    }

    public Collection<AwakenedSkill> getNormalAwakenings() {
        return this.awakeningSkills.values().stream().filter(s -> s.getSkillT().getSkillAwakeningType().isNormal()).collect(Collectors.toList());
    }

    public Collection<AwakenedSkill> getWeaponAwakenings() {
        return this.awakeningSkills.values().stream().filter(s -> s.getSkillT().getSkillAwakeningType().isAwakeningWeapon()).collect((Collectors.toList()));
    }

    public CreatureSkill getCreatureSkill(final int skillId) {
        return this.skills.get(skillId);
    }

    public boolean containsAwaken(final int skillId) {
        return this.awakeningSkills.containsKey(skillId);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList skillsDB = new BasicDBList();
        for (final CreatureSkill skill : this.skills.values()) {
            if (skill.getTemplate().isPromptForLearning()) {
                skillsDB.add(skill.toDBObject());
            }
        }
        final BasicDBList awakeningDB = new BasicDBList();
        for (final AwakenedSkill awakeningSkill : this.awakeningSkills.values()) {
            final BasicDBObject obj = new BasicDBObject();
            obj.append("skillId", awakeningSkill.getSkillId());
            obj.append("abilityId", awakeningSkill.getAbilityId());
            awakeningDB.add(obj);
        }
        builder.append("skills", skillsDB);
        builder.append("awakenedSkills", awakeningDB);
        builder.append("skillExpLevel", this.skillExpLevel);
        builder.append("totalSkillPoints", this.totalSkillPoints);
        builder.append("availableSkillPoints", this.availableSkillPoints);
        builder.append("currentSkillPointsExp", this.currentSkillPointsExp);
        return builder.get();
    }
}
