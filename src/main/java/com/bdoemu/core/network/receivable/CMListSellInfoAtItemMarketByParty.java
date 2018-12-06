// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListSellInfoAtItemMarketByParty;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class CMListSellInfoAtItemMarketByParty extends ReceivablePacket<GameClient> {
    private int npcGameObjId;

    public CMListSellInfoAtItemMarketByParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (!ItemMarketConfig.ITEM_MARKET_BUY_ENABLED) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoAuctionNotBuy, this.opCode));
                return;
            }
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null || function.getTerritoryKeyForItemMarket() == null) {
                return;
            }
            final Collection<PartyItemMarket> partyItems = ItemMarketService.getInstance().getSellByPartyItems();
            if (!partyItems.isEmpty()) {
                final ListSplitter<PartyItemMarket> splitterItems = (ListSplitter<PartyItemMarket>) new ListSplitter((Collection) partyItems, 739);
                while (splitterItems.hasNext()) {
                    player.sendPacket(new SMListSellInfoAtItemMarketByParty(splitterItems.getNext(), splitterItems.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, 0, player));
                }
            } else {
                player.sendPacket(new SMListSellInfoAtItemMarketByParty(partyItems, EPacketTaskType.Add, 0, player));
            }
        }
    }
}
