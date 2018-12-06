// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;

public class SMResultMentalGame extends SendablePacket<GameClient> {
    private final CardGame game;

    public SMResultMentalGame(final CardGame game) {
        this.game = game;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.game.getTotalSimpathy());
        buffer.writeD(this.game.getIntimacy());
        buffer.writeC(this.game.getIntimacy() > 0);
    }
}
