// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMDiePlayer extends SendablePacket<GameClient> {
    private int killerGameObjId;
    private int ownerGameObjId;
    private boolean isOnLogin;
    private boolean isPvpDuel;
    private long actionHash;

    public SMDiePlayer(final int killerGameObjId, final int ownerGameObjId, final boolean isOnLogin, final boolean isPvpDuel) {
        this(killerGameObjId, ownerGameObjId, isOnLogin, isPvpDuel, 2544805566L);
    }

    public SMDiePlayer(final int killerGameObjId, final int ownerGameObjId, final boolean isOnLogin, final boolean isPvpDuel, final long actionHash) {
        this.killerGameObjId = killerGameObjId;
        this.ownerGameObjId = ownerGameObjId;
        this.isOnLogin = isOnLogin;
        this.actionHash = actionHash;
        this.isPvpDuel = isPvpDuel;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.killerGameObjId);
        buffer.writeD(this.ownerGameObjId);
        buffer.writeD(0);
        buffer.writeD(0);
        buffer.writeD(this.actionHash);
        buffer.writeC(0);
        buffer.writeC(this.isPvpDuel);
        buffer.writeC(this.isOnLogin);
    }
}
