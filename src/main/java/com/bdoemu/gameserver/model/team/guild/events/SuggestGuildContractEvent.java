package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptGuildQuest;
import com.bdoemu.core.network.receivable.CMSuggestGuildContract;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.service.GameTimeService;

public class SuggestGuildContractEvent implements IGuildEvent {
    private Guild _guild;
    private Player _player;
    private long _memberAccountId;
    private int _contractPeriod;
    private long _dailyPayment;
    private long _penaltyCost;
    private GuildMember _member;

    public SuggestGuildContractEvent(Player player, Guild guild, long memberAccountId, int contractPeriod, long dailyPayment, long penaltyCost) {
        _guild = guild;
        _player = player;
        _memberAccountId = memberAccountId;
        _contractPeriod = contractPeriod;
        _dailyPayment = dailyPayment;
        _penaltyCost = penaltyCost;
    }

    @Override
    public void onEvent() {
        if (_player.getAccountId() == _memberAccountId) {
            GuildMember member = _player.getGuildMember();
            if (member != null) {
                member.updateContract(3600 * 24 * _contractPeriod + (int) GameTimeService.getServerTimeInSecond(), _contractPeriod, _dailyPayment, _penaltyCost);
                _player.sendPacket(new SMNotifyGuildInfo(EGuildNotifyType.LEFT_HIMSELF, _guild, _guild.getName(), _player.getName(), _contractPeriod, _dailyPayment, _penaltyCost));
                _guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.WHO_JOIN, _guild, _player.getAccountId(), member.getContractEndDate(), _dailyPayment, _penaltyCost, member.getContractEndDate(), _contractPeriod));
            }
        } else {
            if (_member.getPlayer() != null)
                _member.getPlayer().sendPacket(new SMNotifyGuildInfo(EGuildNotifyType.LEFT_HIMSELF, _guild, _guild.getName(), _player.getName(), _contractPeriod, _dailyPayment, _penaltyCost));
        }

        // Collect guild silver..
        //_guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.JOIN, _guild, _player.getAccountId(), _dailyPayment));
    }

    @Override
    public boolean canAct() {
        _member = _guild.getMember(_memberAccountId);
        if (_member == null) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoGuildIsntMember, CMSuggestGuildContract.class));
            return false;
        }

        if (!_player.getGuildMemberRankType().isMaster() && !_player.getGuildMemberRankType().isOfficer()) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMSuggestGuildContract.class));
            return false;
        }

        return _guild.getGuildQuest() == null;
    }
}














