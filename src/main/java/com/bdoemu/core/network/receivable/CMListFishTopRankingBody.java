// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListFishTopRankingBody;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.fishing.FishingTopRank;
import com.bdoemu.gameserver.model.creature.player.fishing.services.FishingService;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class CMListFishTopRankingBody extends ReceivablePacket<GameClient> {
    private int key;

    public CMListFishTopRankingBody(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.key = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getEncyclopediaStorage().hasEncyclopedia(this.key)) {
            final FishingTopRank fishingTopRank = FishingService.getInstance().getTopRank(this.key);
            if (fishingTopRank != null) {
                player.sendPacket(new SMListFishTopRankingBody(fishingTopRank.getFishingTopRankBodies(), this.key, EPacketTaskType.Add));
            }
        }
    }
}
