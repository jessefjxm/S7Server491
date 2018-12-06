// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.ServantSkill;

public class SMSetServantSkillExp extends SendablePacket<GameClient> {
    private long servantObjId;
    private ServantSkill servantSkill;

    public SMSetServantSkillExp(final long servantObjId, final ServantSkill servantSkill) {
        this.servantObjId = servantObjId;
        this.servantSkill = servantSkill;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servantObjId);
        buffer.writeC(this.servantSkill.getSkillId());
        buffer.writeD(this.servantSkill.getExp());
        buffer.writeC(this.servantSkill.isCannotChange());
    }
}
