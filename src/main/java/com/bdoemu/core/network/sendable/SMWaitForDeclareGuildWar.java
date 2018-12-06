package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMWaitForDeclareGuildWar extends SendablePacket<GameClient> {
    private Guild _srcGuild;
    private Guild _dstGuild;

    public SMWaitForDeclareGuildWar(Guild srcGuild, Guild dstGuild) {
        _srcGuild = srcGuild;
        _dstGuild = dstGuild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(_srcGuild.getObjectId());
        buffer.writeS(_srcGuild.getName(), 62);
        buffer.writeQ(_dstGuild.getObjectId());
        buffer.writeS(_dstGuild.getName(), 62);
        buffer.writeC(0);
    }
}
