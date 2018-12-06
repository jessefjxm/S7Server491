// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.CreatureSkill;

public class SMSkillList extends SendablePacket<GameClient> {
    private final Player player;

    public SMSkillList(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(this.player.getClassType().getId());
        buffer.writeH(this.player.getSkillList().getSkills().size());
        for (final CreatureSkill cs : this.player.getSkillList().getSkills()) {
            buffer.writeH(cs.getSkillLevel());
            buffer.writeH(cs.getSkillId());
            buffer.writeC(0);
        }
    }
}
