// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.world.region.GameSector;

public class SMRemoveCollectInfo extends SendablePacket<GameClient> {
    private Collect collect;
    private int subSectorX;
    private int subSectorY;

    public SMRemoveCollectInfo(final Collect collect) {
        this.collect = collect;
        final GameSector gameSector = collect.getLocation().getGameSector();
        this.subSectorX = gameSector.getSubSectorX();
        this.subSectorY = gameSector.getSubSectorY();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.subSectorX);
        buffer.writeH(this.subSectorY);
        buffer.writeH(this.collect.getIndex());
        buffer.writeC(6);
    }
}
