package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRefuseInviteGuild;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMAnswerInviteGuild extends ReceivablePacket<GameClient> {
    private int _actorId;
    private int _answer;
    private int _type;
    private int _contractPeriod;
    private long _dailyPayment;
    private long _penalty;

    public CMAnswerInviteGuild(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _actorId = readD();
        _answer = readD();
        readC();
        _type = readCD();
        _contractPeriod = readD();
        _dailyPayment = readQ();
        _penalty = readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Player invitor = World.getInstance().getPlayer(_actorId);
            if (invitor != null) {
                if (_answer > 0) {
                    GuildService.getInstance().inviteToGuild(invitor, player);
                } else {
                    invitor.sendPacket(new SMRefuseInviteGuild(player.getName(), EStringTable.eErrNoRequestRefuse, 0L));
                }
            }
        }
    }
}
