// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;

public class SMListFixedHouseInstallations extends SendablePacket<GameClient> {
    public static int MAX_COUNT;

    static {
        SMListFixedHouseInstallations.MAX_COUNT = 98;
    }

    private final Collection<HouseInstallation> houseInstallations;
    private final EPacketTaskType type;
    private final HouseHold houseHold;

    public SMListFixedHouseInstallations(final HouseHold houseHold, final Collection<HouseInstallation> houseInstallations, final EPacketTaskType type) {
        this.houseHold = houseHold;
        this.houseInstallations = houseInstallations;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.houseHold.getBasePoints());
        buffer.writeD(this.houseHold.getSetPoints());
        buffer.writeD(this.houseHold.getRelationPoints());
        buffer.writeC(this.type.ordinal());
        buffer.writeH(this.houseInstallations.size());
        for (final HouseInstallation houseInstallation : this.houseInstallations) {
            buffer.writeQ(houseInstallation.getObjectId());
            buffer.writeQ(-1L);
            buffer.writeD(0);
            buffer.writeH(houseInstallation.getCharacterKey());
            buffer.writeH(1);
            buffer.writeH(-1);
            final Location loc = houseInstallation.getLoc();
            buffer.writeF(loc.getX());
            buffer.writeF(loc.getZ());
            buffer.writeF(loc.getY());
            buffer.writeD(0);
            buffer.writeF(loc.getCos());
            buffer.writeD(0);
            buffer.writeF(loc.getSin());
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeQ(this.houseHold.getObjectId());
            buffer.writeH(this.houseHold.getCreatureId());
            buffer.writeQ(this.houseHold.getAccountId());
            buffer.writeS((CharSequence) this.houseHold.getFamily(), 62);
            buffer.writeQ(houseInstallation.getInstallDate());
            buffer.writeC(houseInstallation.getObjectKind());
            buffer.writeH(houseInstallation.getEndurance());
            buffer.writeQ(houseInstallation.getParentObjId());
        }
    }
}
