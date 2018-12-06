package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.DeclareGuildWarEvent;

public class CMDeclareGuildWar extends ReceivablePacket<GameClient> {
    private String _guildName;

    public CMDeclareGuildWar(final short opcode) {
        super(opcode);
    }

    protected void read() {
        readQ(); // Should be Guild ID, but is nulled instead.
        _guildName = readS(62);
    }

    public void runImpl() {
        if (getClient() == null)
            return;

        final Player player = ((GameClient) getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new DeclareGuildWarEvent(player, guild, _guildName));
            }
        }
    }
}
