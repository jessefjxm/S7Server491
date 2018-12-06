// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class SMCombineDyeItem extends SendablePacket<GameClient> {
    private final int slot;
    private final EItemStorageLocation storageType;

    public SMCombineDyeItem(final int slot, final EItemStorageLocation storageType) {
        this.slot = slot;
        this.storageType = storageType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.storageType.getId());
        buffer.writeC(this.slot);
    }
}
