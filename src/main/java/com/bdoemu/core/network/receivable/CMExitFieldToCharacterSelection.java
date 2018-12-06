// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.MainServer;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMExitFieldToCharacterSelection;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMExitFieldToCharacterSelection extends ReceivablePacket<GameClient> {
    public CMExitFieldToCharacterSelection(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final int cookie = Rnd.get(1, Integer.MAX_VALUE);
            MainServer.getRmi().updateCookie(player.getAccountId(), cookie);
            this.sendPacket((SendablePacket) new SMExitFieldToCharacterSelection(cookie));
        }
    }
}
