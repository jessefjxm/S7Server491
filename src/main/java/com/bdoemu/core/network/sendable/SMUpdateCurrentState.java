// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;

public class SMUpdateCurrentState extends SendablePacket<GameClient> {
    private final CardGame game;

    public SMUpdateCurrentState(final CardGame game) {
        this.game = game;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeF(this.game.getNpcInterest());
        buffer.writeF(this.game.getNpcSympathy());
        buffer.writeF(this.game.getHit());
        buffer.writeF(0);
        buffer.writeF(0);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeH(0);
        buffer.writeD(this.game.getTotalIntimacy());
        for (final Card card : this.game.getCards()) {
            if (card != null) {
                buffer.writeD(card.getCardId());
            } else {
                buffer.writeD(0);
            }
        }
        buffer.writeH(0);
        buffer.writeD(0);
        buffer.writeH(0);
        buffer.writeH(this.game.getSuccessReqValue());
        buffer.writeH(this.game.getCurrentReqValue());
        buffer.writeH(this.game.getReqValue());
        buffer.writeH(this.game.getFailReqValue());
        buffer.writeH(this.game.getNeedCount());
        buffer.writeH(this.game.getTryCardIndex());
        buffer.writeH(this.game.getTotalSimpathy());
        buffer.writeH(this.game.getCurrentSimpathy());
        buffer.writeH(this.game.getZodiacIndex());
        buffer.writeH(this.game.getThemeTemplate().getThemeId());
        buffer.writeC(this.game.getGameCount());
        buffer.writeH(this.game.getGameId());
        buffer.writeC((int) this.game.getState().getId());
    }
}
