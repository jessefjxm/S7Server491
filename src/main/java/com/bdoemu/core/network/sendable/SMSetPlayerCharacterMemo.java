// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Memo;

public class SMSetPlayerCharacterMemo extends SendablePacket<GameClient> {
    private final Memo memo;

    public SMSetPlayerCharacterMemo(final Memo memo) {
        this.memo = memo;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.memo.getObjectId());
        buffer.writeQ(this.memo.getDate() / 1000L);
        buffer.writeD(this.memo.getUnk());
        buffer.writeF(this.memo.getX());
        buffer.writeF(this.memo.getZ());
        buffer.writeF(this.memo.getY());
        buffer.writeH(this.memo.getGroup1());
        buffer.writeH(this.memo.getQuestId1());
        buffer.writeH(this.memo.getGroup2());
        buffer.writeH(this.memo.getQuestId2());
        buffer.writeB(this.memo.getUnkData());
        buffer.writeS((CharSequence) this.memo.getMemoText(), 602);
    }
}
