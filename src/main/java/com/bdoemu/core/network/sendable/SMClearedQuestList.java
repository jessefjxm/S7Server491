// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.quests.ClearedQuest;

import java.util.Collection;

public class SMClearedQuestList extends SendablePacket<GameClient> {
    public static final int MAX_SIZE = 1000;
    private final Collection<ClearedQuest> clearedQuestList;

    public SMClearedQuestList(final Collection<ClearedQuest> clearedQuestList) {
        this.clearedQuestList = clearedQuestList;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.clearedQuestList.size());
        for (final ClearedQuest quest : this.clearedQuestList) {
            buffer.writeH(quest.getQuestGroupId());
            buffer.writeH(quest.getQuestId());
            buffer.writeQ(quest.getRepeatTime() / 1000L);
        }
    }
}
