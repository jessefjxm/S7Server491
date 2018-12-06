// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.ChangePartyLeaderEvent;
import com.bdoemu.gameserver.worldInstance.World;

public class CMChangePartyLeader extends ReceivablePacket<GameClient> {
    private int gameObjId;

    public CMChangePartyLeader(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
    }

    public void runImpl() {
        final Player oldOwner = ((GameClient) this.getClient()).getPlayer();
        if (oldOwner != null) {
            final Player newOwner = World.getInstance().getPlayer(this.gameObjId);
            if (newOwner != null) {
                final IParty<Player> party = oldOwner.getParty();
                if (party != null) {
                    party.onEvent(new ChangePartyLeaderEvent(party, oldOwner, newOwner));
                }
            }
        }
    }
}
