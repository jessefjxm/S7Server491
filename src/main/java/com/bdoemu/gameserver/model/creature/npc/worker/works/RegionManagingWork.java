// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class RegionManagingWork extends ANpcWork {
    public RegionManagingWork(final ENpcWorkingType workType) {
        super(workType);
    }

    @Override
    public void load(final BasicDBObject dbObject) {
    }

    @Override
    public void read(final BuffReader buffReader) {
        buffReader.skipAll();
    }

    public DBObject toDBObject() {
        return null;
    }
}
