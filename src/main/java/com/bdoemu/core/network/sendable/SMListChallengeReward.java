// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.challenge.ChallengeReward;

import java.util.Collection;

public class SMListChallengeReward extends SendablePacket<GameClient> {
    private final Collection<ChallengeReward> challenges;

    public SMListChallengeReward(final Collection<ChallengeReward> challenges) {
        this.challenges = challenges;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(this.challenges.size());
        for (final ChallengeReward challenge : this.challenges) {
            buffer.writeH(challenge.getChallengeId());
            buffer.writeD(challenge.getRewardCount());
        }
    }
}
