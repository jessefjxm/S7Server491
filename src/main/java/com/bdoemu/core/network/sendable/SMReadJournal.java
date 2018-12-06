// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;

import java.util.Collection;

public class SMReadJournal extends SendablePacket<GameClient> {
    private final long startDate;
    private final EJournalType journalType;
    private final Collection<JournalEntry> journalEntries;

    public SMReadJournal(final long startDate, final EJournalType journalType, final Collection<JournalEntry> journalEntries) {
        this.startDate = startDate;
        this.journalType = journalType;
        this.journalEntries = journalEntries;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.startDate);
        buffer.writeH(this.journalType.ordinal());
        buffer.writeH(this.journalEntries.size());
        for (final JournalEntry entry : this.journalEntries) {
            buffer.writeQ(entry.getDate() / 1000L);
            buffer.writeH(entry.getType().ordinal());
            buffer.writeH((int) entry.getParam0());
            buffer.writeH((int) entry.getParam1());
            buffer.writeH((int) entry.getParam2());
            buffer.writeH((int) entry.getParam3());
            buffer.writeH((int) entry.getParam4());
            buffer.writeH((int) entry.getParam5());
            buffer.writeS((CharSequence) entry.getParam6(), 102);
        }
    }
}
