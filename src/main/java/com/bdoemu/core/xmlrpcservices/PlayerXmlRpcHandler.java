package com.bdoemu.core.xmlrpcservices;

import com.bdoemu.commons.model.xmlrpc.BaseXmlRpcHandler;
import com.bdoemu.commons.model.xmlrpc.impl.XmlRpcPlayer;
import com.bdoemu.commons.xmlrpc.XmlRpcHandler;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.AccountData;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@XmlRpcHandler
public class PlayerXmlRpcHandler extends BaseXmlRpcHandler {
    public String getPlayersByAccountId(final long accountId) {
        final AccountData accountData = AccountsDBCollection.getInstance().load(accountId);
        final ConcurrentHashMap<Long, Player> players = PlayersDBCollection.getInstance().loadByAccountId(accountId);
        if (!players.isEmpty()) {
            final List<XmlRpcPlayer> xmlRpcPlayers = new ArrayList<XmlRpcPlayer>();
            for (final Player player : players.values()) {
                final XmlRpcPlayer xmlRpcPlayer = player.toXMLRpcObject("");
                xmlRpcPlayer.setMaxWp(player.getBaseWp() + accountData.getMentalCardHandler().getWpBonus());
                xmlRpcPlayers.add(xmlRpcPlayer);
            }
            return this.json(xmlRpcPlayers);
        }
        return this.json(this.getMessageOk());
    }
}
