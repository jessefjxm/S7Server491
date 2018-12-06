// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;

public class SMMailDetail extends SendablePacket<GameClient> {
    private final Mail mail;

    public SMMailDetail(final Mail mail) {
        this.mail = mail;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.mail.getObjectId());
        buffer.writeQ(this.mail.getSenderAccountId());
        buffer.writeS((CharSequence) this.mail.getName(), 62);
        buffer.writeS((CharSequence) this.mail.getMailSubject(), 202);
        buffer.writeS((CharSequence) this.mail.getMailMessage(), 602);
        buffer.writeC(this.mail.getItemType());
        buffer.writeH(this.mail.getProductNr());
        buffer.writeH(this.mail.getItemId());
        buffer.writeH(this.mail.getEnchantLevel());
        buffer.writeQ(this.mail.getCount());
        buffer.writeB(new byte[96]);
    }
}
