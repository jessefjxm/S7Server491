package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.Collection;

public class SMResetInstanceSummonActor extends SendablePacket<GameClient> {
    private final Player owners;

    public SMResetInstanceSummonActor(final Player owners) {
        this.owners = owners;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(owners.getGameObjectId());
        buffer.writeQ(0); // owners.getObjectId()
    }
}
