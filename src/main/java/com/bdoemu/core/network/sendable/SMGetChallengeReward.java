// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMGetChallengeReward extends SendablePacket<GameClient> {
    private final int challengeId;

    public SMGetChallengeReward(final int challengeId) {
        this.challengeId = challengeId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.challengeId);
    }
}
