// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMAcceptQuest extends SendablePacket<GameClient> {
    private final int questGroup;
    private final int questId;

    public SMAcceptQuest(final int questGroup, final int questId) {
        this.questGroup = questGroup;
        this.questId = questId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.questGroup);
        buffer.writeH(this.questId);
    }
}
