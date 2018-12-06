package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.StopGuildWarEvent;

public class CMStopGuildWar extends ReceivablePacket<GameClient> {
    private long _guildId;

    public CMStopGuildWar(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _guildId = readQ();
    }

    public void runImpl() {
        if (getClient() == null)
            return;

        final Player player = ((GameClient) getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new StopGuildWarEvent(player, guild, _guildId));
            }
        }
    }
}
