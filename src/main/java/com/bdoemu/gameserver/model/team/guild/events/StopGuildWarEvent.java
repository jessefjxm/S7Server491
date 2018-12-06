package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMStopGuildWar;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMStopGuildWar;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.model.GuildWar;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;

public class StopGuildWarEvent implements IGuildEvent {
    private Player _player;
    private Guild _guild;
    private Guild _targetGuild;
    private long _targetGuildObjectId;
    private GuildWar _targetGuildWar;

    public StopGuildWarEvent(final Player player, final Guild guild, long objId) {
        _player = player;
        _guild = guild;
        _targetGuild = null;
        _targetGuildWar = null;
        _targetGuildObjectId = objId;
    }

    @Override
    public void onEvent() {
        if (_targetGuild != null && _targetGuildWar != null) {
            // Remove the guild war.
            //removeGuildWarEntry(targetWar.getObjectId());
            //targetGuild.removeGuildWarEntry(getObjectId());

            // Send message to everyone.
            World.getInstance().broadcastWorldPacket(new SMStopGuildWar(_guild, _targetGuild));

            // TODO: Fix enum, im sure its wrong.

            // Update target guild
            //_targetGuild.sendBroadcastPacket(new SMNotifyGuildInfo(_guild, EGuildNotifyType.UNK22, 0, -1, "", _guild.getName()));

            // Notify our guild
            //_guild.sendBroadcastPacket(new SMNotifyGuildInfo(_guild, EGuildNotifyType.UNK22, 0, -1, "", _targetGuild.getName()));
        }
    }

    @Override
    public boolean canAct() {
        // Check authority.
        if (!_player.getGuildMemberRankType().isMaster() && !_player.getGuildMemberRankType().isOfficer()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMStopGuildWar.class));
            return false;
        }

        _targetGuild = GuildService.getInstance().getGuild(_targetGuildObjectId);

        // Guild does not exist.
        if (_targetGuild == null) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildNotExist, CMStopGuildWar.class));
            return false;
        }

        // Check if same guild
        if (_guild.getObjectId() == _targetGuild.getObjectId()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDontDeclareGuildWarByTargetMine, CMStopGuildWar.class));
            return false;
        }

        // Check if timer run out.
        _targetGuildWar = _guild.getGuildWar(_targetGuild.getObjectId());
        if (_targetGuildWar != null) {
            if (_targetGuildWar.canStop())
                return true;
        } else {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDontStopGuildWar, CMStopGuildWar.class));
            return false;
        }
        return true;
    }
}