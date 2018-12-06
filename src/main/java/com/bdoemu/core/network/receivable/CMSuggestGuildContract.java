package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.SuggestGuildContractEvent;

public class CMSuggestGuildContract extends ReceivablePacket<GameClient> {
    private long _memberAccountId;
    private int _contractPeriod;
    private long _dailyPayment;
    private long _penaltyCost;

    public CMSuggestGuildContract(final short opcode) {
        super(opcode);
    }

    protected void read() {
        readQ(); //_memberId = readQ();
        _memberAccountId = readQ();
        _contractPeriod = readHD();
        _dailyPayment = readQ();
        _penaltyCost = readQ();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();

            if (guild != null) {
                guild.onEvent(new SuggestGuildContractEvent(player, guild, _memberAccountId, _contractPeriod, _dailyPayment, _penaltyCost));
            }
        }
    }
}
