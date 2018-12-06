package com.bdoemu.gameserver.model.trade.events;

import com.bdoemu.core.network.sendable.SMLockItemExchangeWithPlayer;
import com.bdoemu.core.network.sendable.SMUnlockItemExchangeWithPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.trade.PlayerTradeBag;
import com.bdoemu.gameserver.model.trade.Trade;

public class LockItemExchangeEvent implements ITradeEvent {
    private Trade trade;
    private Player player;
    private Player partner;
    private PlayerTradeBag tradeBag;
    private PlayerTradeBag partnerTradeBag;

    public LockItemExchangeEvent(final Trade trade, final Player player) {
        this.trade = trade;
        this.player = player;
        this.partner = trade.getPartner(player);
        this.tradeBag = trade.getPlayerTradeBag(player);
        this.partnerTradeBag = trade.getPlayerTradeBag(this.partner);
    }

    @Override
    public void onEvent() {
        if (this.trade.lock(this.player)) {
            this.partner.sendPacket(new SMLockItemExchangeWithPlayer());
        } else {
            this.partner.sendPacket(new SMUnlockItemExchangeWithPlayer());
        }
    }

    @Override
    public boolean canAct() {
        return this.player.getTrade() == this.trade && !this.tradeBag.isAssure() && !this.partnerTradeBag.isAssure();
    }
}
