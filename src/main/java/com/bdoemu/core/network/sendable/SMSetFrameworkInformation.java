// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.ICipher;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMSetFrameworkInformation extends SendablePacket<GameClient> {
    private final ICipher crypt;

    public SMSetFrameworkInformation(final ICipher crypt) {
        this.crypt = crypt;
        this.setEncrypt(true);
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeB(this.crypt.getFrameWorkData());
    }
}
