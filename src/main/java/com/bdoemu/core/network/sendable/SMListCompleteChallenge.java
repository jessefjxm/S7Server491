// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.challenge.AChallenge;

import java.util.Collection;

public class SMListCompleteChallenge extends SendablePacket<GameClient> {
    private final Collection<AChallenge> challenges;

    public SMListCompleteChallenge(final Collection<AChallenge> challenges) {
        this.challenges = challenges;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(this.challenges.size());
        for (final AChallenge challenge : this.challenges) {
            buffer.writeH(challenge.getChallengeId());
            buffer.writeQ(challenge.getCompleteDate());
        }
    }
}
