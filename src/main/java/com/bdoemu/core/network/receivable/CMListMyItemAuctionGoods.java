// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListMyServantMatingAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMListMyWorkerAuctionGoodsVer2;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class CMListMyItemAuctionGoods extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private EAuctionRegisterType auctionRegisterType;

    public CMListMyItemAuctionGoods(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.auctionRegisterType = EAuctionRegisterType.values()[this.readC()];
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
            switch (this.auctionRegisterType) {
                case ServantMarket: {
                    final Collection<ServantItemMarket> servants = AuctionGoodService.getInstance().getMyRegisteredMarketServants(player.getAccountId());
                    if (!servants.isEmpty()) {
                        final ListSplitter<ServantItemMarket> splitterServant = (ListSplitter<ServantItemMarket>) new ListSplitter((Collection) servants, 15);
                        while (splitterServant.hasNext()) {
                            player.sendPacket(new SMListMyServantMatingAuctionGoodsVer2(splitterServant.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, splitterServant.getNext(), function.getAuctionKey()));
                        }
                        break;
                    }
                    player.sendPacket(new SMListMyServantMatingAuctionGoodsVer2(EPacketTaskType.Add, servants, function.getAuctionKey()));
                    break;
                }
                case ServantMatingMarket: {
                    final Collection<ServantItemMarket> matingServants = AuctionGoodService.getInstance().getMyRegisteredMatingServants(player.getAccountId());
                    if (!matingServants.isEmpty()) {
                        final ListSplitter<ServantItemMarket> splitterServant2 = (ListSplitter<ServantItemMarket>) new ListSplitter((Collection) matingServants, 15);
                        while (splitterServant2.hasNext()) {
                            player.sendPacket(new SMListMyServantMatingAuctionGoodsVer2(splitterServant2.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, splitterServant2.getNext(), function.getMatingKey()));
                        }
                        break;
                    }
                    player.sendPacket(new SMListMyServantMatingAuctionGoodsVer2(EPacketTaskType.Add, matingServants, function.getMatingKey()));
                    break;
                }
                case NpcWorkerMarket: {
                    final Collection<NpcWorkerItemMarket> npcWorkers = AuctionGoodService.getInstance().getMyRegisteredNpcWorkers(player.getAccountId());
                    if (!npcWorkers.isEmpty()) {
                        final ListSplitter<NpcWorkerItemMarket> splitterServant3 = (ListSplitter<NpcWorkerItemMarket>) new ListSplitter((Collection) npcWorkers, 15);
                        while (splitterServant3.hasNext()) {
                            player.sendPacket(new SMListMyWorkerAuctionGoodsVer2(splitterServant3.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, splitterServant3.getNext(), function.getAuctionKey()));
                        }
                        break;
                    }
                    player.sendPacket(new SMListMyWorkerAuctionGoodsVer2(EPacketTaskType.Add, npcWorkers, function.getAuctionKey()));
                    break;
                }
            }
        }
    }
}
