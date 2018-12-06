// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.GuildSkillList;

public class SMGuildSkillPoint extends SendablePacket<GameClient> {
    private GuildSkillList guildSkillList;
    private int type;

    public SMGuildSkillPoint(final GuildSkillList guildSkillList, final int type) {
        this.guildSkillList = guildSkillList;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.guildSkillList.getSkillExpLevel());
        buffer.writeH(this.guildSkillList.getAvailableSkillPoints());
        buffer.writeH(this.guildSkillList.getTotalSkillPoints());
        buffer.writeD(this.guildSkillList.getCurrentSkillPointsExp());
        buffer.writeC(this.type);
    }
}
