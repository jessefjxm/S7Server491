// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.LearnGuildSkillEvent;

public class CMLearnGuildSkill extends ReceivablePacket<GameClient> {
    private int skillLevel;
    private int skillId;
    private int unk;

    public CMLearnGuildSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillLevel = this.readH();
        this.skillId = this.readHD();
        this.unk = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new LearnGuildSkillEvent(player, guild, this.skillId));
            }
        }
    }
}
