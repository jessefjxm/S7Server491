// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMContactNpc extends SendablePacket<GameClient> {
    private final int gameObjId;
    private final int dialogType;
    private final int discoverDialog;
    private final long staticId;

    public SMContactNpc(final long staticId, final int gameObjId, final int dialogType, final int discoverDialog) {
        this.staticId = staticId;
        this.gameObjId = gameObjId;
        this.dialogType = dialogType;
        this.discoverDialog = discoverDialog;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.staticId);
        buffer.writeD(this.gameObjId);
        buffer.writeD(this.dialogType);
        buffer.writeD(this.discoverDialog);
    }
}
