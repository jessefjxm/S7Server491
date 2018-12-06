// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.skills.PlayerSkillList;

public class SMSetCharacterSkillPoints extends SendablePacket<GameClient> {
    private final PlayerSkillList skillList;

    public SMSetCharacterSkillPoints(final PlayerSkillList skillList) {
        this.skillList = skillList;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.skillList.getSkillExpLevel());
        buffer.writeH(this.skillList.getAvailableSkillPoints());
        buffer.writeH(this.skillList.getTotalSkillPoints());
        buffer.writeD(this.skillList.getCurrentSkillPointsExp());
    }
}
