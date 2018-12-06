// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyItNowServantAuctionItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMBuyItNowServantAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private int creatureId;
    private int servantLevel;
    private long itemMarketObjId;
    private long servantObjId;
    private long price;
    private long moneyObjId;
    private EItemStorageLocation storageLocation;

    public CMBuyItNowServantAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.itemMarketObjId = this.readQ();
        this.creatureId = this.readHD();
        this.servantLevel = this.readD();
        this.servantObjId = this.readQ();
        this.price = this.readQ();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null || function.getAuctionKey() == null) {
                return;
            }
            final int regionId = npc.getRegionId();
            final ServantItemMarket servantItemMarket = AuctionGoodService.getInstance().getServantItemMarket(this.itemMarketObjId);
            if (servantItemMarket == null) {
                player.sendPacket(new SMNak(EStringTable.eErrNoBuyItNowServantFailed, this.opCode));
                return;
            }
            if (servantItemMarket.getAccountId() == player.getAccountId()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoStableIsFull, this.opCode));
                return;
            }
            /*if (player.getAccountData().getBuyServantAuctionCoolTime() + 3600000L > GameTimeService.getServerTimeInMillis()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoServantBuyByTime, this.opCode));
                return;
            }*/
            if (player.getServantController().isStableFull(regionId, servantItemMarket.getServant().getServantType())) {
                player.sendPacket(new SMNak(EStringTable.eErrNoStableIsFull, this.opCode));
                return;
            }
            player.getPlayerBag().onEvent(new BuyItNowServantAuctionItemEvent(player, servantItemMarket, this.storageLocation, regionId));
        }
    }
}
