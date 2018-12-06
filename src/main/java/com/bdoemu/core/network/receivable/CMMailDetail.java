// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMailDetail;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;

public class CMMailDetail extends ReceivablePacket<GameClient> {
    private long mailId;

    public CMMailDetail(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.mailId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Mail mail = player.getMailBox().getMailMap().get(this.mailId);
            if (mail != null) {
                player.sendPacket(new SMMailDetail(mail));
            }
        }
    }
}
