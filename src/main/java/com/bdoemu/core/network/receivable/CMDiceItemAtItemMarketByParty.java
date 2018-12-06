// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketByPartyInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.enums.ENotifyItemMarketPartyInfoType;
import com.bdoemu.gameserver.model.team.party.IParty;

public class CMDiceItemAtItemMarketByParty extends ReceivablePacket<GameClient> {
    private long itemMarketObjId;
    private int itemId;
    private int enchantLevel;
    private boolean isRefuse;

    public CMDiceItemAtItemMarketByParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemMarketObjId = this.readQ();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
        this.isRefuse = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final IParty<Player> party = player.getParty();
            if (party != null) {
                final PartyItemMarket partyItemMarket = party.getPartyInventory().get(this.itemMarketObjId);
                if (partyItemMarket == null) {
                    player.sendPacket(new SMNotifyItemMarketByPartyInfo(this.itemId, this.enchantLevel, this.itemMarketObjId, 1L, ENotifyItemMarketPartyInfoType.Clear));
                    player.sendPacket(new SMNak(EStringTable.eErrNoAlreadySoldOutItemAtItemMarket, this.opCode));
                    return;
                }
                if (partyItemMarket.getPartyId() != party.getPartyId()) {
                    return;
                }
                partyItemMarket.dice(player, this.isRefuse);
            }
        }
    }
}
