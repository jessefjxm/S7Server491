package com.bdoemu.gameserver.model.skills;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.gameserver.dataholders.VehicleSkillData;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillOwnerT;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServantSkillList extends JSONable {
    private final ConcurrentHashMap<Integer, ServantSkill> servantSkills;
    private final Servant owner;
    private final VehicleSkillOwnerT vehicleSkillOwnerT;

    public ServantSkillList(final Servant owner) {
        this.servantSkills = new ConcurrentHashMap<Integer, ServantSkill>();
        this.owner = owner;
        this.vehicleSkillOwnerT = VehicleSkillData.getInstance().getTemplate(owner.getCreatureId());
        if (this.vehicleSkillOwnerT != null) {
            int skillMaxCount = owner.getServantSetTemplate().getBasicLearnSkillMaxCount();
            final int basicSkill = owner.getServantSetTemplate().getBasicLearnSkillKey();
            if (basicSkill > 0) {
                this.addSkill(basicSkill);
            } else {
                while (skillMaxCount > 0) {
                    this.learnRndSkill(false);
                    --skillMaxCount;
                }
            }
        }
    }

    public ServantSkillList(final Servant owner, final Collection<ServantSkill> skillList) {
        this.servantSkills = new ConcurrentHashMap<Integer, ServantSkill>();
        this.owner = owner;
        this.vehicleSkillOwnerT = VehicleSkillData.getInstance().getTemplate(owner.getCreatureId());
        for (final ServantSkill servantSkill : skillList) {
            this.servantSkills.put(servantSkill.getSkillId(), new ServantSkill(this.vehicleSkillOwnerT, servantSkill.getSkillId(), servantSkill.getExp()));
        }
    }

    public ServantSkillList(final Servant owner, final BasicDBObject basicDBObject) {
        this.servantSkills = new ConcurrentHashMap<Integer, ServantSkill>();
        this.owner = owner;
        this.vehicleSkillOwnerT = VehicleSkillData.getInstance().getTemplate(owner.getCreatureId());
        final BasicDBList dbList = (BasicDBList) basicDBObject.get("servantSkills");
        if (this.vehicleSkillOwnerT != null) {
            for (int index = 0; index < dbList.size(); ++index) {
                final ServantSkill servantSkill = new ServantSkill(this.vehicleSkillOwnerT, (BasicDBObject) dbList.get(index));
                this.servantSkills.put(servantSkill.getSkillId(), servantSkill);
            }
        }
    }

    public Collection<ServantSkill> getSkills() {
        return this.servantSkills.values();
    }

    public boolean containsSkill(final int skillId) {
        return this.servantSkills.containsKey(skillId);
    }

    public ServantSkill removeSkill(final int skillId) {
        return this.servantSkills.remove(skillId);
    }

    public ServantSkill getSkill(final int skillId) {
        return this.servantSkills.get(skillId);
    }

    public int getSkillExp(final int skillId) {
        final ServantSkill servantSkill = this.servantSkills.get(skillId);
        return (servantSkill != null) ? servantSkill.getExp() : 0;
    }

    public boolean isCannotChange(final int skillId) {
        final ServantSkill servantSkill = this.servantSkills.get(skillId);
        return servantSkill != null && servantSkill.isCannotChange();
    }

    public List<Integer> getPossibleLearnSkills() {
        final List<Integer> skillsForLearning = new ArrayList<Integer>();
        final VehicleSkillOwnerT vehicleSkillOwnerT = VehicleSkillData.getInstance().getTemplate(this.owner.getCreatureId());
        if (vehicleSkillOwnerT != null) {
            for (int i = 0; i < vehicleSkillOwnerT.getIsLearn().length; ++i) {
                final int skillId = i + 1;
                if (vehicleSkillOwnerT.getIsLearn()[i] == 1 && !this.servantSkills.containsKey(skillId)) {
                    skillsForLearning.add(skillId);
                }
            }
        }
        return skillsForLearning;
    }

    public ServantSkill changeSkill(int hopeSkill) {
        int hopeRate = ServantConfig.HOPE_RATE * this.owner.getHope();
        List<Integer> skillsPossibleForLearning = this.getPossibleLearnSkills();
        if (skillsPossibleForLearning.isEmpty()) {
            return null;
        }
        List<Integer> skillsForLearning = Collections.emptyList();
        int tryCount = 0;
        long chance = Rnd.get(1000000);
        while (skillsForLearning.isEmpty()) {
            int baseChance = (this.owner.isImprint() ? ServantConfig.VEHICLE_LEARN_SKILL_ADD_RATE_FROM_IMPIRIT : 0) + tryCount * 50000;
            skillsForLearning = this.getPossibleLearnSkills().stream().filter(
                    skillId -> chance <= (long) (
                            (skillId == hopeSkill ? hopeRate : 0)
                                    + baseChance
                                    + this.vehicleSkillOwnerT.getAbles()[skillId - 1]
                                    * (ServantConfig.VEHICLE_LEARN_SKILL_RATE / 1000000.0)
                    )).collect(Collectors.toList());
            ++tryCount;
        }
        return this.addSkill(skillsForLearning.get(Rnd.get(0, skillsForLearning.size() - 1)));
    }

    public ServantSkill learnRndSkill(boolean isLearnFromItem) {
        long chance = Rnd.get(1000000);
        List<Integer> skillsForLearning = this.getPossibleLearnSkills().stream().filter(
                skillId -> (!isLearnFromItem
                        || VehicleSkillData.getInstance().getSkillTemplate(skillId.intValue()).isLearnFromItem()
                ) && chance <= (long) (this.vehicleSkillOwnerT.getAbles()[skillId - 1] * (ServantConfig.VEHICLE_LEARN_SKILL_RATE / 1000000.0)
                        + (this.owner.isImprint() ? ServantConfig.VEHICLE_LEARN_SKILL_ADD_RATE_FROM_IMPIRIT : 0))
        ).collect(Collectors.toList());
        if (!skillsForLearning.isEmpty())
            return this.addSkill(skillsForLearning.get(Rnd.get(0, skillsForLearning.size() - 1)));
        return null;
    }

    public ServantSkill addSkill(final int skillId) {
        return this.servantSkills.computeIfAbsent(skillId, integer -> new ServantSkill(this.vehicleSkillOwnerT, skillId, 0));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.add("servantSkills", this.servantSkills.values().stream().map(ServantSkill::toDBObject).collect(Collectors.toCollection(BasicDBList::new)));
        return builder.get();
    }
}
