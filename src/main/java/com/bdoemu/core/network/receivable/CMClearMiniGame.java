// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMClearMiniGame extends ReceivablePacket<GameClient> {
    private int miniGameId;

    public CMClearMiniGame(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.miniGameId = this.readH();
        this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getObserveController().notifyObserver(EObserveType.clearMiniGame, this.miniGameId);
        }
    }
}
