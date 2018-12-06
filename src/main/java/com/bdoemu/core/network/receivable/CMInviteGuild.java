package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMAskInviteGuild;
import com.bdoemu.core.network.sendable.SMRefuseInviteGuild;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMInviteGuild extends ReceivablePacket<GameClient> {
    private String  _name;
    private int     _term;
    private long    _dailyPay;
    private long    _penalty;

    public CMInviteGuild(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _name       = readS(62);
        _term       = readD();
        _dailyPay   = readQ();
        _penalty    = readQ();
    }

    public void runImpl() {
        final Player player = getClient().getPlayer();
        if (player != null) {
            final Player invited = World.getInstance().getPlayer(_name);
            if (invited != null) {
                final Guild guild = player.getGuild();
                if (guild != null) {
                    if (invited.getAccountData().getGuildCoolTime() > GameTimeService.getServerTimeInMillis()) {
                        player.sendPacket(new SMRefuseInviteGuild(invited.getName(), EStringTable.eErrNoGuildJoinableTimeDoNotGoes, invited.getAccountData().getGuildCoolTime() / 1000L));
                        return;
                    }

                    if (guild.getGuildQuest() != null)
                        return;

                    invited.sendPacket(new SMAskInviteGuild(invited.getName(), invited.getFamily(), guild.getName(), player.getGameObjectId(), _term, _dailyPay, _penalty, guild.getObjectId()));
                }
            }
        }
    }
}
