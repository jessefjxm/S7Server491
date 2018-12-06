// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;

import java.util.Collection;

public class SMProgressingQuestList extends SendablePacket<GameClient> {
    private final Collection<Quest> progressingQuestList;

    public SMProgressingQuestList(final Collection<Quest> progressingQuestList) {
        this.progressingQuestList = progressingQuestList;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.progressingQuestList.size());
        for (final Quest quest : this.progressingQuestList) {
            buffer.writeH(quest.getQuestGroupId());
            buffer.writeH(quest.getQuestId());
            for (final int step : quest.getSteps()) {
                buffer.writeD(step);
            }
            buffer.writeQ(quest.getAcceptTime());
        }
    }
}
