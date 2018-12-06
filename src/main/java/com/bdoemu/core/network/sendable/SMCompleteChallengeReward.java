// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.challenge.AChallenge;

public class SMCompleteChallengeReward extends SendablePacket<GameClient> {
    private final AChallenge challenge;

    public SMCompleteChallengeReward(final AChallenge challenge) {
        this.challenge = challenge;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.challenge.getChallengeId());
        buffer.writeQ(this.challenge.getCompleteDate());
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(0);
    }
}
