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

public class SMAddTents extends SendablePacket<GameClient> {
    public static final int maximum = 4;
    private final Collection<HouseHold> tents;

    public SMAddTents(final Collection<HouseHold> tents) {
        if (tents.size() > 4) {
            throw new IllegalArgumentException("Maximum size entries size is 4");
        }
        this.tents = tents;
    }

    public static int getMaximum() {
        return 4;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(0);
        buffer.writeH(this.tents.size());
        for (final HouseHold tent : this.tents) {
            final Location location = tent.getLocation();
            buffer.writeD(tent.getGameObjectId());
            buffer.writeD(-1024);
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeD(tent.getCreatureId());
            buffer.writeF(tent.getGameStats().getHp().getCurrentHp());
            buffer.writeF(tent.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(tent.getGuildCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(tent.getPartyCache());
            buffer.writeC(-1);
            buffer.writeQ(tent.getObjectId());
            final List<HouseInstallation> installations = tent.getInstallations();
            for (int index = 0; index < 10; ++index) {
                HouseInstallation houseInstallation = null;
                if (index < installations.size()) {
                    houseInstallation = tent.getInstallations().get(index);
                }
                buffer.writeQ(72057559678189568L);
                buffer.writeQ(16777208L);
                buffer.writeB(31);
                buffer.writeH((houseInstallation != null) ? houseInstallation.getCharacterKey() : 0);
                buffer.writeB(39);
                buffer.writeF(1);
                buffer.writeB(16);
                buffer.writeF(1);
                buffer.writeB(18);
                buffer.writeF(1);
                buffer.writeD(0);
                buffer.writeF((houseInstallation != null) ? houseInstallation.getLoc().getX() : 0.0);
                buffer.writeF((houseInstallation != null) ? houseInstallation.getLoc().getZ() : 0.0);
                buffer.writeF((houseInstallation != null) ? houseInstallation.getLoc().getY() : 0.0);
                buffer.writeF((houseInstallation != null) ? houseInstallation.getLoc().getCos() : 0.0);
                buffer.writeD(0);
                buffer.writeH(0);
                buffer.writeQ((houseInstallation != null) ? houseInstallation.getObjectId() : 0L);
                buffer.writeQ((houseInstallation != null) ? tent.getAccountId() : -1L);
                buffer.writeS((CharSequence) ((houseInstallation != null) ? tent.getFamily() : ""), 62);
                buffer.writeQ((houseInstallation != null) ? tent.getObjectId() : 0L);
                buffer.writeH(0);
                buffer.writeQ(-1L);
                buffer.writeH(-1);
                buffer.writeH(-1);
                buffer.writeQ((houseInstallation != null) ? houseInstallation.getInstallDate() : 0L);
                buffer.writeH(0);
                buffer.writeQ(0L);
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
                buffer.writeB(59);
            }
            buffer.writeB(3);
            buffer.writeQ(tent.getEndDate());
            buffer.writeB(30);
            buffer.writeD(tent.getFertilizer());
            buffer.writeQ(0L);
            buffer.writeQ(tent.getGuildCache());
            buffer.writeQ(tent.getAccountId());
            buffer.writeS((CharSequence) "", 62);
            buffer.writeS((CharSequence) tent.getFamily(), 62);
            buffer.writeD(0);
        }
    }
}
