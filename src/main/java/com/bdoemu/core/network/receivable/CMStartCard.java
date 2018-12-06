// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.npc.card.events.StartCardEvent;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMStartCard extends ReceivablePacket<GameClient> {
    public CMStartCard(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final CardGame game = player.getMentalCardHandler().getGame();
            if (game != null) {
                game.onEvent(new StartCardEvent(player, game));
            }
        }
    }
}
