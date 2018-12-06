// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.mongodb.BasicDBObject;

public interface INpcWork {
    void load(final BasicDBObject p0);

    void load(final Object... p0);

    ENpcWorkingType getWorkType();

    boolean canStopWork();
}
