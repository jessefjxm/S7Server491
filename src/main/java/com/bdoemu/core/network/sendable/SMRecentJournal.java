// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.journal.JournalEntry;

import java.util.Collection;

public class SMRecentJournal extends SendablePacket<GameClient> {
    private final Collection<JournalEntry> journalEntries;

    public SMRecentJournal(final Collection<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.journalEntries.size());
        for (final JournalEntry journalEntry : this.journalEntries) {
            buffer.writeQ(journalEntry.getDate() / 1000L);
            buffer.writeH(journalEntry.getType().ordinal());
            buffer.writeH((int) journalEntry.getParam0());
            buffer.writeH((int) journalEntry.getParam1());
            buffer.writeH((int) journalEntry.getParam2());
            buffer.writeH((int) journalEntry.getParam3());
            buffer.writeH((int) journalEntry.getParam4());
            buffer.writeH((int) journalEntry.getParam5());
            buffer.writeS((CharSequence) journalEntry.getParam6(), 102);
        }
    }
}
