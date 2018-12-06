// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMMailRemove;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.databaseCollections.MailsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;

public class CMMailRemove extends ReceivablePacket<GameClient> {
    private long id;
    private boolean result;

    public CMMailRemove(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.id = this.readQ();
        this.result = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Mail mail = player.getMailBox().getMailMap().get(this.id);
            if (mail == null) {
                return;
            }
            if (mail.getItemId() > 0) {
                player.sendPacket(new SMNak(EStringTable.eErrNoDbInternalError, this.opCode));
                return;
            }
            if (player.getMailBox().removeMail(this.id) != null) {
                MailsDBCollection.getInstance().delete(this.id);
                player.sendPacket(new SMMailRemove(this.id, this.result));
            }
        }
    }
}
