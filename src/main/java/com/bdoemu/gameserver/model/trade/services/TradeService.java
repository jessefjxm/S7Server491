package com.bdoemu.gameserver.model.trade.services;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMCancelItemExchangeWithPlayer;
import com.bdoemu.core.network.sendable.SMRespondItemExchangeWithPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.trade.Trade;

public class TradeService {
    public static synchronized void trade(final Player invited, final Player invitor) {
        if (invitor.hasTrade()) {
            invited.sendPacket(new SMCancelItemExchangeWithPlayer(EStringTable.eErrNoItemExchangeTargetIsDoing));
            return;
        }
        if (invited.hasTrade()) {
            invited.sendPacket(new SMCancelItemExchangeWithPlayer(EStringTable.eErrNoItemExchangeAlreadyIsDoing));
            return;
        }
        final Trade trade = new Trade(invitor, invited);
        invitor.setTrade(trade);
        invited.setTrade(trade);
        invitor.sendPacket(new SMRespondItemExchangeWithPlayer(invited.getGameObjectId()));
    }
}
