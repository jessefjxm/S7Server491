package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNotifyGuildNotice;
import com.bdoemu.core.network.sendable.SMRefreshGuildBasicCache;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNoticeType;

public class CMSetGuildNotice extends ReceivablePacket<GameClient> {
    private EGuildNoticeType type;
    private String message;

    public CMSetGuildNotice(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = EGuildNoticeType.values()[this.readC()];
        this.message = this.readS(602);
    }

    public void runImpl() {
        if (getClient() == null)
            return;

        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild == null) {
                return;
            }
            if (!player.getGuildMemberRankType().isMaster() && !player.getGuildMemberRankType().isOfficer()) {
                return;
            }
            if (this.type == EGuildNoticeType.NOTICE) {
                guild.setNotice(this.message);
                guild.sendBroadcastPacket(new SMNotifyGuildNotice(guild));
            } else {
                guild.setDescription(this.message);
                guild.sendBroadcastPacket(new SMRefreshGuildBasicCache(guild));
            }
        }
    }
}
