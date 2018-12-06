// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;

public class SMUseSummonItemNak extends SendablePacket<GameClient> {
    private final int unk1;
    private final EStringTable stringTable;
    private final Creature summon;

    public SMUseSummonItemNak(final int unk1, final EStringTable stringTable, final Creature summon) {
        this.unk1 = unk1;
        this.stringTable = stringTable;
        this.summon = summon;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.unk1);
        buffer.writeD(this.stringTable.getHash());
        if (this.summon != null) {
            buffer.writeH(this.summon.getCreatureId());
            final Location loc = this.summon.getLocation();
            buffer.writeF(loc.getX());
            buffer.writeF(loc.getZ());
            buffer.writeF(loc.getY());
        } else {
            buffer.writeH(0);
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeF(0);
        }
    }
}
