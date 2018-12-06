package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.core.network.sendable.SMGiveupGuildQuest;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class GiveupGuildQuestEvent implements IGuildEvent {
    private Player _player;
    private Guild _guild;
    private GuildQuest _guildQuest;

    public GiveupGuildQuestEvent(final Player player, final Guild guild) {
        _player = player;
        _guild = guild;
    }

    @Override
    public void onEvent() {
        GuildService.getInstance().removeGuildQuest(_guildQuest);
        _guild.setGuildQuest(null);
        _guild.sendBroadcastPacket(new SMGiveupGuildQuest());
    }

    @Override
    public boolean canAct() {
        _guildQuest = _guild.getGuildQuest();
        return _guildQuest != null && GuildService.getInstance().containsGuild(_guild) && _guild == _player.getGuild() && (_player.getGuildMemberRankType().isMaster() || _player.getGuildMemberRankType().isOfficer());
    }
}