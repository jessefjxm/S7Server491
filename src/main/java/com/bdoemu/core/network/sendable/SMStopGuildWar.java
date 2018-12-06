package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMStopGuildWar extends SendablePacket<GameClient> {
    private Guild _srcGuild;
    private Guild _dstGuild;

    public SMStopGuildWar(Guild srcGuild, Guild dstGuild) {
        _srcGuild = srcGuild;
        _dstGuild = dstGuild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(_srcGuild.getObjectId());
        buffer.writeQ(_dstGuild.getObjectId());
        buffer.writeS(_srcGuild.getName());
        buffer.writeS(_dstGuild.getName());
    }
}
