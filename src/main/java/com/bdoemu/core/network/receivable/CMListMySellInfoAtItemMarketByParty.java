package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListSellInfoAtItemMarketByParty;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.team.party.IParty;

import java.util.Collection;
import java.util.stream.Collectors;

public class CMListMySellInfoAtItemMarketByParty extends ReceivablePacket<GameClient> {
    public CMListMySellInfoAtItemMarketByParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final IParty<Player> party = player.getParty();
            if (party != null) {
                final Collection<PartyItemMarket> partyItems = party.getPartyInventory().values().stream().filter(partyItemMarket -> partyItemMarket.containWinner(player.getAccountId())).collect(Collectors.toList());
                if (!partyItems.isEmpty()) {
                    final ListSplitter<PartyItemMarket> splitterItems = new ListSplitter<>(partyItems, 739);
                    while (splitterItems.hasNext()) {
                        player.sendPacket(new SMListSellInfoAtItemMarketByParty(splitterItems.getNext(), splitterItems.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, 1, player));
                    }
                } else {
                    player.sendPacket(new SMListSellInfoAtItemMarketByParty(partyItems, EPacketTaskType.Add, 1, player));
                }
            }
        }
    }
}
