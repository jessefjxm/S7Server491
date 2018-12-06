// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListLifeRank;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.service.LifeRankService;

public class CMListLifeRank extends ReceivablePacket<GameClient> {
    private ELifeExpType lifeExpType;

    public CMListLifeRank(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.lifeExpType = ELifeExpType.valueOf(this.readCD());
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMListLifeRank(LifeRankService.getInstance().getTopPlayerRanks(this.lifeExpType), this.lifeExpType, player));
        }
    }
}
