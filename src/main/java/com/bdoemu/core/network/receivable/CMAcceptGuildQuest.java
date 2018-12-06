// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.AcceptGuildQuestEvent;

public class CMAcceptGuildQuest extends ReceivablePacket<GameClient> {
    private int questId;
    private int questLevel;
    private EItemStorageLocation storageLocation;

    public CMAcceptGuildQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.questId = this.readD();
        this.questLevel = this.readD();
        this.readC();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            if (guild != null) {
                guild.onEvent(new AcceptGuildQuestEvent(guild, player, this.questId));
            }
        }
    }
}
