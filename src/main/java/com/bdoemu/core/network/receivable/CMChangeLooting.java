// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;
import com.bdoemu.gameserver.model.team.party.events.ChangePartyLootingEvent;

public class CMChangeLooting extends ReceivablePacket<GameClient> {
    private byte lootingType;

    public CMChangeLooting(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.lootingType = this.readC();
    }

    public void runImpl() {
        final EPartyLootType lootType = EPartyLootType.valueOf(this.lootingType);
        if (lootType == null) {
            return;
        }
        final Player owner = ((GameClient) this.getClient()).getPlayer();
        if (owner != null) {
            final IParty<Player> party = owner.getParty();
            if (party != null) {
                party.onEvent(new ChangePartyLootingEvent(party, owner, lootType));
            }
        }
    }
}
