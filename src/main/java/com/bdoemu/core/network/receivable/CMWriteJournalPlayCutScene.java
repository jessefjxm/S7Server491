// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMWriteJournalPlayCutScene extends ReceivablePacket<GameClient> {
    private short cutSceneId;
    private short cutSceneId2;

    public CMWriteJournalPlayCutScene(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.cutSceneId = this.readH();
        this.cutSceneId2 = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final JournalEntry journalEntry = new JournalEntry();
            journalEntry.setDate(GameTimeService.getServerTimeInMillis());
            journalEntry.setType(EJournalEntryType.CutSceneViewed);
            journalEntry.setParam0(this.cutSceneId);
            journalEntry.setParam1(this.cutSceneId2);
            player.addJournalEntryAndNotify(journalEntry);
        }
    }
}
