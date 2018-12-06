// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMBuyItNowServantAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMListServantInfo;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

public class BuyItNowServantAuctionItemEvent extends AItemEvent {
    private ServantItemMarket servantItemMarket;
    private EItemStorageLocation storageLocation;
    private Servant servant;

    public BuyItNowServantAuctionItemEvent(final Player player, final ServantItemMarket servantItemMarket, final EItemStorageLocation storageLocation, final int regionId) {
        super(player, player, player, EStringTable.eErrNoServantMatingGetMoney, EStringTable.eErrNoServantMatingGetMoney, regionId);
        this.servantItemMarket = servantItemMarket;
        this.storageLocation = storageLocation;
        this.servant = servantItemMarket.getServant();
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.getAccountData().setBuyServantAuctionCoolTime(GameTimeService.getServerTimeInMillis());
        this.servantItemMarket.setSold(true);
        final Player owner = World.getInstance().getPlayerByAccount(this.servantItemMarket.getAccountId());
        if (owner != null) {
            owner.getServantController().delete(this.servant);
            owner.sendPacket(new SMBuyItNowServantAuctionGoodsVer2(this.servant.getObjectId()));
        }
        MailService.getInstance().sendMail(this.servantItemMarket.getAccountId(), -1L, "{3183609639|309549604}", "{3183609639|1680443143}", "{3183609639|1509486054|2990812255|" + this.servantItemMarket.getServant().getName() + "}");
        final Servant boughtServant = new Servant(this.servant);
        boughtServant.setAccountId(this.player.getAccountId());
        boughtServant.setRegionId(this.regionId);
        boughtServant.setServantState(EServantState.Stable);
        this.player.getServantController().add(boughtServant);
        this.player.sendPacket(new SMBuyItNowServantAuctionGoodsVer2(boughtServant.getObjectId()));
        this.player.sendPacket(new SMListServantInfo(boughtServant, EPacketTaskType.Update));
        ServantAuctionDBCollection.getInstance().update(this.servantItemMarket);
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        this.decreaseItem(0, this.servantItemMarket.getPrice(), this.storageLocation);
        return super.canAct() && AuctionGoodService.getInstance().buyServantItemMarket(this.servantItemMarket, this.player);
    }
}
