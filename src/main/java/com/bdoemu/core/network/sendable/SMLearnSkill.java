// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMLearnSkill extends SendablePacket<GameClient> {
    private final Player player;
    private final int skillId;
    private final int skillLevel;
    private final int skillPoints;

    public SMLearnSkill(final Player player, final int skillId, final int skillLevel, final int skillPoints) {
        this.player = player;
        this.skillId = skillId;
        this.skillLevel = skillLevel;
        this.skillPoints = skillPoints;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(this.skillLevel);
        buffer.writeH(this.skillId);
        buffer.writeH(this.skillPoints);
        buffer.writeD(0);
        buffer.writeD(this.player.getGameObjectId());
    }
}
