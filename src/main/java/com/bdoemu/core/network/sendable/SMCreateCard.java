// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.Card;

public class SMCreateCard extends SendablePacket<GameClient> {
    private final Card card;

    public SMCreateCard(final Card card) {
        this.card = card;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.card.getCardId());
        buffer.writeD(this.card.getCardGradeType().getLevel());
    }
}
