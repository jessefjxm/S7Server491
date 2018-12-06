// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;

public class SMBroadcastLifeExperience extends SendablePacket<GameClient> {
    private Player player;
    private ELifeExpType lifeExpType;

    public SMBroadcastLifeExperience(final Player player, final ELifeExpType lifeExpType) {
        this.player = player;
        this.lifeExpType = lifeExpType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(0);
        buffer.writeS((CharSequence) this.player.getFamily(), 62);
        buffer.writeS((CharSequence) this.player.getName(), 62);
        buffer.writeD(this.lifeExpType.ordinal());
        buffer.writeD(4716214);
        buffer.writeD(0);
    }
}
