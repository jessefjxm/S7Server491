// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.CreatureSkill;

public class SMRefreshPcLearnedActiveSkillsCache extends SendablePacket<GameClient> {
    private final Player player;

    public SMRefreshPcLearnedActiveSkillsCache(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.player.getObjectId());
        buffer.writeD(this.player.getLearnSkillCacheCount());
        buffer.writeH(this.player.getSkillList().getSkills().size());
        for (final CreatureSkill skill : this.player.getSkillList().getSkills()) {
            buffer.writeH(skill.getSkillLevel());
            buffer.writeH(skill.getSkillId());
        }
    }
}
