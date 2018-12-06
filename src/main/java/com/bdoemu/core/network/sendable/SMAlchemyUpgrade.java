// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMAlchemyUpgrade extends SendablePacket<GameClient> {
    private final int ownerGameObjId;
    private final int alchemyStoneExp;
    private final int slotIndex;
    private final EItemStorageLocation storageLocation;

    public SMAlchemyUpgrade(final int ownerGameObjId, final int alchemyStoneExp, final int slotIndex, final EItemStorageLocation storageLocation) {
        this.ownerGameObjId = ownerGameObjId;
        this.alchemyStoneExp = alchemyStoneExp;
        this.slotIndex = slotIndex;
        this.storageLocation = storageLocation;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.ownerGameObjId);
        buffer.writeC(this.storageLocation.getId());
        buffer.writeC(this.slotIndex);
        buffer.writeD(this.alchemyStoneExp);
    }
}
