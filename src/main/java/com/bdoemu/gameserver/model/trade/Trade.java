package com.bdoemu.gameserver.model.trade;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.trade.events.ITradeEvent;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class Trade {
    private final CloseableReentrantLock lock;
    private ConcurrentHashMap<Player, PlayerTradeBag> trades;

    public Trade(final Player owner, final Player invitor) {
        this.trades = new ConcurrentHashMap<>();
        this.lock = new CloseableReentrantLock();
        this.trades.put(owner, new PlayerTradeBag(owner));
        this.trades.put(invitor, new PlayerTradeBag(invitor));
    }

    public void onEvent(final ITradeEvent event) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (event.canAct()) {
                event.onEvent();
            }
        }
    }

    public Item getItem(final Player player, final int tradeSlot) {
        return this.getPlayerTradeBag(player).getItems()[tradeSlot];
    }

    public Item removeItem(final Player player, final int tradeSlot) {
        final Item item = this.getPlayerTradeBag(player).getItems()[tradeSlot];
        this.getPlayerTradeBag(player).getItems()[tradeSlot] = null;
        return item;
    }

    public boolean putItem(final Player player, final Item item, final int tradeSlot) {
        if (item == null || this.getItem(player, tradeSlot) != null) {
            return false;
        }
        for (final Item tradeItem : this.getPlayerTradeBag(player).getItems()) {
            if (tradeItem != null && tradeItem == item) {
                return false;
            }
        }
        this.getPlayerTradeBag(player).getItems()[tradeSlot] = item;
        return true;
    }

    public PlayerTradeBag getPlayerTradeBag(final Player player) {
        return this.trades.get(player);
    }

    public boolean lock(final Player player) {
        return this.getPlayerTradeBag(player).lock();
    }

    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
        for (final Player player : this.trades.keySet()) {
            player.sendPacket(sp);
        }
    }

    public Player getPartner(final Player player) {
        final Enumeration<Player> elements = this.trades.keys();
        final Player partner = elements.nextElement();
        if (partner == player) {
            return elements.nextElement();
        }
        return partner;
    }
}
