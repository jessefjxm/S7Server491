// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMSetRenderHelm;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.ERenderType;

public class CMSetRenderHelm extends ReceivablePacket<GameClient> {
    private boolean result;

    public CMSetRenderHelm(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.result = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerRenderStorage().setRender(ERenderType.HELM, this.result);
            player.sendBroadcastItSelfPacket(new SMSetRenderHelm(player));
        }
    }
}
