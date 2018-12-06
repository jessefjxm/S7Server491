// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.GamePacketFactory;

public class SMNak extends SendablePacket<GameClient> {
    private final long stringHash;
    private final int callerPacketOpcode;
    private final long param;

    public SMNak(final EStringTable stringTableMessage) {
        this.stringHash = stringTableMessage.getHash();
        this.callerPacketOpcode = 0;
        this.param = 0L;
    }

    public SMNak(final EStringTable stringTableMessage, final int callerPacketOpcode) {
        this.stringHash = stringTableMessage.getHash();
        this.callerPacketOpcode = callerPacketOpcode;
        this.param = 0L;
    }

    public SMNak(final EStringTable stringTableMessage, final Class<? extends ReceivablePacket> callerPacketClass) {
        this.stringHash = stringTableMessage.getHash();
        this.callerPacketOpcode = GamePacketFactory.getInstance().getClientPacketOpCode((Class) callerPacketClass);
        this.param = 0L;
    }

    public SMNak(final EStringTable stringTableMessage, final int callerPacketOpcode, final long param) {
        this.stringHash = stringTableMessage.getHash();
        this.callerPacketOpcode = callerPacketOpcode;
        this.param = param;
    }

    public SMNak(final EStringTable stringTableMessage, final Class<? extends ReceivablePacket> callerPacketClass, final long param) {
        this.stringHash = stringTableMessage.getHash();
        this.callerPacketOpcode = GamePacketFactory.getInstance().getClientPacketOpCode((Class) callerPacketClass);
        this.param = param;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.stringHash);
        buffer.writeH(this.callerPacketOpcode);
        buffer.writeQ(this.param);
    }
}
