// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMPlayerLogOnOff extends SendablePacket<GameClient> {
    private final Player player;
    private final boolean logIn;

    public SMPlayerLogOnOff(final Player player, final boolean logIn) {
        this.player = player;
        this.logIn = logIn;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.player.getAccountId());
        buffer.writeQ(this.player.getObjectId());
        buffer.writeQ(this.logIn ? GameTimeService.getServerTimeInSecond() : 0L);
        buffer.writeQ(this.logIn ? 0L : GameTimeService.getServerTimeInSecond());
        buffer.writeH(this.logIn ? this.player.getMaxWp() : -1);
        buffer.writeH(this.logIn ? this.player.getExplorePointHandler().getMainExplorePoint().getMaxExplorePoints() : -1);
        buffer.writeS(this.player.getName(), 62);
        buffer.writeD(this.logIn ? this.player.getLevel() : 0);
        buffer.writeQ(this.player.getGuildCache());
        buffer.writeS(this.player.getFamily(), 62);
        buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        buffer.writeH(this.player.getCreatureId());
    }
}
