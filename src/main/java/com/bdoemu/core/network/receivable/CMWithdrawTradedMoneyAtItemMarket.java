// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.WithdrawTradedMoneyAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMWithdrawTradedMoneyAtItemMarket extends ReceivablePacket<GameClient> {
    private long revenue;
    private long itemMarketObjectId;
    private int sessionId;
    private EItemStorageLocation dstLocation;

    public CMWithdrawTradedMoneyAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemMarketObjectId = this.readQ();
        this.revenue = this.readQ();
        this.sessionId = this.readD();
        this.dstLocation = EItemStorageLocation.valueOf(this.readC());
        this.readQ();
        this.readH();
        this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && this.revenue > 0L) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.sessionId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null) {
                return;
            }
            final Integer territoryKey = function.getTerritoryKeyForItemMarket();
            if (territoryKey == null) {
                return;
            }
            player.getPlayerBag().onEvent(new WithdrawTradedMoneyAtItemMarketEvent(player, this.dstLocation, this.itemMarketObjectId, this.revenue, npc.getRegionId()));
        }
    }
}
