// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.ERenderType;

public class SMSetRenderUnderwear extends SendablePacket<GameClient> {
    private Player player;

    public SMSetRenderUnderwear(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeC(this.player.getPlayerRenderStorage().isRender(ERenderType.UNDERWEAR));
    }
}
