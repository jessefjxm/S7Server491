// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBObject;

public abstract class ANpcWork extends JSONable {
    protected ENpcWorkingType workType;

    public ANpcWork(final ENpcWorkingType workType) {
        this.workType = workType;
    }

    public ENpcWorkingType getWorkType() {
        return this.workType;
    }

    public boolean canStopWork() {
        return false;
    }

    public abstract void load(final BasicDBObject p0);

    public abstract void read(final BuffReader p0);

    public boolean canAct(final NpcWorker npcWorker, final Player player) {
        return true;
    }
}
