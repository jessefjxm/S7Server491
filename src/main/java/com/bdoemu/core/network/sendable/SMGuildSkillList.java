// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMGuildSkillList extends SendablePacket<GameClient> {
    private Guild guild;

    public SMGuildSkillList(final Guild guild) {
        this.guild = guild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeH(this.guild.getGuildSkillList().getSkills().size());
        for (final SkillT skillT : this.guild.getGuildSkillList().getSkills()) {
            buffer.writeH(1);
            buffer.writeH(skillT.getSkillId());
        }
    }
}
