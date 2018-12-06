// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMGetUserNickNameByCharacterName extends SendablePacket<GameClient> {
    private String name;
    private String familyName;

    public SMGetUserNickNameByCharacterName(final String name, final String familyName) {
        this.name = name;
        this.familyName = familyName;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.name, 62);
        buffer.writeS((CharSequence) this.familyName, 62);
    }
}
