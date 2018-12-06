// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMMailList extends SendablePacket<GameClient> {
    private final Collection<Mail> mails;
    private final EPacketTaskType packetTaskType;

    public SMMailList(final Collection<Mail> mails, final EPacketTaskType packetTaskType) {
        this.packetTaskType = packetTaskType;
        this.mails = mails;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeH(this.mails.size());
        for (final Mail mail : this.mails) {
            buffer.writeQ(mail.getObjectId());
            buffer.writeS((CharSequence) mail.getName(), 62);
            buffer.writeS((CharSequence) mail.getMailSubject(), 202);
            buffer.writeQ(mail.getReceivedTimeSeconds());
            buffer.writeC(mail.getItem() != null);
            buffer.writeC(0);
        }
    }
}
