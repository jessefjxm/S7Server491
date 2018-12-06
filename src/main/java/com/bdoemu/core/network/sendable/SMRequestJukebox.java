// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseInstallation;

public class SMRequestJukebox extends SendablePacket<GameClient> {
    private HouseInstallation houseInstallation;
    private int id;
    private int type;

    public SMRequestJukebox(final HouseInstallation houseInstallation, final int id, final int type) {
        this.id = id;
        this.type = type;
        this.houseInstallation = houseInstallation;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.id);
        buffer.writeQ(this.houseInstallation.getObjectId());
        buffer.writeC(this.type);
    }
}
