// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;

public class SMUnoccupyFixedHouseInstallation extends SendablePacket<GameClient> {
    private final HouseInstallation installation;
    private final HouseHold houseHold;

    public SMUnoccupyFixedHouseInstallation(final HouseHold houseHold, final HouseInstallation installation) {
        this.houseHold = houseHold;
        this.installation = installation;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.houseHold.getObjectId());
        buffer.writeH(this.installation.getCharacterKey());
        buffer.writeQ(this.installation.getObjectId());
        buffer.writeD(this.houseHold.getBasePoints());
        buffer.writeD(this.houseHold.getSetPoints());
        buffer.writeD(this.houseHold.getRelationPoints());
    }
}
