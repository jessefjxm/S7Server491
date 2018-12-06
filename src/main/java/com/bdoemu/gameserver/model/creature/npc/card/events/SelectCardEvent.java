// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

import com.bdoemu.core.network.sendable.SMUpdateCurrentState;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SelectCardEvent implements ICardEvent {
    private Player player;
    private CardGame game;
    private int cardId;
    private int index;
    private Card card;

    public SelectCardEvent(final Player player, final CardGame game, final int cardId, final int index) {
        this.player = player;
        this.game = game;
        this.cardId = cardId;
        this.index = index;
    }

    @Override
    public void onEvent() {
        this.game.getCards()[this.index] = this.card;
        this.player.sendPacket(new SMUpdateCurrentState(this.game));
    }

    @Override
    public boolean canAct() {
        if (this.index < 0 || this.index >= this.game.getNeedCount()) {
            return false;
        }
        if (this.cardId > 0) {
            this.card = this.game.getTheme().getCards().get(this.cardId);
            if (this.card == null) {
                return false;
            }
            for (final Card card : this.game.getCards()) {
                if (card == this.card) {
                    return false;
                }
            }
        }
        return this.player.getMentalCardHandler().getGame() == this.game && this.game.getState().isSelectCards();
    }
}
