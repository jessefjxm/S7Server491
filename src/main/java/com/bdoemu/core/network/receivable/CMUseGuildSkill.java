// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.team.guild.Guild;

import java.util.Collections;

public class CMUseGuildSkill extends ReceivablePacket<GameClient> {
    private int skillLevel;
    private int skillId;
    private int unk;

    public CMUseGuildSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillLevel = this.readH();
        this.skillId = this.readHD();
        this.unk = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getGuildMemberRankType().isMaster()) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                final SkillT skillT = guild.getGuildSkillList().getSkill(this.skillId);
                if (skillT != null && skillT.getSkillType().isActive()) {
                    for (final Player member : guild.getMembersOnline()) {
                        SkillService.useSkill(player, skillT, null, Collections.singletonList(member));
                    }
                }
            }
        }
    }
}
