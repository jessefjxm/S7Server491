// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMActionRefresh extends SendablePacket<GameClient> {
    private IAction action;

    public SMActionRefresh(final IAction action) {
        this.action = action;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.action.getOwnerGameObj());
        buffer.writeF(this.action.getCarrierX());
        buffer.writeF(this.action.getCarrierZ());
        buffer.writeF(this.action.getCarrierY());
        buffer.writeF(this.action.getNewX());
        buffer.writeF(this.action.getNewZ());
        buffer.writeF(this.action.getNewY());
        buffer.writeF(0);
        buffer.writeF(this.action.getCos());
        buffer.writeD(0);
        buffer.writeF(this.action.getSin());
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeD(this.action.getActionHash());
    }
}
