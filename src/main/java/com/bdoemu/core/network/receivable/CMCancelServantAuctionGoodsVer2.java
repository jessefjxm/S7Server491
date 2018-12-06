// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCancelServantAuctionGoodsVer2;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMCancelServantAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long objectId;
    private long servantObjectId;
    private EAuctionRegisterType auctionRegisterType;

    public CMCancelServantAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.objectId = this.readQ();
        this.auctionRegisterType = EAuctionRegisterType.values()[this.readC()];
        this.servantObjectId = this.readQ();
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
                    final ServantItemMarket servantItemMarket = AuctionGoodService.getInstance().removeServant(player.getAccountId(), this.objectId);
                    if (servantItemMarket != null) {
                        servantItemMarket.getServant().setServantState(EServantState.Stable);
                        servantItemMarket.getServant().setAuctionRegisterType(EAuctionRegisterType.None);
                        ServantsDBCollection.getInstance().save(servantItemMarket.getServant());
                        player.sendPacket(new SMCancelServantAuctionGoodsVer2(this.servantObjectId));
                        ServantAuctionDBCollection.getInstance().delete(servantItemMarket.getObjectId());
                        break;
                    }
                    break;
                }
                case ServantMatingMarket: {
                    final ServantItemMarket servantMatingItemMarket = AuctionGoodService.getInstance().removeMatingServant(player.getAccountId(), this.objectId);
                    if (servantMatingItemMarket != null) {
                        servantMatingItemMarket.getServant().setServantState(EServantState.Stable);
                        servantMatingItemMarket.getServant().setAuctionRegisterType(EAuctionRegisterType.None);
                        ServantsDBCollection.getInstance().save(servantMatingItemMarket.getServant());
                        player.sendPacket(new SMCancelServantAuctionGoodsVer2(this.servantObjectId));
                        ServantAuctionDBCollection.getInstance().delete(servantMatingItemMarket.getObjectId());
                        break;
                    }
                    break;
                }
            }
        }
    }
}
