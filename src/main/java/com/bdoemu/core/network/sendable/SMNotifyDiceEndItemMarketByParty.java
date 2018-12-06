// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.team.PartyItemWinner;

public class SMNotifyDiceEndItemMarketByParty extends SendablePacket<GameClient> {
    private PartyItemMarket partyItemMarket;

    public SMNotifyDiceEndItemMarketByParty(final PartyItemMarket partyItemMarket) {
        this.partyItemMarket = partyItemMarket;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.partyItemMarket.getMarketObjectId());
        buffer.writeH(this.partyItemMarket.getItemId());
        buffer.writeH(this.partyItemMarket.getEnchantLevel());
        for (final PartyItemWinner partyItemWinner : this.partyItemMarket.getPartyItemWinners()) {
            if (partyItemWinner != null) {
                buffer.writeS((CharSequence) partyItemWinner.getFamily(), 62);
                buffer.writeD(partyItemWinner.getDice());
            } else {
                buffer.writeS((CharSequence) "", 62);
                buffer.writeD(0);
            }
        }
    }
}
