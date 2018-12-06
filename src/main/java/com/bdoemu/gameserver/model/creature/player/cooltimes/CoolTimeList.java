package com.bdoemu.gameserver.model.creature.player.cooltimes;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoolTimeList extends JSONable {
    private final Map<Integer, CoolTime> itemCoolTimes;
    private final Map<Integer, CoolTime> skillCoolTimes;

    public CoolTimeList() {
        this.itemCoolTimes = new ConcurrentHashMap<>();
        this.skillCoolTimes = new ConcurrentHashMap<>();
    }

    public CoolTimeList(final BasicDBObject dbObject) {
        this.itemCoolTimes = new ConcurrentHashMap<>();
        this.skillCoolTimes = new ConcurrentHashMap<>();
        final BasicDBList itemCoolTimesDB = (BasicDBList) dbObject.get("itemCoolTimes");
        for (int i = 0; i < itemCoolTimesDB.size(); ++i) {
            final BasicDBObject itemCoolTimeDB = (BasicDBObject) itemCoolTimesDB.get(i);
            final int reuseGroup = itemCoolTimeDB.getInt("reuseGroup");
            final int remainingTime = itemCoolTimeDB.getInt("remainingTime");
            this.addItemCoolTime(reuseGroup, remainingTime);
        }
        final BasicDBList skillCoolTimesDB = (BasicDBList) dbObject.get("skillCoolTimes");
        for (int j = 0; j < skillCoolTimesDB.size(); ++j) {
            final BasicDBObject skillCoolTimeDB = (BasicDBObject) skillCoolTimesDB.get(j);
            final int skillId = skillCoolTimeDB.getInt("skillId");
            final int remainingTime2 = skillCoolTimeDB.getInt("remainingTime");
            this.addSkillCoolTime(skillId, remainingTime2);
        }
    }

    public Collection<CoolTime> getAllCoolTimes() {
        final List<CoolTime> coolTimes = new ArrayList<CoolTime>();
        coolTimes.addAll(this.skillCoolTimes.values());
        coolTimes.addAll(this.itemCoolTimes.values());
        return coolTimes;
    }

    public boolean addSkillCoolTime(final int skillId, final int reuseCycle) {
        final CoolTime ct = new CoolTime(reuseCycle, -1, skillId, 1);
        this.skillCoolTimes.put(skillId, ct);
        this.clearCoolTimes(this.skillCoolTimes);
        return true;
    }

    public void clearCoolTimes(final Map<Integer, CoolTime> map) {
        map.values().stream().filter(ct -> ct.getRemainigTime() <= 0L).forEach(ct -> map.values().remove(ct));
    }

    public void clearCoolTimes() {
        this.itemCoolTimes.clear();
        this.skillCoolTimes.clear();
    }

    public boolean addItemCoolTime(final int reuseGroup, final int reuseCycle) {
        if (this.hasItemCoolTime(reuseGroup)) {
            return false;
        }
        final CoolTime ct = new CoolTime(reuseCycle, reuseGroup, 0, 0);
        this.itemCoolTimes.put(reuseGroup, ct);
        this.clearCoolTimes(this.itemCoolTimes);
        return true;
    }

    public boolean hasSkillCoolTime(final int skillId) {
        final CoolTime ct = this.skillCoolTimes.get(skillId);
        return ct != null && ct.getRemainigTime() > 0L;
    }

    public boolean hasItemCoolTime(final int reuseGroup) {
        final CoolTime ct = this.itemCoolTimes.get(reuseGroup);
        return ct != null && ct.getRemainigTime() > 0L;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList skillCoolTimesDB = new BasicDBList();
        this.skillCoolTimes.values().stream().filter(coolTime -> coolTime.getRemainigTime() > 0L).forEach(coolTime -> {
            BasicDBObject obj = new BasicDBObject();
            obj.append("skillId", coolTime.getSkillId());
            obj.append("remainingTime", coolTime.getRemainigTime());
            skillCoolTimesDB.add(obj);
        });
        builder.append("skillCoolTimes", skillCoolTimesDB);
        final BasicDBList itemCoolTimesDB = new BasicDBList();
        this.itemCoolTimes.values().stream().filter(coolTime -> coolTime.getRemainigTime() > 0L).forEach(coolTime -> {
            BasicDBObject obj2 = new BasicDBObject();
            obj2.append("reuseGroup", coolTime.getReuseGroup());
            obj2.append("remainingTime", coolTime.getRemainigTime());
            itemCoolTimesDB.add(obj2);
        });
        builder.append("itemCoolTimes", itemCoolTimesDB);
        return builder.get();
    }
}
