// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMStartServantSkillExpTraining extends SendablePacket<GameClient> {
    private Servant servant;
    private int skillId;

    public SMStartServantSkillExpTraining(final Servant servant, final int skillId) {
        this.servant = servant;
        this.skillId = skillId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeC(this.skillId);
        buffer.writeQ(this.servant.getSkillTrainingTime());
    }
}
