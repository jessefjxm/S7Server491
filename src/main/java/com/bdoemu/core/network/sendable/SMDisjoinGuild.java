// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class SMDisjoinGuild extends SendablePacket<GameClient> {
    private final Player player;
    private final Guild guild;

    public SMDisjoinGuild(final Player player, final Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.player.getAccountId());
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.guild.getObjectId());
        buffer.writeQ(this.player.getCache());
    }
}
