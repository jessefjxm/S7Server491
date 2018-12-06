// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.InteriorRelationPointData;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.creature.templates.InteriorRelationPointT;
import com.bdoemu.gameserver.model.creature.templates.ObjectTemplate;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class HouseInstallation extends JSONable {
    private static int maxProgressPercentage;

    static {
        HouseInstallation.maxProgressPercentage = 2000000;
    }

    private final long objectId;
    private final long parentObjId;
    private final int itemId;
    private int endurance;
    private int progressPercentage;
    private Location loc;
    private long installDate;
    private long updateDate;
    private CreatureTemplate creatureTemplate;
    private boolean catchBug;
    private boolean pruning;

    public HouseInstallation(final long objectId, final int characterKey, final int itemId, final int endurance, final Location loc, final long parentObjId) {
        this.catchBug = false;
        this.pruning = false;
        this.objectId = objectId;
        this.loc = loc;
        this.creatureTemplate = CreatureData.getInstance().getTemplate(characterKey);
        this.itemId = itemId;
        this.endurance = endurance;
        this.parentObjId = parentObjId;
        this.installDate = GameTimeService.getServerTimeInSecond();
        this.updateDate = GameTimeService.getServerTimeInSecond();
    }

    public HouseInstallation(final BasicDBObject dbObject) {
        this.catchBug = false;
        this.pruning = false;
        this.objectId = dbObject.getLong("objectId");
        this.parentObjId = dbObject.getLong("parentObjId");
        this.creatureTemplate = CreatureData.getInstance().getTemplate(dbObject.getInt("characterKey"));
        this.itemId = dbObject.getInt("itemId");
        this.progressPercentage = dbObject.getInt("progressPercentage", 0);
        this.endurance = dbObject.getInt("endurance");
        this.installDate = dbObject.getLong("installDate");
        this.updateDate = dbObject.getLong("updateDate", 0L);
        this.pruning = dbObject.getBoolean("pruning", false);
        this.catchBug = dbObject.getBoolean("catchBug", false);
        this.loc = new Location((BasicDBObject) dbObject.get("loc"));
    }

    public static HouseInstallation newHouseInstallation(final int characterKey, final int itemId, final int endurance, final Location loc, final long parentObjId) {
        final long objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.HouseInstallation);
        return new HouseInstallation(objectId, characterKey, itemId, endurance, loc, parentObjId);
    }

    public void addProgressPercentage(final int percentage) {
        if (this.progressPercentage >= HouseInstallation.maxProgressPercentage) {
            return;
        }
        if (this.progressPercentage + percentage > HouseInstallation.maxProgressPercentage) {
            this.progressPercentage = HouseInstallation.maxProgressPercentage;
        } else {
            this.progressPercentage += percentage;
        }
    }

    public boolean isCatchBug() {
        return this.catchBug;
    }

    public void setCatchBug(final boolean catchBug) {
        this.catchBug = catchBug;
    }

    public boolean isPruning() {
        return this.pruning;
    }

    public void setPruning(final boolean pruning) {
        this.pruning = pruning;
    }

    public int getProgressPercentage() {
        return this.progressPercentage;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public void setEndurance(final int endurance) {
        this.endurance = endurance;
    }

    public int getItemId() {
        return this.itemId;
    }

    public Location getLoc() {
        return this.loc;
    }

    public void setLoc(final Location loc) {
        this.loc = loc;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public long getInstallDate() {
        return this.installDate;
    }

    public long getUpdateDate() {
        return this.updateDate;
    }

    public void onUpdate(final boolean hasScarecrow, final boolean hasWaterway, final boolean hasFertilizer) {
        this.updateDate = GameTimeService.getServerTimeInSecond();
        final int value = 12000 * (100 + (hasFertilizer ? 20 : 0)) / 100;
        if (!this.isPruning()) {
            this.addProgressPercentage(this.isCatchBug() ? (value / 2) : value);
        }
        this.pruning = (Rnd.get(1000000) <= (hasScarecrow ? (EtcOptionConfig.PRUNING_RATE / 2) : EtcOptionConfig.PRUNING_RATE));
        this.catchBug = (Rnd.get(1000000) <= (hasWaterway ? (EtcOptionConfig.CATCHBUG_RATE / 2) : EtcOptionConfig.CATCHBUG_RATE));
    }

    public int getCharacterKey() {
        return this.creatureTemplate.getCreatureId();
    }

    public ObjectTemplate getObjectTemplate() {
        return this.creatureTemplate.getObjectTemplate();
    }

    public long getParentObjId() {
        return this.parentObjId;
    }

    public int getObjectKind() {
        return this.getObjectTemplate().getObjectKind();
    }

    public InteriorRelationPointT getRelationPointT() {
        return InteriorRelationPointData.getInstance().getTemplate(this.getCharacterKey());
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("objectId", (Object) this.objectId);
        builder.append("parentObjId", (Object) this.parentObjId);
        builder.append("characterKey", (Object) this.getCharacterKey());
        builder.append("itemId", (Object) this.itemId);
        builder.append("endurance", (Object) this.endurance);
        builder.append("installDate", (Object) this.installDate);
        builder.append("updateDate", (Object) this.updateDate);
        builder.append("pruning", (Object) this.pruning);
        builder.append("catchBug", (Object) this.catchBug);
        builder.append("progressPercentage", (Object) this.progressPercentage);
        builder.append("loc", (Object) this.loc.toDBObject());
        return builder.get();
    }
}
