package com.bdoemu.gameserver.model.team.guild.events;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;

public class LoginGuildEvent implements IGuildEvent {
    private Guild guild;
    private Player player;
    private GuildMember member;
    private boolean isGuildJoin;

    public LoginGuildEvent(final Guild guild, final Player player, final boolean isGuildJoin) {
        this.guild = guild;
        this.player = player;
        this.isGuildJoin = isGuildJoin;
    }

    @Override
    public void onEvent() {
        this.guild.addOnlineMember(this.player);
        this.member.updateLoginInfo(this.player);
        this.member.setLastLoginDate(GameTimeService.getServerTimeInMillis());
        this.member.updateInfo(this.player);
        final ListSplitter<GuildMember> memberListSplitter = new ListSplitter<>(this.guild.getMembers().values(), SMGetGuildInformation.MAX_MEMBERS);
        while (memberListSplitter.hasNext()) {
            this.player.sendPacketNoFlush(new SMGetGuildInformation(this.guild, memberListSplitter.getNext()));
        }
        this.player.sendPacketNoFlush(new SMGuildSkillPoint(this.guild.getGuildSkillList(), 1));
        this.player.sendPacketNoFlush(new SMGuildSkillList(this.guild));
        this.player.sendPacketNoFlush(new SMUpdateGuildQuest(this.guild, this.player));
        if (this.guild != null && this.guild.getGuildQuest() != null)
            this.guild.getGuildQuest().registerObserverOnLogin(this.player);
        this.player.sendPacketNoFlush(new SMListWarringGuild(this.guild));
        this.player.sendPacketNoFlush(new SMNotifyGuildInfo(EGuildNotifyType.MEMBER_LOGIN, this.guild));
        if (this.isGuildJoin) {
            this.player.sendPacketNoFlush(new SMRespondToJoinGuild(this.guild, this.member));
        }
        this.guild.sendBroadcastPacket(new SMLoginGuild(this.member, this.guild));
        this.guild.getGuildSkillList().getSkills().stream().filter(skillT -> skillT.getSkillType().isPassive()).forEach(skillT -> this.member.addActiveBuffs(SkillService.useSkill(this.player, skillT, null, Collections.singletonList(this.player))));
    }

    @Override
    public boolean canAct() {
        this.member = this.guild.getMember(this.player.getAccountId());
        return GuildService.getInstance().containsGuild(this.guild) && this.member != null && !this.guild.isMemberOnline(this.player);
    }
}
