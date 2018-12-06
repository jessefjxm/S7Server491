// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;

public class CMTakePartyItem extends ReceivablePacket<GameClient> {
    private int itemId;
    private int slotIndex;
    private int enchant;
    private long count;

    public CMTakePartyItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.slotIndex = this.readCD();
        this.count = this.readQ();
        this.itemId = this.readHD();
        this.enchant = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final IParty<Player> party = player.getParty();
            if (party != null) {
            }
        }
    }
}
