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
import com.bdoemu.gameserver.model.creature.player.itemPack.events.StartServantMatingItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMStartServantMating extends ReceivablePacket<GameClient> {
    private long itemMarketObjId;
    private long femaleObjId;
    private long maleObjId;
    private long moneyObjId;
    private long breedMoneyCost;
    private boolean isSelfOnly;
    private int npcGameObjId;
    private int horseId;
    private int level;
    private EItemStorageLocation storageLocation;

    public CMStartServantMating(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemMarketObjId = this.readQ();
        this.isSelfOnly = this.readCB();
        this.npcGameObjId = this.readD();
        this.femaleObjId = this.readQ();
        this.maleObjId = this.readQ();
        this.horseId = this.readHD();
        this.level = this.readD();
        this.readCD();
        this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.readB(42);
        this.breedMoneyCost = this.readQ();
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
            final Servant femaleServant = player.getServantController().getServant(this.femaleObjId);
            if (femaleServant != null && femaleServant.getServantState().isStable() && femaleServant.getRegionId() == npc.getRegionId()) {
                if (!femaleServant.getGameStats().getMp().isFull()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoServantMpIsLack, this.opCode));
                    return;
                }
                final ServantItemMarket servantItemMarket = AuctionGoodService.getInstance().getServantItemMarket(this.itemMarketObjId);
                if (!servantItemMarket.getServant().getServantState().isRegisterMating()) {
                    return;
                }
                player.getPlayerBag().onEvent(new StartServantMatingItemEvent(player, femaleServant, servantItemMarket, this.storageLocation, this.isSelfOnly, npc.getRegionId()));
            }
        }
    }
}
