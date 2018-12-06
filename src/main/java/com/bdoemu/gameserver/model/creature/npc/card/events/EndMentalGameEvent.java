// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

import com.bdoemu.core.network.sendable.SMEndMentalGame;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGameState;
import com.bdoemu.gameserver.model.creature.player.Player;

public class EndMentalGameEvent implements ICardEvent {
    private Player player;
    private CardGame game;

    public EndMentalGameEvent(final Player player, final CardGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void onEvent() {
        if (this.game.getState().isReadyToReward()) {
            this.game.setState(ECardGameState.Rewarded);
            if (this.game.getTotalIntimacy() > 0) {
                this.player.getIntimacyHandler().updateIntimacy(this.game.getCreatureId(), this.game.getSessionId(), this.game.getTotalIntimacy());
            }
        }
        this.player.getMentalCardHandler().setGame(null);
        this.player.sendPacket(new SMEndMentalGame());
    }

    @Override
    public boolean canAct() {
        return this.player.getMentalCardHandler().getGame() == this.game;
    }
}
