// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;

public class SMItemTransferLifeExperience extends SendablePacket<GameClient> {
    private ELifeExpType lifeExpType;

    public SMItemTransferLifeExperience(final ELifeExpType lifeExpType) {
        this.lifeExpType = lifeExpType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.lifeExpType.ordinal());
    }
}
