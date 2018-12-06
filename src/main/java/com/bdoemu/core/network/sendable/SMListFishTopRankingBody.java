// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.fishing.FishingTopRankBody;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMListFishTopRankingBody extends SendablePacket<GameClient> {
    private Collection<FishingTopRankBody> fishingTopRankBodies;
    private EPacketTaskType packetTaskType;
    private int key;

    public SMListFishTopRankingBody(final Collection<FishingTopRankBody> fishingTopRankBodies, final int key, final EPacketTaskType packetTaskType) {
        this.fishingTopRankBodies = fishingTopRankBodies;
        this.packetTaskType = packetTaskType;
        this.key = key;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.key);
        buffer.writeH(this.fishingTopRankBodies.size());
        for (final FishingTopRankBody fishingTopRankBody : this.fishingTopRankBodies) {
            buffer.writeQ(fishingTopRankBody.getTime());
            buffer.writeD(fishingTopRankBody.getMaxFishSize());
            buffer.writeF(fishingTopRankBody.getX());
            buffer.writeF(fishingTopRankBody.getZ());
            buffer.writeF(fishingTopRankBody.getY());
            buffer.writeQ(fishingTopRankBody.getAccountId());
            buffer.writeS((CharSequence) fishingTopRankBody.getFamilyName(), 62);
            buffer.writeQ(fishingTopRankBody.getCharacterObjectId());
            buffer.writeS((CharSequence) fishingTopRankBody.getCharacterName(), 62);
        }
    }
}
