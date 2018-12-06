// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;

public class SMAcquiredJournal extends SendablePacket<GameClient> {
    private final EJournalType journalType;
    private final JournalEntry journalEntry;

    public SMAcquiredJournal(final EJournalType journalType, final JournalEntry journalEntry) {
        this.journalType = journalType;
        this.journalEntry = journalEntry;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.journalEntry.getDate() / 1000L);
        buffer.writeH(this.journalType.ordinal());
        buffer.writeH(this.journalEntry.getType().ordinal());
        buffer.writeH((int) this.journalEntry.getParam0());
        buffer.writeH((int) this.journalEntry.getParam1());
        buffer.writeH((int) this.journalEntry.getParam2());
        buffer.writeH((int) this.journalEntry.getParam3());
        buffer.writeH((int) this.journalEntry.getParam4());
        buffer.writeH((int) this.journalEntry.getParam5());
        buffer.writeS((CharSequence) this.journalEntry.getParam6(), 102);
    }
}
