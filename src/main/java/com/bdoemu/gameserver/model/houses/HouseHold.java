// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.sendable.SMTentHarvestInformation;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.EquipSetOptionData;
import com.bdoemu.gameserver.dataholders.HouseData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.EInstallationType;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.InteriorRelationPointT;
import com.bdoemu.gameserver.model.creature.templates.ObjectTemplate;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.items.templates.EquipSetOptionT;
import com.bdoemu.gameserver.model.stats.containers.HouseholdGameStats;
import com.bdoemu.gameserver.model.stats.containers.HouseholdLifeStats;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HouseHold extends Creature {
    private final EFixedHouseType fixedHouseType;
    private final ConcurrentHashMap<Long, HouseInstallation> installations;
    private long accountId;
    private long date;
    private long endDate;
    private long cache;
    private int basePoints;
    private int setPoints;
    private int relationPoints;
    private int itemId;
    private int fertilizer;

    public HouseHold(final Player player, final int creatureId, final int itemId, final SpawnPlacementT spawnPlacementT) {
        super(-1024, CreatureData.getInstance().getTemplate(creatureId), spawnPlacementT);
        this.installations = new ConcurrentHashMap<Long, HouseInstallation>();
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.Household);
        this.date = GameTimeService.getServerTimeInSecond();
        this.accountId = player.getAccountId();
        this.cache = player.getCache();
        this.itemId = itemId;
        this.fixedHouseType = ((HouseData.getInstance().getHouse(creatureId) != null) ? EFixedHouseType.House : EFixedHouseType.Tent);
        if (this.fixedHouseType.isTent()) {
            this.refreshLifeTime();
        }
        this.setGameStats(new HouseholdGameStats(this));
        this.setLifeStats(new HouseholdLifeStats(this));
    }

    public HouseHold(final BasicDBObject dbObject) {
        super(-1024, CreatureData.getInstance().getTemplate((dbObject.getInt("houseId", 0) == 0) ? dbObject.getInt("creatureId") : dbObject.getInt("houseId")), new SpawnPlacementT(dbObject.containsField("location") ? new Location((BasicDBObject) dbObject.get("location")) : new Location()));
        this.installations = new ConcurrentHashMap<Long, HouseInstallation>();
        this.objectId = dbObject.getLong("_id");
        this.accountId = dbObject.getLong("accountId");
        this.date = dbObject.getLong("date");
        this.endDate = dbObject.getLong("endDate", 0L);
        this.itemId = dbObject.getInt("itemId", 0);
        this.fertilizer = dbObject.getInt("fertilizer", 0);
        this.setGameStats(new HouseholdGameStats(this));
        this.setLifeStats(dbObject.containsField("lifeStats") ? new HouseholdLifeStats(this, (BasicDBObject) dbObject.get("lifeStats")) : new HouseholdLifeStats(this));
        final BasicDBList installationsDB = (BasicDBList) dbObject.get("installations");
        for (final Object anInstallationsDB : installationsDB) {
            final BasicDBObject installationDB = (BasicDBObject) anInstallationsDB;
            final HouseInstallation installation = new HouseInstallation(installationDB);
            this.installations.put(installation.getObjectId(), installation);
        }
        this.recalculatePoints();
        this.fixedHouseType = EFixedHouseType.values()[dbObject.getInt("fixedHouseType", 1)];
    }

    public EFixedHouseType getFixedHouseType() {
        return this.fixedHouseType;
    }

    public int getInstalledMaxCount() {
        int installedMaxCount = 0;
        for (final HouseInstallation installation : this.installations.values()) {
            final Integer maxCount = installation.getObjectTemplate().getInstallationMaxCount();
            if (maxCount != null) {
                installedMaxCount += maxCount;
            }
        }
        return installedMaxCount;
    }

    public void onUpdate() {
        if (this.endDate > GameTimeService.getServerTimeInSecond()) {
            boolean hasScarecrow = false;
            boolean hasWaterway = false;
            for (final HouseInstallation installation : this.installations.values()) {
                if (installation.getObjectTemplate().getInstallationType().isScarecrow()) {
                    hasScarecrow = true;
                } else {
                    if (!installation.getObjectTemplate().getInstallationType().isWaterway()) {
                        continue;
                    }
                    hasWaterway = true;
                }
            }
            for (final HouseInstallation installation : this.installations.values()) {
                final EInstallationType installationType = installation.getObjectTemplate().getInstallationType();
                if (installationType.isHavest() || installationType.isLivestockHarvest()) {
                    installation.onUpdate(hasScarecrow, hasWaterway, this.fertilizer > 0);
                }
            }
            this.addFertilizer(-12000);
            final SMTentHarvestInformation tentHarvestInformation = new SMTentHarvestInformation(Collections.singleton(this));
            final SMTentInformation tentInformation = new SMTentInformation(Collections.singleton(this));
            this.sendBroadcastPacket(tentHarvestInformation);
            this.sendBroadcastPacket(tentInformation);
            final Player owner = World.getInstance().getPlayerByAccount(this.accountId);
            if (owner != null) {
                owner.sendPacket(tentHarvestInformation);
                owner.sendPacket(tentInformation);
            }
        }
    }

    @Override
    public void onSpawn() {
        super.onSpawn();
        this.gameObjectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures);
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Creatures, (long) this.getGameObjectId());
    }

    public final void refreshLifeTime() {
        this.endDate = GameTimeService.getServerTimeInSecond() + 604800L;
    }

    public void recalculatePoints() {
        int basePoints = 0;
        int setPoints = 0;
        int relationPoints = 0;
        final ConcurrentHashMap<Integer, List<Integer>> equipSetBuffs = new ConcurrentHashMap<Integer, List<Integer>>();
        for (final HouseInstallation installation : this.installations.values()) {
            final ObjectTemplate objectTemplate = installation.getObjectTemplate();
            basePoints += objectTemplate.getInteriorPoints();
            final int itemId = installation.getItemId();
            final EquipSetOptionT equipSetOptionT = EquipSetOptionData.getInstance().getTemplate(itemId);
            if (equipSetOptionT != null) {
                final List<Integer> setItems = equipSetBuffs.computeIfAbsent(equipSetOptionT.getIndex(), k -> new ArrayList());
                setItems.add(itemId);
                final Integer buff = equipSetOptionT.getBuffs().get(setItems.size());
                setPoints += ((buff == null) ? 0 : buff);
            }
            final HouseInstallation parentInstallation = this.getHouseInstallation(installation.getParentObjId());
            if (parentInstallation != null) {
                final InteriorRelationPointT relationPointT = installation.getRelationPointT();
                if (relationPointT == null) {
                    continue;
                }
                switch (parentInstallation.getObjectTemplate().getInstallationType()) {
                    case Carpenter: {
                        relationPoints += relationPointT.getOnDresser();
                        continue;
                    }
                    case Bed: {
                        relationPoints += relationPointT.getOnBed();
                        continue;
                    }
                    case Bookcase: {
                        relationPoints += relationPointT.getOnBookCase();
                        continue;
                    }
                    case Smithing: {
                        relationPoints += relationPointT.getOnChair();
                        continue;
                    }
                    case Weaving: {
                        relationPoints += relationPointT.getOnSofa();
                        continue;
                    }
                    case Treasure: {
                        relationPoints += relationPointT.getOnTable();
                        continue;
                    }
                    case Forging: {
                        relationPoints += relationPointT.getOnDiningTable();
                        continue;
                    }
                    case Alchemy1: {
                        relationPoints += relationPointT.getOnBedSideCabinet();
                        continue;
                    }
                    case Founding: {
                        relationPoints += relationPointT.getOnWardrobe();
                        continue;
                    }
                }
            }
        }
        this.setPoints = setPoints;
        this.basePoints = basePoints;
        this.relationPoints = relationPoints;
    }

    public int getFertilizer() {
        return this.fertilizer;
    }

    public void addFertilizer(final int fertilizer) {
        if (this.fertilizer + fertilizer > 1000000) {
            this.fertilizer = 1000000;
        } else if (this.fertilizer + fertilizer < 0) {
            this.fertilizer = 0;
        } else {
            this.fertilizer += fertilizer;
        }
    }

    public HouseInstallation getHouseInstallation(final long objectId) {
        return this.installations.get(objectId);
    }

    public HouseInstallation removeHouseInstallation(final long objectId) {
        return this.installations.remove(objectId);
    }

    public boolean removeHouseInstallation(final HouseInstallation houseInstallation) {
        return this.installations.values().remove(houseInstallation);
    }

    public List<HouseInstallation> getInstallations() {
        if (this.installations.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<HouseInstallation>(this.installations.values());
    }

    public ConcurrentHashMap<Long, HouseInstallation> getInstallationMap() {
        return this.installations;
    }

    public void putInstallation(final HouseInstallation houseInstallation) {
        this.installations.put(houseInstallation.getObjectId(), houseInstallation);
    }

    public String getFamily() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public int getItemId() {
        return this.itemId;
    }

    @Override
    public long getObjectId() {
        return this.objectId;
    }

    public long getDate() {
        return this.date;
    }

    public long getEndDate() {
        return this.endDate;
    }

    public long getAccountId() {
        return this.accountId;
    }

    @Override
    public long getCache() {
        return this.cache;
    }

    @Override
    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        return false;
    }

    @Override
    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        return false;
    }

    public int getInteriorPoints() {
        return this.basePoints + this.setPoints + this.relationPoints;
    }

    public int getSetPoints() {
        return this.setPoints;
    }

    public int getBasePoints() {
        return this.basePoints;
    }

    public int getRelationPoints() {
        return this.relationPoints;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", (Object) this.getObjectId());
        builder.append("creatureId", (Object) this.getTemplate().getCreatureId());
        builder.append("location", (Object) this.getLocation().toDBObject());
        builder.append("fixedHouseType", (Object) this.fixedHouseType.ordinal());
        builder.append("accountId", (Object) this.getAccountId());
        builder.append("date", (Object) this.getDate());
        builder.append("endDate", (Object) this.getEndDate());
        builder.append("itemId", (Object) this.itemId);
        builder.append("fertilizer", (Object) this.fertilizer);
        builder.append("interiorPoints", (Object) this.getInteriorPoints());
        final BasicDBList installationDBList = new BasicDBList();
        for (final HouseInstallation houseInstallation : this.installations.values()) {
            installationDBList.add((Object) houseInstallation.toDBObject());
        }
        builder.append("installations", (Object) installationDBList);
        return builder.get();
    }
}
