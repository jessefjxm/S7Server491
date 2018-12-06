// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.world.Location;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class HarvestWorkingWork extends ANpcWork {
    private long tentObjId;
    private Location loc;

    public HarvestWorkingWork(final ENpcWorkingType workType) {
        super(workType);
    }

    @Override
    public void load(final BasicDBObject dbObject) {
        this.tentObjId = dbObject.getLong("tentObjId");
        this.loc = new Location((BasicDBObject) dbObject.get("location"));
    }

    @Override
    public void read(final BuffReader buffReader) {
        this.tentObjId = buffReader.readQ();
        buffReader.readC();
        buffReader.readD();
        buffReader.readD();
    }

    @Override
    public boolean canAct(final NpcWorker npcWorker, final Player player) {
        final HouseHold houseHold = player.getHouseholdController().getHouseHold(this.tentObjId);
        if (houseHold == null) {
            return false;
        }
        this.loc = new Location(houseHold.getLocation());
        return true;
    }

    public long getTentObjId() {
        return this.tentObjId;
    }

    public Location getLoc() {
        return this.loc;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("workType", (Object) this.workType.name());
        builder.append("tentObjId", (Object) this.tentObjId);
        builder.append("location", (Object) this.loc.toDBObject());
        return builder.get();
    }
}
