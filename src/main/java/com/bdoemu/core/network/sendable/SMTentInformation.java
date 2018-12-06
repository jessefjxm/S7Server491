// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;
import java.util.List;

public class SMTentInformation extends SendablePacket<GameClient> {
    private final Collection<HouseHold> tents;

    public SMTentInformation(final Collection<HouseHold> tents) {
        this.tents = tents;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.tents.size());
        for (final HouseHold tent : this.tents) {
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(tent.getCreatureId());
            final Location location = tent.getLocation();
            buffer.writeD(tent.getGameObjectId());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeQ(tent.getObjectId());
            buffer.writeQ(tent.getEndDate());
            buffer.writeD(5);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeH(0);
            final List<HouseInstallation> installations = tent.getInstallations();
            for (int index = 0; index < 10; ++index) {
                HouseInstallation houseInstallation = null;
                if (index < installations.size()) {
                    houseInstallation = tent.getInstallations().get(index);
                }
                buffer.writeQ((houseInstallation != null) ? houseInstallation.getObjectId() : 0L);
                buffer.writeH((houseInstallation != null) ? houseInstallation.getCharacterKey() : 0);
                buffer.writeQ((houseInstallation != null) ? houseInstallation.getInstallDate() : 0L);
                buffer.writeQ(140697581816800L);
                buffer.writeQ((houseInstallation != null) ? houseInstallation.getUpdateDate() : 0L);
                buffer.writeD((houseInstallation != null) ? houseInstallation.getProgressPercentage() : 0);
                buffer.writeF(0);
                buffer.writeD(0);
                buffer.writeD(0);
                buffer.writeC(houseInstallation != null && houseInstallation.isPruning());
                buffer.writeC(houseInstallation != null && houseInstallation.isCatchBug());
                buffer.writeC(0);
                buffer.writeC(0);
                buffer.writeD(0);
            }
            buffer.writeQ(tent.getAccountId());
            buffer.writeS((CharSequence) tent.getFamily(), 62);
        }
    }
}
