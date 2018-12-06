// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EChangeNameType;

public class SMChangeName extends SendablePacket<GameClient> {
    private final EChangeNameType changeNameType;
    private final String newName;
    private final long objectId;
    private final int cacheCount;
    private Player player;

    public SMChangeName(final EChangeNameType changeNameType, final Player player, final String newName, final long objectId, final int cacheCount) {
        this.changeNameType = changeNameType;
        this.player = player;
        this.newName = newName;
        this.objectId = objectId;
        this.cacheCount = cacheCount;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.changeNameType.ordinal());
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeQ(this.objectId);
        buffer.writeD(this.cacheCount);
        buffer.writeS((CharSequence) this.newName, 62);
    }
}
