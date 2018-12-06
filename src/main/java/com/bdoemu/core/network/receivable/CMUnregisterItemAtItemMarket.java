// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UnregisterItemAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMUnregisterItemAtItemMarket extends ReceivablePacket<GameClient> {
    private long objectId;
    private int sessionId;

    public CMUnregisterItemAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.objectId = this.readQ();
        this.readQ();
        this.readH();
        this.readH();
        this.readQ();
        this.readH();
        this.sessionId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
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
            player.getPlayerBag().onEvent(new UnregisterItemAtItemMarketEvent(player, this.objectId, territoryKey));
        }
    }
}
