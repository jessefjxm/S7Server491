// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListContentsBattleRank;
import com.bdoemu.core.network.sendable.SMListContentsLocalWarRank;
import com.bdoemu.core.network.sendable.SMListContentsMoneyRank;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EContentsRankType;
import com.bdoemu.gameserver.service.LifeRankService;

import java.util.List;

public class CMListContentsRank extends ReceivablePacket<GameClient> {
    private EContentsRankType contentsRankType;

    public CMListContentsRank(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.contentsRankType = EContentsRankType.values()[this.readD()];
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final List<Player> players = LifeRankService.getInstance().getTopPlayerRanks(this.contentsRankType);
            switch (this.contentsRankType) {
                case MoneyRank: {
                    player.sendPacket(new SMListContentsMoneyRank(players));
                    break;
                }
                case BattleRank: {
                    player.sendPacket(new SMListContentsBattleRank(players));
                    break;
                }
                case LocalWarRank: {
                    player.sendPacket(new SMListContentsLocalWarRank(players));
                    break;
                }
            }
        }
    }
}
