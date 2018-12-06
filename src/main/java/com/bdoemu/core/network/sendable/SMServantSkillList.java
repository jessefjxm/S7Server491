// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.skills.ServantSkill;

public class SMServantSkillList extends SendablePacket<GameClient> {
    private Servant servant;

    public SMServantSkillList(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeH(this.servant.getServantSkillList().getSkills().size());
        for (final ServantSkill servantSkill : this.servant.getServantSkillList().getSkills()) {
            buffer.writeC(servantSkill.getSkillId());
            buffer.writeD(servantSkill.getExp());
            buffer.writeC(servantSkill.isCannotChange());
        }
    }
}
