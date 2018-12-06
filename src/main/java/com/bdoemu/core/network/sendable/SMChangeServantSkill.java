// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMChangeServantSkill extends SendablePacket<GameClient> {
    private Servant servant;
    private int removedSkills;
    private int addedSkills;

    public SMChangeServantSkill(final Servant servant, final int removedSkills, final int addedSkills) {
        this.servant = servant;
        this.removedSkills = removedSkills;
        this.addedSkills = addedSkills;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeC(this.removedSkills);
        buffer.writeC(this.addedSkills);
        buffer.writeD(this.servant.getHope());
    }
}
