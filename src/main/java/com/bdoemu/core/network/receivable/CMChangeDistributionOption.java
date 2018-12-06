// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.events.ChangeDistributionOptionPartyEvent;
import com.bdoemu.gameserver.model.team.party.events.EDistributionItemGrade;

public class CMChangeDistributionOption extends ReceivablePacket<GameClient> {
    private long moneyCount;
    private EDistributionItemGrade distributionItemGrade;

    public CMChangeDistributionOption(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.moneyCount = this.readQ();
        this.distributionItemGrade = EDistributionItemGrade.valueOf(this.readC());
    }

    public void runImpl() {
        if (this.distributionItemGrade == null || this.moneyCount < 0L) {
            return;
        }
        final Player owner = ((GameClient) this.getClient()).getPlayer();
        if (owner != null) {
            final IParty<Player> party = owner.getParty();
            if (party != null) {
                party.onEvent(new ChangeDistributionOptionPartyEvent(party, owner, this.distributionItemGrade, this.moneyCount));
            }
        }
    }
}
