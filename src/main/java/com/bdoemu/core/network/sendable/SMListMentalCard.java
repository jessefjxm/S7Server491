// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.Card;

import java.util.Collection;

public class SMListMentalCard extends SendablePacket<GameClient> {
    private static final int maximum = 2000;
    private final Collection<Card> cards;

    public SMListMentalCard(final Collection<Card> cards) {
        this.cards = cards;
    }

    public static int getMaximum() {
        return 2000;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.cards.size());
        for (final Card card : this.cards) {
            buffer.writeD(card.getCardId());
            buffer.writeD(card.getCardGradeType().getLevel());
        }
    }
}
