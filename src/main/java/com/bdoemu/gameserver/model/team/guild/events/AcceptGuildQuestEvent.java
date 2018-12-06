package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptGuildQuest;
import com.bdoemu.core.network.sendable.SMAcceptGuildQuest;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.dataholders.GuildQuestData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.guildquests.GuildQuest;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class AcceptGuildQuestEvent implements IGuildEvent {
    private Guild _guild;
    private Player _player;
    private int _questId;
    private GuildQuest _guildQuest;

    public AcceptGuildQuestEvent(final Guild guild, final Player player, final int questId) {
        _guild = guild;
        _player = player;
        _questId = questId;
    }

    @Override
    public void onEvent() {
        if (_guild.getGuildWarehouse().canDecreaseItem(0, _guildQuest.getGuildQuestT().getNeedItemCount()))
            _guild.getGuildWarehouse().decreaseItem(0, _guildQuest.getGuildQuestT().getNeedItemCount());
        else
            _player.getPlayerBag().getInventory().decrease(0, _guildQuest.getGuildQuestT().getNeedItemCount());
        _guild.setGuildQuest(_guildQuest);
        _guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.ACCEPT_GUILD_QUEST, _guild, (int) _guildQuest.getGuildQuestT().getNeedItemCount(), 4, 1));
        _guild.sendBroadcastPacket(new SMAcceptGuildQuest(_guild.getGuildQuest()));
    }

    @Override
    public boolean canAct() {
        if (_guild.getMembersOnline().stream().filter(gm -> _guild.getMember(gm.getAccountId()) != null && _guild.getMember(gm.getAccountId()).isContractValid()).count() < 3) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoGuildQuestRestrictByMemberCount, CMAcceptGuildQuest.class));
            return false;
        }

        // Contract expired. Unable to contribute to the guild mission.
        if (_player.getGuildMember() != null && !_player.getGuildMember().isContractValid()) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoDoNotGuildQuestByContractableTime, CMAcceptGuildQuest.class));
            return false;
        }

        // You do not have authority to use guild features.
        if (!_player.getGuildMemberRankType().isMaster() && !_player.getGuildMemberRankType().isOfficer()) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMAcceptGuildQuest.class));
            return false;
        }

        // After entering a field, missions will be restricted for 10 minutes.
        if (_player.getExistenceTime() < 600) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoGuildQuestRestrictByEnterField, CMAcceptGuildQuest.class));
            return false;
        }

        // Cannot apply for another quest for 10 minutes after finishing a quest.
        if (_guild.getLastGuildQuestCompletedTime() + 600 > System.currentTimeMillis() / 1000) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoRestrictByGuildQuestComplete, CMAcceptGuildQuest.class));
            return false;
        }

        // Another quest is currently in progress.
        if (_guild.getGuildQuest() != null) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoOtherGuildQuestisProgressing, CMAcceptGuildQuest.class));
            return false;
        }

        // An exception occurred during script execution.
        final GuildQuestT questTemplate = GuildQuestData.getInstance().getTemplate(_questId);
        if (!GuildService.getInstance().containsGuild(_guild) || _guild != _player.getGuild() || questTemplate == null) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoScriptRaiseException, CMAcceptGuildQuest.class));
            return false;
        }

        // The Guild Funds are invalid.
        if (!_guild.getGuildWarehouse().canDecreaseItem(0, questTemplate.getNeedItemCount()) &&
                !_player.getPlayerBag().getInventory().canDecreaseItem(0, questTemplate.getNeedItemCount())) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoGuildBusinessFundsIsInvalid, CMAcceptGuildQuest.class));
            return false;
        }
        _guildQuest = GuildService.getInstance().newGuildQuest(questTemplate, _guild);

        // Another guild has acquired the mission first.
        if (questTemplate.isPreoccupancy() && _guildQuest == null) {
            _player.sendPacket(new SMNak(EStringTable.eErrNoOtherGuildisPreoccupancy, CMAcceptGuildQuest.class));
            return false;
        }

        return _guildQuest != null;
    }
}