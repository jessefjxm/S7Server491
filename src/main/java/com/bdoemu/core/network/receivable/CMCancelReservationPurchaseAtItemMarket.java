// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.CancelReservationPurchaseAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;

public class CMCancelReservationPurchaseAtItemMarket extends ReceivablePacket<GameClient> {
    private int itemId;
    private int enchantLevel;
    private int npcGameObjId;
    private long moneyCount;
    private long itemCount;
    private long moneyObjId;
    private EItemStorageLocation storageLocation;

    public CMCancelReservationPurchaseAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.moneyCount = this.readQ();
        this.itemCount = this.readQ();
        this.npcGameObjId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjId = this.readQ();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null) {
                return;
            }
            final Integer territoryKey = function.getTerritoryKeyForItemMarket();
            if (territoryKey == null) {
                return;
            }
            final ETerritoryKeyType territoryKeyType = ETerritoryKeyType.values()[territoryKey];
            player.getPlayerBag().onEvent(new CancelReservationPurchaseAtItemMarketEvent(player, this.itemId, this.enchantLevel, territoryKeyType, npc.getRegionId(), this.storageLocation, this.moneyObjId));
        }
    }
}
