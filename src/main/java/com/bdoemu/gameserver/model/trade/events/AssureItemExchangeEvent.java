package com.bdoemu.gameserver.model.trade.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMAssureItemExchangeWithPlayer;
import com.bdoemu.core.network.sendable.SMCancelItemExchangeWithPlayer;
import com.bdoemu.core.network.sendable.SMCompleteItemExchangeWithPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CompleteTradeItemEvent;
import com.bdoemu.gameserver.model.trade.PlayerTradeBag;
import com.bdoemu.gameserver.model.trade.Trade;

public class AssureItemExchangeEvent implements ITradeEvent {
    private Player player;
    private Player partner;
    private Trade trade;
    private PlayerTradeBag tradeBag;
    private PlayerTradeBag partnerTradeBag;

    public AssureItemExchangeEvent(final Player player, final Trade trade) {
        this.player = player;
        this.trade = trade;
        this.partner = trade.getPartner(player);
        this.tradeBag = trade.getPlayerTradeBag(player);
        this.partnerTradeBag = trade.getPlayerTradeBag(this.partner);
    }

    @Override
    public void onEvent() {
        this.tradeBag.assure();
        this.partner.sendPacket(new SMAssureItemExchangeWithPlayer());
        if (this.tradeBag.isAssure() && this.partnerTradeBag.isAssure()) {
            if (!this.player.getPlayerBag().onEvent(new CompleteTradeItemEvent(this.player, this.partner, this.trade))) {
                this.trade.sendBroadcastPacket(new SMCancelItemExchangeWithPlayer(EStringTable.eErrNoInventoryIsntEqual));
            }
            this.trade.sendBroadcastPacket(new SMCompleteItemExchangeWithPlayer());
        }
    }

    @Override
    public boolean canAct() {
        return this.player.getTrade() == this.trade && this.tradeBag.isLock() && this.partnerTradeBag.isLock() && !this.tradeBag.isAssure();
    }
}
