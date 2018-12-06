// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMClearSkillPartly extends SendablePacket<GameClient> {
    private final Player player;
    private final int skillLevel;
    private final int skillId;

    public SMClearSkillPartly(final Player player, final int skillLevel, final int skillId) {
        this.player = player;
        this.skillLevel = skillLevel;
        this.skillId = skillId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.skillLevel);
        buffer.writeH(this.skillId);
        buffer.writeD(this.player.getGameObjectId());
    }
}
