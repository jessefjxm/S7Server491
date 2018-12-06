// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.alchemy.enums.EAlchemyType;

public class SMStartAlchemy extends SendablePacket<GameClient> {
    private EAlchemyType alchemyType;
    private int type;
    private EStringTable messageId;

    public SMStartAlchemy(final EAlchemyType alchemyType, final EStringTable messageId, final int type) {
        this.alchemyType = alchemyType;
        this.messageId = messageId;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD((int) this.alchemyType.getId());
        buffer.writeD(this.messageId.getHash());
        buffer.writeD(this.type);
    }
}
