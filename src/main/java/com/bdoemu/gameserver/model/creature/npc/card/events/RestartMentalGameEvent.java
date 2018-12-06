// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

import com.bdoemu.core.network.sendable.SMRestartMentalGame;
import com.bdoemu.core.network.sendable.SMUpdateCurrentState;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.player.Player;

public class RestartMentalGameEvent implements ICardEvent {
    private Player player;
    private CardGame game;

    public RestartMentalGameEvent(final Player player, final CardGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void onEvent() {
        this.game.restartGame();
        this.player.sendPacket(new SMRestartMentalGame((this.game.getGameCount() == 2) ? 42048 : 54476));
        this.player.sendPacket(new SMUpdateCurrentState(this.game));
    }

    @Override
    public boolean canAct() {
        return this.player.getMentalCardHandler().getGame() == this.game && this.game.getGameCount() < 3 && this.game.getState().isReadyToReward() && this.game.getTotalIntimacy() > 0;
    }
}
