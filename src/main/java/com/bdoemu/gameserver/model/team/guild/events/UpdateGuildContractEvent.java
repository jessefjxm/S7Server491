package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.service.GameTimeService;

/**
 * @author Nullbyte
 */

public class UpdateGuildContractEvent implements IGuildEvent {
    private Guild       _guild;
    private GuildMember _member;
    private Player      _player;
    private int         _term;
    private long        _benefit;
    private long        _penalty;

    public UpdateGuildContractEvent(Player player, Guild guild, int term, long benefit, long penalty) {
        _guild      = guild;
        _player     = player;
        _member     = null;
        _term       = term;
        _benefit    = benefit;
        _penalty    = penalty;
    }

    @Override
    public void onEvent() {
        _member.updateContract(3600 * 24 * _term + (int) GameTimeService.getServerTimeInSecond(), _term, _benefit, _penalty);
        _guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.WHO_JOIN, _guild, _player.getAccountId(), _member.getContractEndDate(), _benefit, _penalty, _member.getContractEndDate(), _term));
    }

    @Override
    public boolean canAct() {
        if (_guild.getGuildQuest() != null)
            return false;

        _member = _guild.getMember(_player.getAccountId());
        return _member != null;
    }
}