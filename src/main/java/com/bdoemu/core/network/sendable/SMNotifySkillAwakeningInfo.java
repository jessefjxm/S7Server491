// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMNotifySkillAwakeningInfo extends SendablePacket<GameClient> {
    private int awakenedSkillSize;
    private int awakenedWeaponSkillSize;

    public SMNotifySkillAwakeningInfo(final int awakenedSkillSize, final int awakenedWeaponSkillSize) {
        this.awakenedSkillSize = awakenedSkillSize;
        this.awakenedWeaponSkillSize = awakenedWeaponSkillSize;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.awakenedSkillSize);
        buffer.writeD(this.awakenedWeaponSkillSize);
    }
}
