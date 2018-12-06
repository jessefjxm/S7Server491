// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMailList;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.stream.Collectors;

public class CMMailList extends ReceivablePacket<GameClient> {
    public CMMailList(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getMailBox().setHasNewMails(false);
            final ListSplitter<Mail> mails = new ListSplitter<>(player.getMailBox().getMails().stream().sorted((o1, o2) -> Long.compare(o2.getReceivedTime(), o1.getReceivedTime())).collect(Collectors.toList()), 57);
            while (mails.hasNext()) {
                player.sendPacket(new SMMailList(mails.getNext(), mails.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
            }
        }
    }
}
