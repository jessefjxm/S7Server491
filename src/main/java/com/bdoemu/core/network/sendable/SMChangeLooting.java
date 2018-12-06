// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.party.enums.EPartyLootType;

public class SMChangeLooting extends SendablePacket<GameClient> {
    private final EPartyLootType lootType;

    public SMChangeLooting(final EPartyLootType lootType) {
        this.lootType = lootType;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC((int) this.lootType.getId());
    }
}
