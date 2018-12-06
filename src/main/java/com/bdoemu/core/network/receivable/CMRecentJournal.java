// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRecentJournal;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;

import java.util.Collection;

public class CMRecentJournal extends ReceivablePacket<GameClient> {
    private int lastEntryCount;

    public CMRecentJournal(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.lastEntryCount = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && this.lastEntryCount > 0) {
            final Collection<JournalEntry> lastEntries = player.getJournal(EJournalType.Character).getRecentEntriesFromJournal(this.lastEntryCount);
            if (!lastEntries.isEmpty()) {
                player.sendPacket(new SMRecentJournal(lastEntries));
            }
        }
    }
}
