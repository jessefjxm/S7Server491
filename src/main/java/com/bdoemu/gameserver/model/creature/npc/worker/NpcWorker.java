package com.bdoemu.gameserver.model.creature.npc.worker;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.dataholders.*;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkerState;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerPassiveSkillT;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerSkillT;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerT;
import com.bdoemu.gameserver.model.creature.npc.worker.works.ANpcWork;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.service.FamilyService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class NpcWorker extends JSONable {
    private final CreatureTemplate creatureTemplate;
    private long objectId;
    private int actionPoints;
    private int level;
    private int exp;
    private int count;
    private int upgradeCount;
    private int workingState;
    private double workerSpeed;
    private ANpcWork npcWork;
    private PlantWorkerT plantWorkerT;
    private RegionTemplate regionTemplate;
    private long accountId;
    private long startTime;
    private long updateTime;
    private ENpcWorkerState state;
    private ConcurrentLinkedQueue<PlantWorkerPassiveSkillT> passiveSkills;
    private EAuctionRegisterType auctionRegisterType;

    public NpcWorker(final long objectId, final int characterKey, final int regionId, final long accountId) {
        this.workingState = -1;
        this.state = ENpcWorkerState.WorkSupervisor;
        this.passiveSkills = new ConcurrentLinkedQueue<>();
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.objectId = objectId;
        this.accountId = accountId;
        this.creatureTemplate = CreatureData.getInstance().getTemplate(characterKey);
        this.plantWorkerT = PlantWorkerData.getInstance().getTemplate(characterKey);
        this.actionPoints = this.plantWorkerT.getActionPoint();
        this.level = 1;
        this.exp = 1;
        this.regionTemplate = RegionData.getInstance().getTemplate(regionId);
        if (this.plantWorkerT.getDefaultSkillKey() != null) {
            this.passiveSkills.add(PlantWorkerData.getInstance().getPassiveSkill(this.plantWorkerT.getDefaultSkillKey()));
        } else {
            this.getNextSkill();
        }
        calculateAddedSpeed();
    }

    public NpcWorker(final NpcWorker npcWorker, final long accountId) {
        this.workingState = -1;
        this.state = ENpcWorkerState.WorkSupervisor;
        this.passiveSkills = new ConcurrentLinkedQueue<>();
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.objectId = npcWorker.getObjectId();
        this.accountId = accountId;
        this.creatureTemplate = npcWorker.getCreatureTemplate();
        this.plantWorkerT = npcWorker.getPlantWorkerT();
        this.actionPoints = npcWorker.getActionPoints();
        this.level = npcWorker.getLevel();
        this.exp = npcWorker.getExp();
        this.upgradeCount = npcWorker.getUpgradeCount();
        this.regionTemplate = npcWorker.getRegionTemplate();
        this.passiveSkills.addAll(npcWorker.getPassiveSkills());
        calculateAddedSpeed();
    }

    public NpcWorker(final BasicDBObject dbObject) {
        this.workingState = -1;
        this.state = ENpcWorkerState.WorkSupervisor;
        this.passiveSkills = new ConcurrentLinkedQueue<>();
        this.auctionRegisterType = EAuctionRegisterType.None;
        this.objectId = dbObject.getLong("_id");
        this.accountId = dbObject.getLong("accountId");
        this.creatureTemplate = CreatureData.getInstance().getTemplate(dbObject.getInt("characterKey"));
        this.plantWorkerT = PlantWorkerData.getInstance().getTemplate(dbObject.getInt("characterKey"));
        this.actionPoints = dbObject.getInt("actionPoints");
        this.level = dbObject.getInt("level");
        this.exp = dbObject.getInt("exp");
        this.workingState = dbObject.getInt("workingState", 0);
        this.auctionRegisterType = EAuctionRegisterType.valueOf(dbObject.getInt("auctionRegisterType", 0));
        if (dbObject.containsField("npcWork")) {
            final BasicDBObject dbnpcWork = (BasicDBObject) dbObject.get("npcWork");
            (this.npcWork = ENpcWorkingType.valueOf(dbnpcWork.getString("workType")).newNpcWorkInstance()).load(dbnpcWork);
        }
        this.startTime = dbObject.getLong("startTime");
        this.updateTime = dbObject.getLong("updateTime", 0L);
        this.upgradeCount = dbObject.getInt("upgradeCount");
        this.state = ENpcWorkerState.values()[dbObject.getInt("state", 0)];
        this.regionTemplate = RegionData.getInstance().getTemplate(dbObject.getInt("regionId"));
        final BasicDBList npcSkillsDBList = (BasicDBList) dbObject.get("npcSkills");
        for (final Object aNpcSkillsDBList : npcSkillsDBList) {
            final Integer skillId = (Integer) aNpcSkillsDBList;
            this.passiveSkills.add(PlantWorkerData.getInstance().getPassiveSkill(skillId));
        }
        calculateAddedSpeed();
    }

    public static NpcWorker newNpcWorker(final int characterKey, final int regionId, final long accountId) {
        return new NpcWorker(GameServerIDFactory.getInstance().nextId(GSIDStorageType.NpcWorker), characterKey, regionId, accountId);
    }

    public final PlantWorkerPassiveSkillT getNextSkill() {
        final List<PlantWorkerPassiveSkillT> skills = PlantWorkerData.getInstance().getPassiveSkills().stream().filter(passiveSkillT -> !this.passiveSkills.contains(passiveSkillT)).collect(Collectors.toList());
        final PlantWorkerPassiveSkillT skill = skills.get(Rnd.get(0, skills.size() - 1));
        this.passiveSkills.add(skill);
        return skill;
    }

    public double getWorkerSpeed() {
        PlantWorkerSkillT skillT = PlantWorkerData.getInstance().getSkill(getPlantWorkerT().getCharacterKey());
        return ((skillT == null ? 1 : skillT.getWorkingEfficiency()) + workerSpeed) / 1_000_000.0;
    }

    private void calculateAddedSpeed() {
        workerSpeed = 0;
        for (PlantWorkerPassiveSkillT passiveSkillT : passiveSkills) {
            if (passiveSkillT.getBuffType() == 0 && passiveSkillT.getBuffValue0() == 0 && passiveSkillT.getBuffValue2() == 1)
                workerSpeed += passiveSkillT.getBuffValue1();
        }
    }

    public RegionTemplate getRegionTemplate() {
        return this.regionTemplate;
    }

    public CreatureTemplate getCreatureTemplate() {
        return this.creatureTemplate;
    }

    public int getUpgradeCount() {
        return this.upgradeCount;
    }

    public void setUpgradeCount(final int upgradeCount) {
        this.upgradeCount = upgradeCount;
    }

    public int getUgradeChance() {
        return this.plantWorkerT.getUgradeChance(this.level);
    }

    public void stopWorking() {
        this.npcWork = null;
        this.startTime = 0L;
    }

    public PlantWorkerT getPlantWorkerT() {
        return this.plantWorkerT;
    }

    public void setPlantWorkerT(final PlantWorkerT plantWorkerT) {
        this.plantWorkerT = plantWorkerT;
    }

    public String getFamily() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }

    public ENpcWorkerState getState() {
        return this.state;
    }

    public void setState(final ENpcWorkerState state) {
        this.state = state;
    }

    public synchronized void addExp(final int exp) {
        if (this.level == 30) {
            return;
        }
        this.exp += exp;
        if (this.exp >= this.getPlantWorkerT().getMaxExp(this.level)) {
            ++this.level;
            this.exp = 0;
            if (this.level / 5 + 1 > this.passiveSkills.size()) {
                this.getNextSkill();
                calculateAddedSpeed();
            }
            if (this.getPlantWorkerT().getUpgradeCharacterKey() != null && this.level % 10 == 0) {
                ++this.upgradeCount;
            }
        }
    }

    public boolean addActions(final int actionPoints) {
        if (this.actionPoints + actionPoints < 0) {
            return false;
        }
        if (this.actionPoints + actionPoints > this.plantWorkerT.getActionPoint()) {
            this.actionPoints = this.plantWorkerT.getActionPoint();
            return true;
        }
        this.actionPoints += actionPoints;
        return true;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(final long updateTime) {
        this.updateTime = updateTime;
    }

    public int getWorkingState() {
        return this.workingState;
    }

    public void setWorkingState(final int workingState) {
        this.workingState = workingState;
    }

    public int getRegionId() {
        return this.regionTemplate.getRegionId();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getWaypointKey() {
        return this.regionTemplate.getWaypointKey();
    }

    public int getExp() {
        return this.exp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getActionPoints() {
        return this.actionPoints;
    }

    public void setActionPoints(final int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public int getCharacterKey() {
        return this.plantWorkerT.getCharacterKey();
    }

    public long getObjectId() {
        return this.objectId;
    }

    public ConcurrentLinkedQueue<PlantWorkerPassiveSkillT> getPassiveSkills() {
        return this.passiveSkills;
    }

    public ANpcWork getNpcWork() {
        return this.npcWork;
    }

    public void setNpcWork(final ANpcWork npcWork) {
        this.npcWork = npcWork;
    }

    public int[] getArrPassiveSkills() {
        final int[] array = new int[7];
        int index = 0;
        for (final PlantWorkerPassiveSkillT skill : this.passiveSkills) {
            array[index++] = skill.getSkillId();
        }
        return array;
    }

    public EAuctionRegisterType getAuctionRegisterType() {
        return this.auctionRegisterType;
    }

    public void setAuctionRegisterType(final EAuctionRegisterType auctionRegisterType) {
        this.auctionRegisterType = auctionRegisterType;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.objectId);
        builder.append("accountId", this.accountId);
        builder.append("characterKey", this.getCharacterKey());
        builder.append("actionPoints", this.actionPoints);
        builder.append("regionId", this.getRegionId());
        builder.append("level", this.level);
        builder.append("exp", this.exp);
        builder.append("startTime", this.startTime);
        builder.append("updateTime", this.updateTime);
        builder.append("state", this.state.ordinal());
        builder.append("workingState", this.workingState);
        builder.append("auctionRegisterType", this.auctionRegisterType.ordinal());
        final ANpcWork _npcWork = this.npcWork;
        if (_npcWork != null) {
            builder.append("npcWork", _npcWork.toDBObject());
        }
        builder.append("upgradeCount", this.upgradeCount);
        final BasicDBList npcSkillsDBList = new BasicDBList();
        for (final PlantWorkerPassiveSkillT passiveSkill : this.passiveSkills) {
            npcSkillsDBList.add(passiveSkill.getSkillId());
        }
        builder.append("npcSkills", npcSkillsDBList);
        return builder.get();
    }
}
