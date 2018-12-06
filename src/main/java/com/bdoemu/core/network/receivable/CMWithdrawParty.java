// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.WithdrawPartyEvent;
import com.bdoemu.gameserver.worldInstance.World;

public class CMWithdrawParty extends ReceivablePacket<GameClient> {
    private int gameObjId;

    public CMWithdrawParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Player kicked = World.getInstance().getPlayer(this.gameObjId);
            if (kicked != null) {
                final IParty<Player> party = player.getParty();
                if (party != null) {
                    party.onEvent(new WithdrawPartyEvent(player, kicked, party));
                }
            }
        }
    }
}
