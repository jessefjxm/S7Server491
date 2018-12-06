// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMReadJournal;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;

import java.util.Collection;

public class CMReadJournal extends ReceivablePacket<GameClient> {
    private long date;
    private EJournalType journalType;

    public CMReadJournal(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.date = this.readQ();
        this.journalType = EJournalType.values()[this.readC()];
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final Collection<JournalEntry> journalEntries = player.getJournal(this.journalType).getJournalEntriesFromDate(this.date);
        if (!journalEntries.isEmpty()) {
            final ListSplitter<JournalEntry> splitter = (ListSplitter<JournalEntry>) new ListSplitter((Collection) journalEntries, 131);
            while (!splitter.isLast()) {
                player.sendPacket(new SMReadJournal(this.date, this.journalType, splitter.getNext()));
            }
        }
    }
}
