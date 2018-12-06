package com.bdoemu.gameserver.model.skills.buffs;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.BuffData;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CreatureBuffList extends JSONable {
    private final Map<Integer, ActiveBuff> buffs;
    protected Creature owner;

    public CreatureBuffList(final Creature owner) {
        this.buffs = new ConcurrentHashMap<Integer, ActiveBuff>();
        this.owner = owner;
    }

    public CreatureBuffList(final BasicDBList buffListDb, final Creature owner) {
        this(owner);
        for (final Object aBuffListDb : buffListDb) {
            final BasicDBObject buffDB = (BasicDBObject) aBuffListDb;
            final int skillId = buffDB.getInt("skillId");
            final int buffId = buffDB.getInt("buffId");
            final int remainingTime = buffDB.getInt("remainingTime");
            final BuffTemplate buffTemplate = BuffData.getInstance().getTemplate(buffId);
            final SkillT skillT = SkillData.getInstance().getTemplate(skillId);
            if (buffTemplate != null && skillT != null && buffTemplate.getValidityTime() > 0L) {
                final ABuffEffect buffHandler = buffTemplate.getModuleType().getEffect();
                if (buffHandler == null) {
                    continue;
                }
                final Collection<ActiveBuff> activeBuffs = buffHandler.initEffect(owner, Collections.singleton(owner), skillT, buffTemplate);
                for (final ActiveBuff activeBuff : activeBuffs) {
                    activeBuff.setValidityTime(remainingTime);
                    this.addActiveBuff(activeBuff);
                }
            }
        }
    }

    public synchronized boolean addActiveBuff(final ActiveBuff activeBuff) {
        if (this.owner.getAi() != null && this.owner.getAi().getBehavior().isReturn()) {
            return false;
        }
        if (activeBuff.getTarget().isDead() && activeBuff.getTemplate().isRemoveOnDead()) {
            return false;
        }
        if (activeBuff.isActiveBuff()) {
            for (final ActiveBuff buff : this.buffs.values()) {
                if (buff.getBuffId() == activeBuff.getBuffId()) {
                    if (buff.refreshBuff()) {
                        return false;
                    }
                    break;
                }
            }
            if (activeBuff.getGroup() > 0) {
                final Optional<ActiveBuff> sameGroupBuff = this.buffs.values().stream().filter(activeBuff1 -> activeBuff1.getGroup() == activeBuff.getGroup()).findFirst();
                if (sameGroupBuff.isPresent()) {
                    if (activeBuff.getLevel() < sameGroupBuff.get().getLevel()) {
                        return false;
                    }
                    sameGroupBuff.get().unapplyEffect();
                }
            }
        }
        activeBuff.initGameObjectId();
        this.buffs.put(activeBuff.getGameObjectId(), activeBuff);
        return true;
    }

    public boolean removeActiveBuff(final ActiveBuff activeBuff) {
        return this.buffs.values().remove(activeBuff);
    }

    public void dispelBuff(final int groupId, final Integer level) {
        this.buffs.values().stream().filter(activeBuff -> activeBuff.getGroup() == groupId && (level == null || activeBuff.getTemplate().getLevel() <= level)).forEach(ActiveBuff::unapplyEffect);
    }

    public void dispelBuffs(final int skillId) {
        this.buffs.values().stream().filter(activeBuff -> activeBuff.getSkillT().getSkillId() == skillId).forEach(ActiveBuff::unapplyEffect);
    }

    public Collection<ActiveBuff> getBuffs() {
        if (this.buffs.isEmpty()) {
            return Collections.emptyList();
        }
        return this.buffs.values().stream().filter(ActiveBuff::isActiveBuff).collect(Collectors.toList());
    }

    public void dispelBuffs() {
        this.buffs.values().forEach(ActiveBuff::unapplyEffect);
    }

    public boolean hasBuff(final ModuleBuffType moduleType, final Integer... params) {
        return this.buffs.values().stream().anyMatch(buff -> buff.getTemplate().getModuleType() == moduleType && buff.getTemplate().isParamsEquals(params));
    }

    public ActiveBuff getBuff(final int buffIndex) {
        final Optional<ActiveBuff> activeBuff = this.buffs.values().stream().filter(buff -> buff.getBuffId() == buffIndex).findAny();
        return activeBuff.orElse(null);
    }

    public void stopBuffTasks() {
        this.buffs.values().forEach(ActiveBuff::stopTask);
    }

    public void removeOnDeathBuffs() {
        this.buffs.values().stream().filter(buff -> buff.getTemplate().isRemoveOnDead()).forEach(ActiveBuff::endEffect);
    }

    public DBObject toDBObject() {
        final BasicDBList buffList = new BasicDBList();
        this.getBuffs().stream().filter(ActiveBuff::isActiveBuff).forEach(buff -> {
            BasicDBObject obj = new BasicDBObject();
            obj.append("skillId", buff.getSkillT().getSkillId());
            obj.append("buffId", buff.getBuffId());
            obj.append("remainingTime", buff.getRemainingTime());
            buffList.add(obj);
        });
        return buffList;
    }
}
