// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.Card;

import java.util.Collection;

public class SMMakeThemeBook extends SendablePacket<GameClient> {
    private Collection<Card> cards;

    public SMMakeThemeBook(final Collection<Card> cards) {
        this.cards = cards;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.cards.size());
        for (final Card card : this.cards) {
            buffer.writeD(card.getCardId());
        }
    }
}
