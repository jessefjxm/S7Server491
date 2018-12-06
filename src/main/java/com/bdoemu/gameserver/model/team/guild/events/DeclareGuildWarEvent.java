package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMDeclareGuildWar;
import com.bdoemu.core.network.sendable.SMDeclareGuildWar;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;

public class DeclareGuildWarEvent implements IGuildEvent {
    private Player _player;
    private Guild _guild;
    private Guild _targetGuild;
    private String _targetGuildName;

    public DeclareGuildWarEvent(final Player player, final Guild guild, final String targetGuildName) {
        _player = player;
        _guild = guild;
        _targetGuild = null;
        _targetGuildName = targetGuildName;
    }

    @Override
    public void onEvent() {
        _player.sendPacket(new SMNak(EStringTable.eErrNoIsGoingToImplement, CMDeclareGuildWar.class));
        /*createGuildWarEntry(this, targetGuild);
        //createGuildWarEntry(targetGuild, this);

        // Set the declarer as warring guild
        //_hasDeclaredWarOnce = true;

        // Remove guild funds
        //_guild.setGuildFund(_guild.getGuildFund() - 150_000);

        // Send message to everyone.
        // TODO: Queue, SMWaitForDeclareGuildWar.
        World.getInstance().broadcastWorldPacket(new SMDeclareGuildWar(_guild, _targetGuild));

        // Update target guild
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.DECLARE_GUILD_WAR, _guild, _targetGuild.getName(), 0));

        // Notify our guild
        _guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.DECLARE_GUILD_WAR, _guild, _targetGuild.getName(), 150_000));

        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK19, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK20, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK23, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK25, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK26, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK28, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK29, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK30, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK31, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK32, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK33, _guild, _targetGuild.getName()));
        _targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.UNK34, _guild, _targetGuild.getName()));*/
    }

    @Override
    public boolean canAct() {
        // Guild War is disabled.
        //_player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMDeclareGuildWar.class));
        //return false;

        // Check authority.
        if (!_player.getGuildMemberRankType().isMaster() && !_player.getGuildMemberRankType().isOfficer()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMDeclareGuildWar.class));
            return false;
        }

        _targetGuild = GuildService.getInstance().getGuildByName(_targetGuildName);

        // Guild does not exist.
        if (_targetGuild == null) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildNotExist, CMDeclareGuildWar.class));
            return false;
        }

        // Check if same guild
        if (_guild.getObjectId() == _targetGuild.getObjectId()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDontDeclareGuildWarByTargetMine, CMDeclareGuildWar.class));
            return false;
        }

        // Check if target guild is clan.
        if (_targetGuild.getGuildType().isClan()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoCanNotDeclareWarGuildGradeIsLowed, CMDeclareGuildWar.class));
            return false;
        }

        // Check if our guild is clan.
        if (_guild.getGuildType().isClan()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoCanNotDeclareWarGuildGradeIsLowed, CMDeclareGuildWar.class));
            return false;
        }

        // Check if already exist.
        if (_guild.getGuildWar(_targetGuild.getObjectId()) != null) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDeclareGuildIsExist, CMDeclareGuildWar.class));
            return false;
        }

        // You cannot declare war on a guild that is not ready for a war yet.
        if (_guild.hasDeclaredWarOnce() && !_targetGuild.hasDeclaredWarOnce()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDontDeclareGuildWarByUnReadyTarget, CMDeclareGuildWar.class));
            return false;
        }

        // Check if we can declare more guild wars.
        if (_guild.getGuildWars().size() > 5) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDontMoreDeclareGuildWar, CMDeclareGuildWar.class));
            return false;
        }

        // Check if guild has enough silver to even start the war.
        /*if (_guild.getGuildFund() < 150_000) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildBusinessFundsIsLack, CMDeclareGuildWar.class));
            return false;
        }*/

        return true;
    }
}








