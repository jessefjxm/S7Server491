// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.gameserver.dataholders.WaypointDataOld;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.WaypointTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class PlantZoneWork extends ANpcWork {
    private int plantExchangeKey;
    private int nodeId;

    public PlantZoneWork(final ENpcWorkingType workType) {
        super(workType);
    }

    @Override
    public void load(final BasicDBObject dbObject) {
        this.plantExchangeKey = dbObject.getInt("plantExchangeKey");
        this.nodeId = dbObject.getInt("nodeId");
    }

    @Override
    public void read(final BuffReader buffReader) {
        this.plantExchangeKey = buffReader.readHD();
        this.nodeId = buffReader.readD();
        buffReader.readH();
        buffReader.readC();
        buffReader.readD();
        buffReader.readD();
    }

    @Override
    public boolean canAct(final NpcWorker npcWorker, final Player player) {
        final WaypointTemplate waypointTemplate = WaypointDataOld.getInstance().getTemplate(this.nodeId);
        if (waypointTemplate == null || !player.getExploration().contains(this.nodeId)) {
            waypointTemplate.getPlantZoneWorkingT().getTemplate().getExchangeGroupKey();
            return false;
        }
        return true;
    }

    public int getNodeId() {
        return this.nodeId;
    }

    public int getPlantExchangeKey() {
        return this.plantExchangeKey;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("workType", (Object) this.workType.name());
        builder.append("plantExchangeKey", (Object) this.plantExchangeKey);
        builder.append("nodeId", (Object) this.nodeId);
        return builder.get();
    }
}
