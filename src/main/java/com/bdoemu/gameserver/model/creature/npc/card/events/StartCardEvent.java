// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

import com.bdoemu.core.network.sendable.SMStartCard;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGameState;
import com.bdoemu.gameserver.model.creature.player.Player;

public class StartCardEvent implements ICardEvent {
    private Player player;
    private CardGame game;

    public StartCardEvent(final Player player, final CardGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void onEvent() {
        this.game.setState(ECardGameState.TryCards);
        this.player.sendPacket(new SMStartCard());
    }

    @Override
    public boolean canAct() {
        int totalCards = 0;
        for (final Card card : this.game.getCards()) {
            if (card != null) {
                ++totalCards;
            }
        }
        return this.player.getMentalCardHandler().getGame() == this.game && this.game.getState().isSelectCards() && totalCards == this.game.getNeedCount();
    }
}
