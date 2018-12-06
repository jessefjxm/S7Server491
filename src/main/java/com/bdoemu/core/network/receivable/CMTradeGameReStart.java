// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMTradeGameReStart extends ReceivablePacket<GameClient> {
    public CMTradeGameReStart(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        Player player = getClient().getPlayer();
        if (player != null)
            player.sendPacket(new SMNak(EStringTable.eErrNoIsGoingToImplement, CMTradeGameReStart.class));
    }
}
