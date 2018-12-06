package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMRaisingGuildGrade;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.core.network.sendable.SMRefreshGuildBasicCache;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CreateGuildItemEvent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;

public class RaisingGuildGradeEvent implements IGuildEvent {
    private Player player;
    private Guild guild;

    public RaisingGuildGradeEvent(final Player player, final Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    @Override
    public void onEvent() {
        this.guild.setGuildType(EGuildType.Guild);
        this.guild.recalculateGuildBasicCacheCount();
        this.guild.getGuildWarehouse().addItem(new Item(1, 80000L, 0), 0);
        this.guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.CLAN_TO_GUILD, this.guild));
        this.guild.sendBroadcastPacket(new SMRefreshGuildBasicCache(this.guild));
    }

    @Override
    public boolean canAct() {
        if (!this.guild.getGuildType().isClan()) {
            this.player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoDoNotHaveGuildRight, CMRaisingGuildGrade.class));
            return false;
        }

        if (!GuildService.getInstance().containsGuild(this.guild)) {
            this.player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildNotExist, CMRaisingGuildGrade.class));
            return false;
        }

        if (this.player.getGuild() != this.guild) {
            this.player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildAllianceNotExist, CMRaisingGuildGrade.class));
            return false;
        }

        if (this.guild.getLeaderAccountId() != this.player.getAccountId()) {
            this.player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoCanDoOnlyGuildMaster, CMRaisingGuildGrade.class));
            return false;
        }

        if (!this.player.getPlayerBag().onEvent(new CreateGuildItemEvent(this.player))) {
            this.player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoGuildBusinessFundsIsLack, CMRaisingGuildGrade.class));
            return false;
        }

        return true;
    }
}