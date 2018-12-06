// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;

public class SMUpdateGuildQuest extends SendablePacket<GameClient> {
    private Guild guild;
    private Player player;

    public SMUpdateGuildQuest(final Guild guild, final Player player) {
        this.guild = guild;
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.player.getFamily(), 62);
        buffer.writeS((CharSequence) this.player.getName(), 62);
        buffer.writeQ(this.guild.getObjectId());
        final GuildQuest guildQuest = this.guild.getGuildQuest();
        if (guildQuest != null) {
            buffer.writeC(guildQuest.getGuildQuestT().isPreoccupancy());
            buffer.writeD(guildQuest.getQuestId());
            buffer.writeQ(guildQuest.getAcceptDate());
            for (final int step : guildQuest.getSteps()) {
                buffer.writeD(step);
            }
            buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
        } else {
            buffer.writeB(new byte[35]);
        }
    }
}
