package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.SuggestGuildContractEvent;
import com.bdoemu.gameserver.model.team.guild.events.UpdateGuildContractEvent;
import com.bdoemu.gameserver.worldInstance.World;

public class CMUpdateGuildContract extends ReceivablePacket<GameClient> {
    private boolean _contractAccepted;
    private long    _guildId;
    private int     _sessionId;
    private int     _term;
    private long    _benefit;
    private long    _penalty;

    public CMUpdateGuildContract(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _contractAccepted   = readC() == 1;
        readQ();
        _sessionId          = readD();
        _term               = readHD();
        _benefit            = readQ();
        _penalty            = readQ();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();

            if (guild != null) {
                Player inviterPlayer = World.getInstance().getPlayer(_sessionId);
                if (inviterPlayer != null && !inviterPlayer.getGuildMemberRankType().isMaster() && !inviterPlayer.getGuildMemberRankType().isOfficer()) {
                    inviterPlayer.sendPacket(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMSuggestGuildContract.class));
                    return;
                }

                if (_contractAccepted)
                    guild.onEvent(new UpdateGuildContractEvent(player, guild, _term, _benefit, _penalty));
                else
                    player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoRejectGuildContract, this.opCode));
            }
        }
    }
}
