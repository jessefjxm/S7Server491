// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.party.events.EDistributionItemGrade;

public class SMChangeDistributionOption extends SendablePacket<GameClient> {
    private final long money;
    private final EDistributionItemGrade distributionItemGrade;

    public SMChangeDistributionOption(final long money, final EDistributionItemGrade distributionItemGrade) {
        this.money = money;
        this.distributionItemGrade = distributionItemGrade;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.money);
        buffer.writeC(this.distributionItemGrade.getId());
    }
}
