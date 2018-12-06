package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMCompleteGuildQuest;
import com.bdoemu.core.network.sendable.SMCompleteGuildQuest;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CompleteGuildQuestEvent implements IGuildEvent {
    private Guild _guild;
    private Player _player;
    private int _rewardSilverAmount;
    private ConcurrentLinkedQueue<Item> _addItemTasks;

    public CompleteGuildQuestEvent(final Guild guild, final Player player) {
        _addItemTasks = new ConcurrentLinkedQueue<Item>();
        _guild = guild;
        _player = player;
        _rewardSilverAmount = 0;
    }

    @Override
    public void onEvent() {
        if (!_addItemTasks.isEmpty())
            _guild.getGuildWarehouse().addItems(_addItemTasks, _player);
        _guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.COMPLETE_GUILD_QUEST, _guild, _player.getObjectId(), _rewardSilverAmount));
        World.getInstance().broadcastWorldPacket(new SMCompleteGuildQuest(_guild));

        if (_guild.getGuildQuest() != null)
            _guild.setGuildQuest(null);
    }

    @Override
    public boolean canAct() {
        if (!_player.getGuildMemberRankType().isMaster() && !_player.getGuildMemberRankType().isOfficer()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMCompleteGuildQuest.class));
            return false;
        }

        if (_guild.getGuildQuest() == null) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoQuestCantClear, CMCompleteGuildQuest.class));
            return false;
        }

        if (!_guild.getGuildQuest().isCompleteQuest() || !_guild.getGuildQuest().isExpired()) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoQuestDontComplete, CMCompleteGuildQuest.class));
            return false;
        }

        final RewardTemplate reward = _guild.getGuildQuest().getGuildQuestT().getRewardT();
        if (reward != null) {
            reward.getRewardItems(-1, _addItemTasks);

            for (Item itemAdded : _addItemTasks) {
                if (itemAdded.getItemId() == 1) {
                    _rewardSilverAmount = (int) itemAdded.getCount();
                    break;
                }
            }
        }

        if (!_guild.getGuildWarehouse().canAddItems(_addItemTasks)) {
            _player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoWarehouseIsntFull, CMCompleteGuildQuest.class));
            return false;
        }

        return GuildService.getInstance().containsGuild(_guild) && _guild == _player.getGuild();
    }
}
























