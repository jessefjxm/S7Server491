// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListHouseAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMListServantMatingAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMListWorkerAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collection;

public class CMListAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private int pageIndex;
    private EAuctionRegisterType auctionRegisterType;

    public CMListAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.auctionRegisterType = EAuctionRegisterType.values()[this.readC()];
        this.readC();
        this.pageIndex = this.readD();
        this.readH();
        this.readC();
        this.readC();
        this.readC();
    }

    public void runImpl() {
        if (this.pageIndex < 0) {
            return;
        }
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
            switch (this.auctionRegisterType) {
                case ServantMarket: {
                    final Collection<ServantItemMarket> registeredServants = AuctionGoodService.getInstance().getMyRegisteredMarketServants(this.pageIndex);
                    player.sendPacket(new SMListServantMatingAuctionGoodsVer2(registeredServants, function.getAuctionKey()));
                    break;
                }
                case ServantMatingMarket: {
                    final Collection<ServantItemMarket> registeredMatingServants = AuctionGoodService.getInstance().getMatingServantsByPage(this.pageIndex, player.getAccountId());
                    player.sendPacket(new SMListServantMatingAuctionGoodsVer2(registeredMatingServants, function.getMatingKey()));
                    break;
                }
                case NpcWorkerMarket: {
                    final Collection<NpcWorkerItemMarket> registeredNpcWorkers = AuctionGoodService.getInstance().getNpcWorkersByPage(this.pageIndex);
                    player.sendPacket(new SMListWorkerAuctionGoodsVer2(registeredNpcWorkers, function.getAuctionKey()));
                    break;
                }
                case GuildAuctionMarket: {
                    //player.sendPacket(new SMListHouseAuctionGoodsVer2());
                    player.sendPacket(new SMNak(EStringTable.eErrNoIsGoingToImplement, this.opCode));
                    break;
                }
            }
        }
    }
}